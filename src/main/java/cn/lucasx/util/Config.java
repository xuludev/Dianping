package cn.lucasx.util;

/**
 * Created by LucasX on 2016/2/16.
 */
public class Config {

    private String cityXmlOne;
    private String cityXmlTwo;
    private String typeXml;

    public String getCityXmlOne() {
        return cityXmlOne;
    }

    public void setCityXmlOne(String cityXmlOne) {
        this.cityXmlOne = cityXmlOne;
    }

    public String getCityXmlTwo() {
        return cityXmlTwo;
    }

    public void setCityXmlTwo(String cityXmlTwo) {
        this.cityXmlTwo = cityXmlTwo;
    }

    public String getTypeXml() {
        return typeXml;
    }

    public void setTypeXml(String typeXml) {
        this.typeXml = typeXml;
    }

    public Config(String cityXmlOne, String cityXmlTwo, String typeXml) {
        this.cityXmlOne = cityXmlOne;
        this.cityXmlTwo = cityXmlTwo;
        this.typeXml = typeXml;
    }

    @Override
    public String toString() {
        return "Config{" +
                "cityXmlOne='" + cityXmlOne + '\'' +
                ", cityXmlTwo='" + cityXmlTwo + '\'' +
                ", typeXml='" + typeXml + '\'' +
                '}';
    }
}
