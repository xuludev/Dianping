package cn.mobile.entity;

import java.util.List;

/**
 * Created by LucasX on 2016/2/3.
 */
public class Csv {

    private String cvsName;
    private List<Merchant> list;

    public Csv(String cvsName, List<Merchant> list) {
        this.cvsName = cvsName;
        this.list = list;
    }

    public Csv() {
    }

    public String getCvsName() {
        return cvsName;
    }

    public void setCvsName(String cvsName) {
        this.cvsName = cvsName;
    }

    public List<Merchant> getList() {
        return list;
    }

    public void setList(List<Merchant> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "Csv{" +
                "cvsName='" + cvsName + '\'' +
                ", list=" + list +
                '}';
    }
}
