package com.jiashi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @ProjectName:
 * @Description:
 * @Param 传输参数
 * @Return
 * @Author: 曾文和
 * @CreateDate: 2020/8/4 14:18
 * @UpdateUser: 曾文和
 * @UpdateDate: 2020/8/4 14:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@Slf4j
public class ExceptionPrintUtil {
    public static void printException(Exception e){
        //方法名
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        String exception = baos.toString();
        log.error(exception);
        try {
            baos.flush();
            baos.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
