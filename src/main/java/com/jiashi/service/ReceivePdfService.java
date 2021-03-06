package com.jiashi.service;

import com.jiashi.dao.ArchiveReceivePdfLogMapper;
import com.jiashi.dao.Archive_DetailMapper;
import com.jiashi.dao.Archive_MasterMapper;
import com.jiashi.entity.Archive_Detail;
import com.jiashi.entity.Archive_Master;
import com.jiashi.entity.FileInfo;
import com.jiashi.util.ExceptionPrintUtil;
import com.jiashi.util.Msg;
import com.lowagie.text.exceptions.BadPasswordException;
import com.lowagie.text.pdf.PdfReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
public class ReceivePdfService {
    @Autowired
    private Archive_MasterMapper masterMapper;
    @Autowired
    private Archive_DetailMapper detailMapper;
    @Autowired
    private ArchiveReceivePdfLogMapper receivePdfLogMapper;
    @Value("${tempPdfFileSrc}")
    private String tempPdfFileSrc;
    @Value("${pdfFileSrc}")
    private String pdfFileSrc;
    @Value("${server.port}")
    private String port;
    public Msg receivePdf(FileInfo fileInfo){
        fileInfo.setCreateTime(new Date());
        log.error(fileInfo.toString());
        //判断是否符合条件
        Msg msg = jedgeNullParams(fileInfo);
        if(msg.getCode() == 200){
            return msg;
        }
        String patientId = fileInfo.getPatientId();
        Archive_Master master = new Archive_Master();
        master.setPatientId(patientId);
        List<Archive_Master> list = masterMapper.selectAllByPatientId(master);
        if(CollectionUtils.isEmpty(list)){
            return Msg.fail("记帐号不存在!");
        }
        //添加接收日志
        String assortId = (String)msg.getExtend().get("assortId");
        //定义临时文件路径
        String tempPdfSrc = "";
        //定义uuid
        String uuid = "";
        try {
            fileInfo.setAssortId(assortId);
            fileInfo.setPort(Integer.valueOf(port));
            //新文件路径
            uuid = UUID.randomUUID().toString().replaceAll("-","");
            //上传到服务器本地磁盘D临时文件夹
            tempPdfSrc = getFileSrc(fileInfo, fileInfo.getSysFlag(), uuid, tempPdfFileSrc);
            fileInfo.setPdfPath(tempPdfSrc);
            //添加进日志
            receivePdfLogMapper.insertSelective(fileInfo);
            log.error(fileInfo.toString());
        }catch (Exception e){
            ExceptionPrintUtil.printException(e);
            log.error("插入数据库信息错误!",e.getMessage());
            return Msg.fail("服务器内部错误!");
        }
        try{
            //上传到临时文件夹
            fileInfo.getFile().transferTo(new File(tempPdfSrc));
        }catch (Exception e){
            ExceptionPrintUtil.printException(e);
            log.error("保存pdf临时文件出错了!",e.getMessage());
            return Msg.fail("保存pdf临时文件出错了!");
        }
        //数据库及转存操作
        try{
            savePdf(fileInfo,list.get(0).getId(),1);
        }catch (Exception e){
            ExceptionPrintUtil.printException(e);
            log.error("业务处理出错了!",e.getMessage());
        }
        return Msg.success();
    }

    /**
     * 数据库及转存操作
     * @param fileInfo
     * @return
     */
    @Transactional
    public Msg savePdf(FileInfo fileInfo,String masterId,Integer source) throws Exception{
        //新文件路径
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        Archive_Detail detail = new Archive_Detail();
        detail.setFlag("0");
        //masterId
        detail.setMasterid(masterId);
        //传输系统标识
        String sysFlag = fileInfo.getSysFlag();
        detail.setSource(sysFlag);
        //文件标识
        String fileFlag = fileInfo.getFileFlag();
        if("BloodSugar".equals(sysFlag)){
            detail.setTitle(fileFlag);
        }else{
            detail.setSubassort(fileFlag);
        }
        //根据masterId和文件标识
        List<Archive_Detail> details = detailMapper.selectAllByMasterIdAndFileFlag(detail);

        //设置文件名
        detail.setTitle(fileInfo.getFileTitle());
        String pdfSrc = getFileSrc(fileInfo, sysFlag, uuid, pdfFileSrc);
        detail.setPdfPath(pdfSrc);
        //设置分类id
        detail.setAssortid(fileInfo.getAssortId());
        //更新时间
        detail.setUploaddatetime(new Date());
        //为空，新增
        detail.setSys("receivePdf");
        if(CollectionUtils.isEmpty(details)){
            detailMapper.insertSelective(detail);
        }else{
            detail.setId(details.get(0).getId());
            //不为空，修改，并删除文件
            //删除文件
            File oldFile = new File(details.get(0).getPdfPath());
            //记录修改前的路径
            fileInfo.setStr1(details.get(0).getPdfPath());
            try {
                if(oldFile.exists()){
                    oldFile.delete();
                }
            }catch(Exception e){
                ExceptionPrintUtil.printException(e);
                log.error("替换原来文件时删除G盘文件:"+details.get(0).getPdfPath()+"失败");
            }
            detailMapper.updateByPrimaryKeySelective(detail);
        }
        //读取原文件
        String fileSrc = fileInfo.getPdfPath();
        File tempFile = new File(fileSrc);
        if(tempFile.exists()){
            //如果是补偿任务，先把原文件转存一遍后删除
            if(null != source && source == 2){
                String targetSrc = tempFile.getParent() + "\\" + uuid + ".pdf";
                Files.copy(Paths.get(fileSrc),Paths.get(targetSrc), StandardCopyOption.REPLACE_EXISTING);
                fileSrc = targetSrc;
            }
            //复制文件到远程映射盘
            Files.copy(Paths.get(fileSrc),Paths.get(pdfSrc), StandardCopyOption.REPLACE_EXISTING);
            //删除原文件
            tempFile.delete();
            if(null != source && source == 2){
                //删除转存文件
                new File(fileSrc).delete();
            }
            //判断是否是空目录，是空目录一起删除
            String absolutePath = tempFile.getParent();
            File directory = new File(absolutePath);
            if(directory.isDirectory() && directory.length() <= 0){
                directory.delete();
            }
            //成功的话fileInfo成功
            fileInfo.setSuccess(Short.valueOf("1"));
            fileInfo.setPdfPath(pdfSrc);
            fileInfo.setEndTime(new Date());
            receivePdfLogMapper.updateByPrimaryKeySelective(fileInfo);
        }else{
            log.error("原文件不存在,"+fileInfo.toString());
        }
        return Msg.success();
    }

