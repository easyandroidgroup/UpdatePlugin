package org.lzh.framework.updatepluginlib.business;

/**
 * @author Administrator
 */
public class HttpException extends RuntimeException {

    private int code;
    private String errorMsg;

    public HttpException (int code,String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
