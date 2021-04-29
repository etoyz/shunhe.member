package com.sh.cloud.common;

/**
 * Created by jxh on 2019/2/14.
 */
public enum ResultConst {
    /**
     * success
     */
    SUCCESS("success", "成功"),
    FAIL("fail", "失败");

    private String result;
    private String message;

    ResultConst(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String resultCode) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

