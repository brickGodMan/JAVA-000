package io.qiancy.rpcfx.api;

import io.qiancy.rpcfx.exception.RpcfxException;

public class RpcfxResponse {

    private Object result;

    private boolean status;

    private RpcfxException exception;

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(RpcfxException exception) {
        this.exception = exception;
    }
}
