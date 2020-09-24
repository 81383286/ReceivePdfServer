package com.jiashi.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

@Data
public class FileInfo implements Serializable {
    private String patientId;

    private String fileFlag;

    private String fileTitle;

    private String fentryNo;

    private String sysFlag;

    private MultipartFile file;

    private Integer id;

    private String assortId;

    private Date createTime;

    private Integer port;

    private Short success = 0;

    private String pdfPath;

    private Date endTime;
}
