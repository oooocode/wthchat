package com.wth.chat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author: wth
 * @Create: 2023/11/25 - 22:50
 */
@Data
public class ModifyNameReq {

    @ApiModelProperty("要修改的名称")
    @NotBlank(message = "名字不能为空")
    @Length(max = 6, message = "名字不能过长")
    private String name;

}
