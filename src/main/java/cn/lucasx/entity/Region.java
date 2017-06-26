package cn.lucasx.entity;

/**
 * Created by LucasX on 2016/2/2.
 */
public class Region {

    private String regionName;
    private String regionNumber;
    private String cityNumber;

    public Region(String regionName, String regionNumber, String cityNumber) {
        this.regionName = regionName;
        this.regionNumber = regionNumber;
        this.cityNumber = cityNumber;
    }

    public Region() {
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionNumber() {
        return regionNumber;
    }

    public void setRegionNumber(String regionNumber) {
        this.regionNumber = regionNumber;
    }

    public String getCityNumber() {
        return cityNumber;
    }

    public void setCityNumber(String cityNumber) {
        this.cityNumber = cityNumber;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionName='" + regionName + '\'' +
                ", regionNumber='" + regionNumber + '\'' +
                ", cityNumber='" + cityNumber + '\'' +
                '}';
    }
}
