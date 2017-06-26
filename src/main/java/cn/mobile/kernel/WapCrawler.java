package cn.mobile.kernel;

import cn.lucasx.entity.City;
import cn.mobile.entity.Shop;
import cn.mobile.entity.Type;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by LucasX on 2016/4/27.
 */
public class WapCrawler {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 " +
            "Firefox/45.0";
    private static List<Shop> shopList = new ArrayList<>();


    public Map<String, List<Shop>> crawl(City city, Type type, int pageNum) {

        String requestUrl = "http://wap.dianping.com/shoplist/" + city.getId() + "/c/" + type.getId() + "/p" +
                pageNum;
        CloseableHttpClient closeableHttpClient = (CloseableHttpClient) WapCrawler.customeHttpClient();
        CloseableHttpResponse closeableHttpResponse = null;
        String[] shopListArr = null;

        try {
            closeableHttpResponse = closeableHttpClient.execute(new HttpGet(requestUrl));
            HttpEntity httpEntity = closeableHttpResponse.getEntity();
            shopListArr = this.parseHtml(EntityUtils.toString(httpEntity)); //初步请求获取的信息

            String requestDetailUrl = "http://wap.dianping.com/shop/" + shopListArr[0]; //二次请求URL
            CloseableHttpClient customeHttpClient = (CloseableHttpClient) WapCrawler.customeHttpClient();
            CloseableHttpResponse httpResponse = customeHttpClient.execute(new HttpGet
                    (requestDetailUrl));

            HttpEntity entity = httpResponse.getEntity();
            String[] detailInfoArr = this.parseHtmlDetail(EntityUtils.toString(entity));
            Shop shop = new Shop(shopListArr[0], shopListArr[1], shopListArr[3], shopListArr[4],
                    shopListArr[2], type.getTypeName(), detailInfoArr[0], detailInfoArr[1], detailInfoArr[2]);

            shopList.add(shop);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }

                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map<String, List<Shop>> map = new HashMap<>();
        map.put(type.getTypeName(), shopList);

        return map;
    }

    private static HttpClient customeHttpClient() {
        return HttpClients.custom().setUserAgent(USER_AGENT).build();
    }

    private static void writeCsv(Map<String, List<Shop>> map, String outDir) throws IOException {
        Path path = Paths.get(outDir);
        if (!Files.isDirectory(path) || Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = "";
        Iterator<String> iterator = map.keySet().iterator();
        if (map.keySet().iterator().hasNext()) {
            fileName = iterator.next();
        }
        Path outPath = Paths.get(outDir + "/" + fileName + ".csv");

        List<Shop> shopList = map.get(fileName);
        StringBuilder stringBuilder = new StringBuilder("");
        shopList.forEach(shop -> {
            stringBuilder.append(shop.getShopId()).append(",").append(shop.getShopName()).append(",")
                    .append(shop.getAvgPrice()).append(",").append(shop.getAddress()).append(",").append
                    (shop.getRank()).append(",").append(shop.getBigType()).append(",").append(shop
                    .getPhoneNumber()).append(",").append(shop.getRecommendInfo()).append(",").append(shop
                    .getCommentNum()).append("\r\n");
        });

        Files.write(outPath, stringBuilder.toString().getBytes("GBK"));
    }

    public static void main(String[] args) {
        WapCrawler wapCrawler = new WapCrawler();
        Map<String, List<Shop>> map = wapCrawler.crawl(new City("苏州", "suzhou", "6"), new Type("10", "美食"),
                1);
        try {
            writeCsv(map, "D:/大众点评");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初步解析html,再根据ShopID二次解析
     *
     * @param rawHtml
     */
    private String[] parseHtml(String rawHtml) {

        String[] shopListArr = new String[5];
        Document document = Jsoup.parse(rawHtml);
        Element ulElement = document.getElementsByTag("ul").get(0); //拿到商户信息所在的 li
        ulElement.children().forEach(element -> {

            Elements elements = element.getElementsByTag("a");
            if (elements.size() > 0) {
                Element aElement = elements.get(0); //得到<a>结点
                String shopId = aElement.attr("href").split("/shop/")[1];
                String shopName = aElement.text();

                String rank = element.getElementsByTag("img").attr("src").replace("/static/img/irr-star", "")
                        .replace("0" +
                                ".png", "");

                Elements elementsEm = element.getElementsByTag("em");
                String avgPrice = "";
                if (elementsEm.size() > 0) {
                    avgPrice = elementsEm.get(0).text();
                }
                String address = element.getElementsByAttributeValue("class", "addr").text();

                shopListArr[0] = shopId;
                shopListArr[1] = shopName;
                shopListArr[2] = rank;
                shopListArr[3] = avgPrice;
                shopListArr[4] = address;
            }
        });

        return shopListArr;
    }

    /**
     * 解析商户信息详情页
     *
     * @param rawHtml
     */
    private String[] parseHtmlDetail(String rawHtml) {
        Document document = Jsoup.parse(rawHtml);
        String phone = "", recommend = "", commentNum = "";
        if (document.getElementsByAttributeValue("class", "tel").size() > 0) {
            phone = document.getElementsByAttributeValue("class", "tel").get(0).text();
        }

        if (document.getElementsByAttributeValue("class", "sd-rec").size() > 0) {
            recommend = document.getElementsByAttributeValue("class", "sd-rec").get(0).text();
        }

        if (document.getElementsByAttributeValue("class", "sd-comment").size() > 0) {
            commentNum = document.getElementsByAttributeValue("class", "sd-comment").get(0).text();
        }
        String[] detailInfoArr = new String[]{phone, recommend, commentNum};

        return detailInfoArr;
    }
}
