package com.jiashi.entity;

import java.util.Date;

public class Archive_Detail {
    private String id;

    private String pdfPath;

    private String masterid;

    private Date uploaddatetime;

    private String assortid;

    private String source;

    private String subassort;

    private String title;

    private String flag;

    private String sys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath == null ? null : pdfPath.trim();
    }

    public String getMasterid() {
        return masterid;
    }

    public void setMasterid(String masterid) {
        this.masterid = masterid == null ? null : masterid.trim();
    }

    public Date getUploaddatetime() {
        return uploaddatetime;
    }

    public void setUploaddatetime(Date uploaddatetime) {
        this.uploaddatetime = uploaddatetime;
    }

    public String getAssortid() {
        return assortid;
    }

    public void setAssortid(String assortid) {
        this.assortid = assortid == null ? null : assortid.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getSubassort() {
        return subassort;
    }

    public void setSubassort(String subassort) {
        this.subassort = subassort == null ? null : subassort.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys == null ? null : sys.trim();
    }
}