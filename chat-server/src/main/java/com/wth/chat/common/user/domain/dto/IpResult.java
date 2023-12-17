package com.wth.chat.common.user.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: wth
 * @Create: 2023/12/18 - 1:00
 */
@Data
public class IpResult<T> implements Serializable {
    @ApiModelProperty("错误码")
    private Integer code;
    @ApiModelProperty("错误消息")
    private String msg;
    @ApiModelProperty("返回对象")
    private T data;

    public boolean isSuccess() {
        return Objects.nonNull(this.code) && this.code == 0;
    }
}
