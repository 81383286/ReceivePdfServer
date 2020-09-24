package com.jiashi.controller;

import com.jiashi.entity.FileInfo;
import com.jiashi.service.ReceivePdfService;
import com.jiashi.util.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("front/")
public class ReceivePdfController {
    @Autowired
    private ReceivePdfService receivePdfService;

    @RequestMapping(value="receivePdf",method = RequestMethod.POST)
    @ResponseBody
    public Msg receivePdf(FileInfo fileInfo){
        try {
            long start = System.currentTimeMillis();
            Msg msg = receivePdfService.receivePdf(fileInfo);
            long end = System.currentTimeMillis();
            log.info("所用时间:"+(end-start)/1000.0+"s");
            log.info(msg.toString());
            return msg;
        }catch (Exception e){
            e.printStackTrace();
            return Msg.fail("服务器内部出错了,请联系系统管理员");
        }
    }
}
