/**
 *
 */
package com.jiashi.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:Msg </p>
 * <p>Description:common return class </p>
 * <p>Company: </p>
 * @author hu
 * @date
 */
public class Msg {
    //state:100-success 200-fail
    private int code;
    //提示信息
    private String msg;
    //用户要返回给浏览器的数据
    private Map<String,Object> extend=new HashMap<String,Object>();

    public static Msg success(){
        Msg result=new Msg();
        result.setCode(100);
        result.setMsg("success");
        return result;
    }

    public static Msg fail(){
        Msg result=new Msg();
        result.setCode(200);
        result.setMsg("fail");
        return result;
    }

    public static Msg fail(String msg){
        Msg result=new Msg();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    public Msg add(String key,Object value){
        this.getExtend().put(key, value);
        return this;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", extend=" + extend +
                '}';
    }
}
