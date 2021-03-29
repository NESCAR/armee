package abc.ney.armee.appris.controller;

import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基本返回类
 * @param <T>
 */
@ApiModel(value = "基本返回类")
public class BaseResp<T> {
    /**
     * 返回码
     */
    @ApiModelProperty(value = "状态码")
    private int status;

    /**
     * 返回信息描述
     */
    @ApiModelProperty(value = "返回信息描述")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BaseResp() {

    }

    /**
     * @param resultStatus 状态码
     * @param message      信息
     * @param data         数据
     */
    public BaseResp(ResultStatus resultStatus, String message, T data) {
        this.status = resultStatus.getStatus();
        this.message = message;
        this.data = data;
    }

    /**
     * @param resultStatus 状态码
     * @param message      信息
     */
    public BaseResp(ResultStatus resultStatus, String message) {
        this.status = resultStatus.getStatus();
        this.message = message;
    }

    /**
     * 不带数据的返回结果
     *
     * @param resultStatus 响应码
     */
    public BaseResp(ResultStatus resultStatus) {
        this.status = resultStatus.getStatus();
        this.message = resultStatus.getMsg();
    }

    /**
     * 带数据的返回结果
     *
     * @param resultStatus 响应码
     * @param data 数据
     */
    public  BaseResp(ResultStatus resultStatus, T data) {
        this.status = resultStatus.getStatus();
        this.message = resultStatus.getMsg();
        this.data = data;
    }
}

