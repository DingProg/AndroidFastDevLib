package ding.in.simple.data.entity;

import java.io.Serializable;

/**
 * fuction：
 * Created by dingdegao on 2016/10/21.
 */
public class DrugTestEntity implements Serializable {

    /**
     * count : 1683
     * description : 适应症 　　适用于与其他抗结核药联合治疗结核杆菌所致的肺结核。亦可用于结核性脑膜炎及非典型分枝杆菌感染的治疗。
     * fcount : 0
     * id : 602
     * img : /drug/081018/bf4f4bac0af1ea7b6231aea0a3ff32b0.jpg
     * keywords : 本品 分枝杆菌 抗结核 神经炎 每日
     * name : 盐酸乙胺丁醇片
     * price : 762
     * rcount : 0
     * tag : 结核病,过敏,其他,痛风
     * type : 化学药品
     */

    private int count;
    private String description;
    private int fcount;
    private int id;
    private String img;
    private String keywords;
    private String name;
    private int price;
    private int rcount;
    private String tag;
    private String type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRcount() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DrugTestEntity{" +
                "count=" + count +
                ", description='" + description + '\'' +
                ", fcount=" + fcount +
                ", id=" + id +
                ", img='" + img + '\'' +
                ", keywords='" + keywords + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", rcount=" + rcount +
                ", tag='" + tag + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
