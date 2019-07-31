package cn.itsource.basic.util;

/**
 * 请求结果
 */
public class AjaxResult {

    private boolean success = true;
    private String msg = "操作成功";
    private Object resultObj;
    private Integer errorCode;

    private AjaxResult() {
    }

    public static AjaxResult getAjaxResult(){
        return new AjaxResult();
    }
    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getResultObj() {
        return resultObj;
    }

    public AjaxResult setResultObj(Object resultObj) {
        this.resultObj = resultObj;
        return this;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public AjaxResult setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
        return this;
    }
}
