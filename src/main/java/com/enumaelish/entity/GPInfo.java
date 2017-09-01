package com.enumaelish.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 挂牌人信息, 一个房源信息会对应多个挂牌人信息，记录价格变化
 */

@Entity
@Table
public class GPInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fwtybh;// 房源核验编码
    private String gplxrxm;// 挂牌人
    private String mdmc;// 挂牌机构
    private Date scgpshsj;// 挂牌时间
    private Date cjsj;// 成交时间？这个时间没显示
    private double price;// 价格
    private Date createTime;// 创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFwtybh() {
        return fwtybh;
    }

    public void setFwtybh(String fwtybh) {
        this.fwtybh = fwtybh;
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

    public void setScgpshsj(Date scgpshsj) {
        this.scgpshsj = scgpshsj;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

