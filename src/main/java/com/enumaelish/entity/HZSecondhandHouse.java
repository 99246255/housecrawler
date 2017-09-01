package com.enumaelish.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 房信网二手房信息
 */
@Entity
@Table(name = "secondhandhouse")
public class HZSecondhandHouse implements Serializable {

    private long gpfyid; // 挂牌信息id
    @Id
    private String fwtybh;// 房源核验编码
    private String cqmc;// 地区
    private long xqid;// 小区id
    private String xqmc;// 小区
    private double gisx;// 经度
    private double gisy;//纬度
    private double jzmj;// 面积
    private String fczsh;// 房产证
    private Date createTime;// 记录载入时间
    private Date updateTime;// 记录修改时间，每次爬取修改，长时间没修改可能已成交
    @OneToMany(mappedBy="fwtybh", fetch = FetchType.EAGER)
    private List<GPInfo> gpInfos = new ArrayList<>(); // 挂牌信息列表，一堆多，一个房源有一个或多个挂牌信息
    public String getFwtybh() {
        return fwtybh;
    }

    public void setFwtybh(String fwtybh) {
        this.fwtybh = fwtybh;
    }

    public String getCqmc() {
        return cqmc;
    }

    public void setCqmc(String cqmc) {
        this.cqmc = cqmc;
    }

    public long getXqid() {
        return xqid;
    }

    public void setXqid(long xqid) {
        this.xqid = xqid;
    }

    public String getXqmc() {
        return xqmc;
    }

    public void setXqmc(String xqmc) {
        this.xqmc = xqmc;
    }

    public double getGisx() {
        return gisx;
    }

    public void setGisx(double gisx) {
        this.gisx = gisx;
    }

    public double getGisy() {
        return gisy;
    }

    public void setGisy(double gisy) {
        this.gisy = gisy;
    }

    public double getJzmj() {
        return jzmj;
    }

    public void setJzmj(double jzmj) {
        this.jzmj = jzmj;
    }

    public String getFczsh() {
        return fczsh;
    }

    public void setFczsh(String fczsh) {
        this.fczsh = fczsh;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<GPInfo> getGpInfos() {
        return gpInfos;
    }

    public void setGpInfos(List<GPInfo> gpInfos) {
        this.gpInfos = gpInfos;
    }

    public long getGpfyid() {
        return gpfyid;
    }

    public void setGpfyid(long gpfyid) {
        this.gpfyid = gpfyid;
    }
}
