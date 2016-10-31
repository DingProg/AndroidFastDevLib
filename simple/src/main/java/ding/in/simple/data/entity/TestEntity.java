package ding.in.simple.data.entity;

import java.io.Serializable;

/**
 * fuction：
 * Created by dingdegao on 2016/10/19.
 */
public class TestEntity implements Serializable {
    private long exprice;
    private long startTime;


    /**
     * description : 性病用药,性病用药药品,性病用药相关药品,治疗性病用药相关功能的药品,性病用药相关药品查询,性病用药药品库
     * drugclass : 0
     * id : 1
     * keywords : 性病用药
     * name : 性病用药
     * seq : 0
     * title : 性病用药
     */

    private String description;
    private int drugclass;
    private int id;
    private String keywords;
    private String name;
    private int seq;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDrugclass() {
        return drugclass;
    }

    public void setDrugclass(int drugclass) {
        this.drugclass = drugclass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "description='" + description + '\'' +
                ", drugclass=" + drugclass +
                ", id=" + id +
                ", keywords='" + keywords + '\'' +
                ", name='" + name + '\'' +
                ", seq=" + seq +
                ", title='" + title + '\'' +
                '}';
    }

}
