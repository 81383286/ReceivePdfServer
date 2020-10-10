package com.jiashi.controller;

import com.jiashi.dao.ArchiveReceivePdfLogMapper;
import com.jiashi.entity.FileInfo;
import com.jiashi.service.ReceivePdfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

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
    @Scheduled(cron = "0 0 0 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    //8083端口处理定时补偿
    private void configureTasks() {
        if("8083".equals(port)){
            List<FileInfo> fileInfos = receivePdfLogMapper.selectAllByError();
            if(!CollectionUtils.isEmpty(fileInfos)){
                for(FileInfo info : fileInfos){
                    try {
                        receivePdfService.savePdf(info, info.getStr1());
                    }catch (Exception e){
                        e.printStackTrace();
                        log.error("补偿出错了,{}"+e.getMessage());
                    }
                }
                log.error("补偿"+fileInfos.size()+"个成功");
            }else{
                log.error(new Date() + ":无不成功记录");
            }

        }
    }
}
