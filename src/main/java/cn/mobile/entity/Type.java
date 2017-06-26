package cn.mobile.entity;

/**
 * Created by LucasX on 2016/2/15.
 * <p>
 * 消费类型Bean
 */
public class Type {
    private String id;
    private String typeName;

    public Type(String id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public Type() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
