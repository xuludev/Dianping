package cn.mobile.kernel;

import cn.lucasx.entity.City;
import cn.lucasx.util.Constant;
import cn.lucasx.util.Toolkit;
import cn.mobile.entity.Csv;
import cn.mobile.entity.Merchant;
import cn.mobile.entity.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LucasX on 2016/2/3.
 */
@Deprecated
public class MDPSpider {

    private static Logger logger = LogManager.getLogger();
    private static final String shopUrlPrefix = new String("http://m.dianping.com");
    private static List<String> visitedUrl = new ArrayList<>();
    private static String merchantUrl = "http://m.dianping.com/shoplist/CITY_ID/D_OR_R/0/c/TYPE_ID/s/s_-1?reqType=ajax&page=";

    public static void getMerchantDetailInfo(City city, List<Type> typeList) {

        typeList.forEach(type -> {

            Csv csv = new Csv();
            List<Merchant> merchantList = new ArrayList<>();
            boolean continueable = true;
            int pageNumber = 2;
            int maxPageNum = 2; //set crawler limitation
            logger.info("正在获取消费类型为 " + type.getTypeName() + " 的数据.....");
            while (continueable) {
                String requestUrl = "";

                if (Integer.parseInt(city.getId().trim()) <= 13) {
                    requestUrl = merchantUrl.replace("CITY_ID", city.getId()).replace("D_OR_R", "r").replace("TYPE_ID", type.getId()) + pageNumber;
                } else {
                    requestUrl = merchantUrl.replace("CITY_ID", city.getId()).replace("D_OR_R", "d").replace("TYPE_ID", type.getId()) + pageNumber;
                }

                if (!visitedUrl.contains(requestUrl)) {
                    logger.debug("正在获取第 " + pageNumber + " 页的数据......该页面地址为 " + requestUrl);
                    maxPageNum++;
                    try {
                        Document document = Jsoup.connect(requestUrl).userAgent(Constant.USER_AGENT).timeout(Constant.REQUEST_TIME_OUT).get();
                        String html = document.toString();
                        if (!html.contains("input")) {
                            continueable = false;
                            break;
                        }

                        if (null == html || "".equals(html.trim())) {
                            continueable = false;
                        }
                        document.getElementsByTag("li").forEach(oneMerchant -> {
                            String shopUrl = shopUrlPrefix + oneMerchant.getElementsByTag("a").get(0).attr("href");

                            //set default value
                            String zone = "", shopName = "", address = "", phone = "", commentNum = "", price = "", mainType = type.getTypeName(), subType = "", score1 = "", score2 = "", score3 = "", otherInfo = "";
                            try {
                                logger.debug("获取 " + shopUrl + " 的数据.....");
                                Document document1 = Jsoup.connect(shopUrl).userAgent(Constant.USER_AGENT).timeout(Constant.REQUEST_TIME_OUT).get();
                                if (document1.getElementsByAttributeValue("class", "shop-name").size() >= 1) {
                                    shopName = document1.getElementsByAttributeValue("class", "shop-name").get(0).text();
                                }
                                if (document1.getElementsByAttributeValue("class", "mainType").size() >= 1) {
                                    zone = document1.getElementsByAttributeValue("class", "mainType").get(0).text();
                                }
                                if (document1.getElementsByAttributeValue("class", "subType").size() >= 1) {
                                    subType = document1.getElementsByAttributeValue("class", "subType").get(0).text();
                                }

                                if (document1.getElementsByAttributeValue("class", "itemNum-val").size() >= 1) {
                                    commentNum = document1.getElementsByAttributeValue("class", "itemNum-val").get(0).text();
                                }

                                if (document1.getElementsByAttributeValue("class", "itemNum").size() >= 1) {
                                    if (null != document1.getElementsByAttributeValue("class", "itemNum").get(0).nextElementSibling()) {
                                        price = document1.getElementsByAttributeValue("class", "itemNum").get(0).nextElementSibling().text();
                                    }
                                }

                                if (document1.getElementsByAttributeValue("class", "desc").size() >= 1) {
                                    Elements scores = document1.getElementsByAttributeValue("class", "desc").get(0).getElementsByTag("span");

                                    if (scores.size() >= 3) {
                                        score1 = scores.get(0).text();
                                        score2 = scores.get(1).text();
                                        score3 = scores.get(2).text();
                                    } else if (scores.size() >= 2) {
                                        score1 = scores.get(0).text();
                                        score2 = scores.get(1).text();
                                    }
                                }

                                Elements infoDiv = document1.getElementsByAttributeValue("class", "info-list link-list");

                                if (infoDiv.size() >= 1) {
                                    if (infoDiv.get(0).getElementsByTag("a").size() >= 1) {
                                        address = infoDiv.get(0).getElementsByTag("a").get(0).text();
                                    }
                                    Elements temp = infoDiv.get(0).getElementsByTag("a");
                                    if (temp.size() >= 1) {
                                        if (null != temp.get(0).nextElementSibling()) {
                                            phone = temp.get(0).nextElementSibling().text();
                                        }
                                    }
                                }

                                if (document1.getElementsByAttributeValue("class", "businessTime").size() >= 1) {
                                    otherInfo = document1.getElementsByAttributeValue("class", "businessTime").get(0).text();
                                }
                                Merchant merchant = new Merchant(zone, shopName, address, phone, commentNum, price, mainType, subType, score1, score2, score3, otherInfo);
                                merchantList.add(merchant);
                                logger.debug(merchant);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    visitedUrl.add(requestUrl);
                }
                pageNumber++;
                csv.setCvsName(type.getTypeName());
                csv.setList(merchantList);
                try {
                    FileUtil.writeToCsv(csv, city.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("城市 : " + city.getName() + " \n类别 : " + type.getTypeName() + " \n第" + pageNumber + " 页的数据写入失败");
                }
            }
        });
    }

    public void process(String cityXmlPath, String typeXmlPath) throws IOException, SAXException, ParserConfigurationException {
        long startTime = System.currentTimeMillis();
        List<City> cityList = Toolkit.readCityXmlConfig(cityXmlPath);
        List<Type> typeList = Toolkit.readTypeXmlConfig(typeXmlPath);

        cityList.forEach(city -> {
            MDPSpider.getMerchantDetailInfo(city, typeList);
        });

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        logger.debug("数据获取完毕，一共消耗 " + (time / 1000) + " 秒....");
        logger.debug("现在时间 : " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }
}