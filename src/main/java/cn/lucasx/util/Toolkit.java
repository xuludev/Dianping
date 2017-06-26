package cn.lucasx.util;

import cn.lucasx.entity.City;
import cn.mobile.entity.Type;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Administrator on 2016/2/1.
 */
public class Toolkit {

    public static List<City> readCityXmlConfig(String xmlPath) throws ParserConfigurationException, IOException, SAXException {
        List<City> cityList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(new File(xmlPath)));
        Element element = document.getDocumentElement();

        NodeList nodeList = element.getChildNodes();
        int len = nodeList.getLength();
        for (int i = 0; i < len; i++) {
            if (nodeList.item(i) instanceof Element) {
                String name = nodeList.item(i).getTextContent().trim();
                String pinyin = ((Element) nodeList.item(i)).getAttribute("pinyin").trim();
                String id = ((Element) nodeList.item(i)).getAttribute("id").trim();
                City city = new City(name, pinyin, id);
                cityList.add(city);
            }
        }

        return cityList;
    }

    public static List<Type> readTypeXmlConfig(String xmlPath) throws ParserConfigurationException, IOException, SAXException {
        List<Type> typeList = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream(new File(xmlPath)));
        Element element = document.getDocumentElement();

        NodeList nodeList = element.getChildNodes();
        int len = nodeList.getLength();
        for (int i = 0; i < len; i++) {
            if (nodeList.item(i) instanceof Element) {
                String name = nodeList.item(i).getTextContent().trim();
                String id = ((Element) nodeList.item(i)).getAttribute("id").trim();
                Type type = new Type(id, name);
                typeList.add(type);
            }
        }

        return typeList;
    }

    public static Config readConfig(String configResourcePath) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(configResourcePath);
        String cityXmlOne = resourceBundle.getString("CITY_XML_ONE").trim();
        String cityXmlTwo = resourceBundle.getString("CITY_XML_TWO").trim();
        String typeXml = resourceBundle.getString("TYPE_XML").trim();

        return new Config(cityXmlOne, cityXmlTwo, typeXml);
    }

}