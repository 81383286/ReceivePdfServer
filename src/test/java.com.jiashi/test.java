package com.jiashi.controller;

import jdk.management.resource.ResourceType;

import java.io.File;

/**
 * @ProjectName:
 * @Description:
 * @Param 传输参数
 * @Return
 * @Author: 曾文和
 * @CreateDate: 2020/11/18 13:47
 * @UpdateUser: 曾文和
 * @UpdateDate: 2020/11/18 13:47
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class test {
    public static void main(String[] args) {
        File file = new File("D:\\pdf\\BloodSugar\\4523829\\fdbdcc7f8f2d4d75826a1ac69d7435d5.pdf");
        file.delete();
        String absolutePath = file.getParent();
        System.out.println("目录:"+absolutePath);
        File directory = new File(absolutePath);
        if(directory.isDirectory() && directory.length() <= 0){
            directory.delete();
        }
    }
    public static void deleteFile(String strPath) {
        //创建文件处理对象
        File dir = new File(strPath);
        //调用文件处理对象的处理方法listFiles()能够获取当前文件夹下的所有文件和文件夹，
        //如果文件夹A下还有文件D，那么D也在childs里
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                //文件为空判断
                if (files[i].length() <= 0) {
                    files[i].delete();
                }
            }
        }
    }
}
