package com.jiashi.controller;

import com.jiashi.dao.ArchiveReceivePdfLogMapper;
import com.jiashi.entity.FileInfo;
import com.jiashi.service.ReceivePdfService;
import com.jiashi.util.ExceptionPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
@Slf4j
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {
    @Value("${server.port}")
    private String port;
    @Autowired
    private ArchiveReceivePdfLogMapper receivePdfLogMapper;
    @Autowired
    private ReceivePdfService receivePdfService;
    //3.添加定时任务
    @Scheduled(cron = "0 33 16 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=60000)
    //8083端口处理定时补偿
    private void configureTasks() {
        if("8083".equals(port)){
            List<FileInfo> fileInfos = receivePdfLogMapper.selectAllByError();
            if(!CollectionUtils.isEmpty(fileInfos)){
                try {
                    //定义成功次数
                    int successCount = 0;
                    for(FileInfo info : fileInfos){
                        receivePdfService.savePdf(info, info.getC1(),2);
                        successCount++;
                    }
                    log.error("补偿"+successCount+"个成功");
                }catch (Exception e){
                    ExceptionPrintUtil.printException(e);
                    log.error("补偿出错了,{}"+e.getMessage());
                }
            }else{
                log.error(new Date() + ":无不成功记录");
            }
        }
    }
}
