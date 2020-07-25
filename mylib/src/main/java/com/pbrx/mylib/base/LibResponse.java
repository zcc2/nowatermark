package com.pbrx.mylib.base;

/**
 * Created by Iverson on 2016/12/23 下午8:59
 * 此类用于：返回数据的基类
 */

public class LibResponse<DataType> {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 通用返回值属性
     */
    private int code;
    /**
     * 通用返回信息。
     */
    private String msg;
    private String ver;
    private boolean success;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 具体的内容。
     */
    private DataType data;


    public DataType getData() {
        return data;
    }

    public void setData(DataType data) {
        this.data = data;
    }

//    @Override
//    public String toString() {
//        return "LibResponse{" +
//                "code='" + code + '\'' +
//                ", message='" + msg + '\'' +
//                ", data=" + data +
//                '}';
//    }
}
