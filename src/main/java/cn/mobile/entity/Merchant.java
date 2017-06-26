package cn.mobile.entity;

/**
 * Created by LucasX on 2016/2/3.
 */
public class Merchant {

    private String zone;
    private String shopName;
    private String address;
    private String phone;
    private String commentNum;
    private String price;
    private String mainType;
    private String subType;
    private String score1;
    private String score2;
    private String score3;
    private String otherInfo;

    public Merchant(String zone, String shopName, String address, String phone, String commentNum, String price, String mainType, String subType, String score1, String score2, String score3, String otherInfo) {
        this.zone = zone;
        this.shopName = shopName;
        this.address = address;
        this.phone = phone;
        this.commentNum = commentNum;
        this.price = price;
        this.mainType = mainType;
        this.subType = subType;
        this.score1 = score1;
        this.score2 = score2;
        this.score3 = score3;
        this.otherInfo = otherInfo;
    }

    public Merchant() {
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "zone='" + zone + '\'' +
                ", shopName='" + shopName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", commentNum='" + commentNum + '\'' +
                ", price='" + price + '\'' +
                ", mainType='" + mainType + '\'' +
                ", subType='" + subType + '\'' +
                ", score1='" + score1 + '\'' +
                ", score2='" + score2 + '\'' +
                ", score3='" + score3 + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                '}';
    }
}
