package cn.lucasx.core;

import cn.lucasx.entity.City;
import cn.lucasx.util.Config;
import cn.lucasx.util.Toolkit;
import cn.mobile.entity.Csv;
import cn.mobile.entity.Merchant;
import cn.mobile.entity.Type;
import cn.mobile.kernel.FileUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Created by LucasX on 2016/2/2.
 */
public class DPSpider {

    /**
     * get specific business zone via city's name
     *
     * @param cityName
     */
    private static Logger logger = LogManager.getLogger();
    private static List<Merchant> merchantList = new ArrayList<>();
    private static final String SHOP_URL_PREFIX = "http://www.dianping.com";
    private static final int SLEEP_SECOND = 2000;

    /**
     * 模拟浏览器请求自定义HttpClient
     *
     * @return
     */
    private static CloseableHttpClient customeHttpClient() {
        CloseableHttpClient closeableHttpClient = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows " +
                "NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537" +
                ".36").build();
        return closeableHttpClient;
    }

    private List<String> getShopUrl(String cityId, String typeId) throws IOException {

        List<String> shopUrlList = new ArrayList<>();
        CloseableHttpClient closeableHttpClient = DPSpider.customeHttpClient();
        int pageNum = 1, maxPageNum = DPSpider.getMaxPage("http://www.dianping.com/search/category/" +
                cityId + "/" + typeId + "/p1");

        while (pageNum <= maxPageNum) {
            String requestUrl = "http://www.dianping.com/search/category/" + cityId + "/" + typeId + "/p" +
                    pageNum;
            logger.debug("请求链接 " + requestUrl);
            HttpGet httpGet = new HttpGet(requestUrl);
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, sdch");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpGet.addHeader("Cache-Control", "no-cache");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Host", "www.dianping.com");
            httpGet.addHeader("Referer", "http://www.dianping.com/suzhou/food");

            try {
                CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);

                Document document = Jsoup.parse(EntityUtils.toString(closeableHttpResponse.getEntity()));

                getMaxPage(requestUrl);

                Elements aElements = document.getElementsByTag("a");
                aElements.forEach(element -> {
                    String shopUrl = SHOP_URL_PREFIX + element.attr("href");
                    if (Pattern.matches("/shop/\\d*$", element.attr("href")) && !shopUrlList.contains
                            (shopUrl)) {
                        shopUrlList.add(shopUrl);
                    }
                });

                Thread.sleep(SLEEP_SECOND);

            } catch (IOException e) {
                e.printStackTrace();

                return shopUrlList;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pageNum++;
        }

        return shopUrlList;
    }

    private static int getMaxPage(String aUrl) throws IOException {
        CloseableHttpResponse closeableHttpResponse = DPSpider.customeHttpClient().execute(new HttpGet(aUrl));
        String pageMax = "1";

        if (closeableHttpResponse.getStatusLine().getStatusCode() < 400) {
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            Document document = Jsoup.parse(EntityUtils.toString(httpEntity));
            Elements pageEles = document.getElementsByAttributeValue("class", "PageLink");
            pageMax = pageEles.get(pageEles.size() - 1).text().trim();
        } else {
            logger.error("对方拒绝了您的请求,错误代码: " + closeableHttpResponse.getStatusLine());
        }

        closeableHttpResponse.close();

        return Integer.parseInt(pageMax);
    }

    private Merchant extractMerchantInfo(String shopUrl) throws IOException {

        CloseableHttpResponse closeableHttpResponse = DPSpider.customeHttpClient().execute(new HttpGet
                (shopUrl));
        Merchant merchant = null;

        logger.debug("该商户链接为 " + shopUrl);
        if (closeableHttpResponse.getStatusLine().getStatusCode() < 400) {
            Document document = Jsoup.parse(EntityUtils.toString(closeableHttpResponse.getEntity()));
            String shopName = "", zone = "", address = "", phoneNumber = "", commentNum = "", mainType
                    = "", subType = "", score1 = "", score2 = "", score3 = "", otherInfo = "", price = "";

            Elements shopNameElements = document.getElementsByAttributeValue("class", "shop-name");
            if (shopNameElements.size() >= 1) {
                shopName = shopNameElements.get(0).ownText();
            }

            Elements regionNameElements = document.getElementsByAttributeValue("itemprop", "locality region");
            if (regionNameElements.size() >= 1) {
                zone = regionNameElements.get(0).text();
            }

            Elements addressElements = document.getElementsByAttributeValue("itemprop", "street-address");
            if (addressElements.size() >= 2) {
                address = addressElements.get(1).text();
            }
            Elements phoneNumberElements = document.getElementsByAttributeValue("itemprop", "tel");
            if (phoneNumberElements.size() >= 1) {
                phoneNumber = phoneNumberElements.get(0).text();
            }

            Elements merchantClassSmallElements = document.getElementsByAttributeValue("class", "breadcrumb");
            if (merchantClassSmallElements.get(0).getElementsByTag("a").size() >= 4) {
                subType = merchantClassSmallElements.get(0).getElementsByTag("a").get(3).text();
            }

            Elements elements = document.getElementsByAttributeValue("class", "brief-info");
            if (elements.size() >= 1) {
                if (elements.get(0).getElementsByTag("span").size() >= 6) {
                    commentNum = elements.get(0).getElementsByTag("span").get(1).text();
                    price = elements.get(0).getElementsByTag("span").get(2).text();
                    score1 = elements.get(0).getElementsByTag("span").get(3).text();
                    score2 = elements.get(0).getElementsByTag("span").get(4).text();
                    score3 = elements.get(0).getElementsByTag("span").get(5).text();
                } else if (elements.get(0).getElementsByTag("span").size() >= 4) {
                    score1 = elements.get(0).getElementsByTag("span").get(3).text();
                    score3 = score2 = score1;
                }
            }

            Elements otherInfoElements = document.getElementsByAttributeValue("class", "other J-other Hide");
            if (otherInfoElements.size() >= 1) {
                otherInfo = otherInfoElements.get(0).text();
            }

            merchant = new Merchant(zone, shopName, address, phoneNumber, commentNum, price, mainType,
                    subType, score1, score2, score3, otherInfo);
            logger.trace("商户信息为 : " + merchant);
            merchantList.add(merchant);
        } else {
            logger.error("频率过高,对方已拒绝您的请求,错误代码:" + closeableHttpResponse.getStatusLine().getStatusCode());
        }
        closeableHttpResponse.close();
        return merchant;
    }

    public void crawl(City city, Type type) throws IOException {

        List<String> shopUrlList = getShopUrl(city.getId(), type.getId());
        List<Merchant> merchantList = new ArrayList<>();

        Csv csv = new Csv();
        csv.setCvsName(type.getTypeName());
        shopUrlList.forEach(s -> {
            try {
                Merchant merchant = this.extractMerchantInfo(s);
                merchant.setMainType(type.getTypeName());
                merchantList.add(merchant);
                csv.setList(merchantList);

                //一边抓取一边写入...
                FileUtil.writeToCsv(csv, city.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public static void main(String[] args) {
        DPSpider dpSpider = new DPSpider();

        Config config = Toolkit.readConfig("config");

        try {
            /*List<City> cityList = Toolkit.readCityXmlConfig(config.getCityXmlTwo());
            List<Type> typeList = Toolkit.readTypeXmlConfig(config.getTypeXml());

            cityList.forEach(city -> {
                typeList.forEach(type -> {
                    try {
                        dpSpider.crawl(city, type);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            });*/
                dpSpider.crawl(new City("青岛", "qingdao", "21"), new Type("75", "教育培训"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}