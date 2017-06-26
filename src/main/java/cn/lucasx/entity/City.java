package cn.lucasx.entity;

/**
 * Created by LucasX on 2016/2/2.
 */
public class City {

    private String name;
    private String pinyin;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public City(String name, String pinyin, String id) {
        this.name = name;
        this.pinyin = pinyin;
        this.id = id;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