    /**
     * 组织全路径且目录不存在创建目录
     * @param fileInfo
     * @param sysFlag
     * @param uuid
     * @param tempPdfFileSrc
     * @return
     */
    private String getFileSrc(FileInfo fileInfo, String sysFlag, String uuid, String tempPdfFileSrc) {
        String tempPdfPath = tempPdfFileSrc + sysFlag + "\\" + fileInfo.getPatientId();
        //创建目录
        if (!new File(tempPdfPath).isDirectory()) {
            new File(tempPdfPath).mkdirs();
        }
        return tempPdfPath + "\\" + uuid + ".pdf";
    }

    /**
     * 判断不为空
     * @param fileInfo
     */
    private Msg jedgeNullParams(FileInfo fileInfo) {
        if(fileInfo.getFile() == null){
            return Msg.fail("file参数不能为空!");
        }
        String patientId = fileInfo.getPatientId();
        if(StringUtils.isBlank(patientId)){
            return Msg.fail("patientId参数不能为空!");
        }
        if(StringUtils.isBlank(fileInfo.getFileFlag())){
            return Msg.fail("fileFlag参数不能为空!");
        }
        if(StringUtils.isBlank(fileInfo.getFileTitle())){
            return Msg.fail("fileTitle参数不能为空!");
        }
        if(StringUtils.isBlank(fileInfo.getFentryNo())){
            return Msg.fail("fentryNo参数不能为空!");
        }
        if(StringUtils.isBlank(fileInfo.getSysFlag())){
            return Msg.fail("sysFlag参数不能为空!");
        }
        //判断是否pdf文件
        MultipartFile file = fileInfo.getFile();
        //获取扩张名
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        if (!"pdf".equals(ext)) {
            return Msg.fail("目前只支持传输pdf文件!");
        }
        //验证文件的完整性
        try {
            new PdfReader(file.getBytes());
        } catch (BadPasswordException e) {
            ExceptionPrintUtil.printException(e);
            log.error("此pdf文档为加密文档,不支持加密文档!",e.getMessage());
            return Msg.fail("此pdf文档为加密文档,不支持加密文档!");
        } catch (Exception e) {
            ExceptionPrintUtil.printException(e);
            log.error("此文档为无效文档,请重新上传!",e.getMessage());
            return Msg.fail("此文档为无效文档,请重新上传!");
        }
        //文件分类编号
        String fentryNo = fileInfo.getFentryNo();
        //转换文件分类id
        String assortId = getAssortId(fentryNo);
        if (StringUtils.isBlank(assortId)) {
            return Msg.fail("文件分类编码不存在!");
        }
        return Msg.success().add("assortId",assortId);
    }

    /**血糖编码：8888
     * 通过his传输的分类参数转换分类id
     * @param fentryNo
     * @return
     */
    private String getAssortId(String fentryNo){
        Map<String, String> map = new HashMap<>();
        map.put("801", "DA342ED81CEE4A8EA827424626F3F521");
        map.put("804", "15E7FE7803F545CB81390BC88E725240");
        map.put("811", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("828", "AFB9FBE656D7492C80AEDE6E685A851A");
        map.put("813", "7A9C621E3F4F4C9CA95292141C5E15E8");
        map.put("845R", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("844", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("022", "85DAE73A87D047D28C222E878C78C670");
        map.put("808", "15E7FE7803F545CB81390BC88E725240");
        map.put("803R", "AFB9FBE656D7492C80AEDE6E685A851A");
        map.put("833", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("843", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("846", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("020", "AC2C8F4A88884DC894630302C61C6A07");
        map.put("021", "AC2C8F4A88884DC894630302C61C6A07");
        map.put("802", "AFB9FBE656D7492C80AEDE6E685A851A");
        map.put("805", "15E7FE7803F545CB81390BC88E725240");
        map.put("810", "C7C73CD034B440F6B33A79E382A5610F");
        map.put("832", "DE599D770E8347CCB5122BC357D96F35");
        map.put("814", "DE599D770E83479CB5126BC357D96F35");
        map.put("816", "DE599D770E8347CCB5122BC357D96F35");
        map.put("818", "C70E8C427A3648B79BE80798C08F4D12");
        map.put("806", "7A9C621E3F4F4C9CA95292141C5E15E8");
        map.put("025", "0DB93797885746B18DAF6C0C936D2DCA");
        map.put("847", "C7C73CD034B440F6B33A79E382A5610F");
        map.put ("02D","D80ED429AEC24C389E444F3156F890B5");
        //血糖
        map.put ("8888","799B1580459442598B6073712736BA50");
        return map.get(fentryNo);
    }
}
