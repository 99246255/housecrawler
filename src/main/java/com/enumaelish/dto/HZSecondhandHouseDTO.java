package com.enumaelish.dto;

import com.enumaelish.utils.CustomDateDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

/**
 * 房信网抓取信息dto，有很多其他字段，值为空或者没猜出含义，暂时不取
 */
public class HZSecondhandHouseDTO {

    private long gpfyid; // 挂牌信息id
    private String fwtybh;// 房源核验编码
    private String cqmc;// 地区
    private long xqid;// 小区id
    private String xqmc;// 小区
    private String gisx;// 经度
    private String gisy;//纬度
    private double jzmj;// 面积
    private String fczsh;// 房产证
    private String gplxrxm;// 挂牌人
    private String mdmc;// 挂牌机构
    private Date scgpshsj;// 挂牌时间
    private Date cjsj;// 成交时间？这个时间没显示
    private double wtcsjg;// 价格

    private long xzqh;//行政区号
    private String xzqhname;// 行政区号名称

    public long getGpfyid() {
        return gpfyid;
    }

    public void setGpfyid(long gpfyid) {
        this.gpfyid = gpfyid;
    }

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

    public String getGisx() {
        return gisx;
    }

    public void setGisx(String gisx) {
        this.gisx = gisx;
    }

    public String getGisy() {
        return gisy;
    }

    public void setGisy(String gisy) {
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

    public String getGplxrxm() {
        return gplxrxm;
    }

    public void setGplxrxm(String gplxrxm) {
        this.gplxrxm = gplxrxm;
    }

    public String getMdmc() {
        return mdmc;
    }

    public void setMdmc(String mdmc) {
        this.mdmc = mdmc;
    }

    public Date getScgpshsj() {
        return scgpshsj;
    }
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setScgpshsj(Date scgpshsj) {
        this.scgpshsj = scgpshsj;
    }

    public Date getCjsj() {
        return cjsj;
    }
    @JsonDeserialize(using = CustomDateDeserialize.class)
    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public double getWtcsjg() {
        return wtcsjg;
    }

    public void setWtcsjg(double wtcsjg) {
        this.wtcsjg = wtcsjg;
    }

    public long getXzqh() {
        return xzqh;
    }

    public void setXzqh(long xzqh) {
        this.xzqh = xzqh;
    }

    public String getXzqhname() {
        return xzqhname;
    }

    public void setXzqhname(String xzqhname) {
        this.xzqhname = xzqhname;
    }
}
