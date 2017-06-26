package cn.mobile.entity;

/**
 * Created by LucasX on 2016/4/27.
 * <p>
 * WAP版的商户信息
 */
public class Shop {

    //shopList页面就可以直接获取到的信息
    private String shopId;
    private String shopName;
    private String avgPrice;
    private String address;
    private String rank;//星级--从图片链接src中取，src="/static/img/irr-star50.png"就代表"五星"
    private String bigType;//商户大类 -- 从type.xml中直接传入，不需要解析

    //需要二次请求到商户详情页才能获取
    private String phoneNumber;
    private String recommendInfo;//网友推荐
    private String commentNum;//点评条数

    public Shop(String shopId, String shopName, String avgPrice, String address, String rank, String
            bigType, String phoneNumber, String recommendInfo, String commentNum) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.avgPrice = avgPrice;
        this.address = address;
        this.rank = rank;
        this.bigType = bigType;
        this.phoneNumber = phoneNumber;
        this.recommendInfo = recommendInfo;
        this.commentNum = commentNum;
    }

    public Shop() {
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRecommendInfo() {
        return recommendInfo;
    }

    public void setRecommendInfo(String recommendInfo) {
        this.recommendInfo = recommendInfo;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getBigType() {
        return bigType;
    }

    public void setBigType(String bigType) {
        this.bigType = bigType;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopId='" + shopId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", avgPrice='" + avgPrice + '\'' +
                ", address='" + address + '\'' +
                ", rank='" + rank + '\'' +
                ", bigType='" + bigType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", recommendInfo='" + recommendInfo + '\'' +
                ", commentNum='" + commentNum + '\'' +
                '}';
    }
}
