package io.qiancy.rpcfx.exception;

import java.text.MessageFormat;

/**
 * 功能简述：
 *
 * @author qiancy
 * @create 2020/12/16
 * @since 1.0.0
 */
public class RpcfxException extends RuntimeException {

    /**
     * 异常错误码
     **/
    private String code;

    /**
     * 异常描述
     **/
    private String msg;
    /**
     * 扩展异常描述（包括msg）
     **/
    private String extMsg;

    /**
     * @param code 错误码
     * @param msg  描述信息
     */
    public RpcfxException(String code, String msg, String extMsg) {
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
        this.extMsg = extMsg;
    }


}
