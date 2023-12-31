package com.wth.chat.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @Author: wth
 * @Create: 2023/12/31 - 23:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemInfoReq {
    @ApiModelProperty(value = "徽章信息入参")
    @Size(max = 50)
    private List<InfoReq> reqList;

    @Data
    public static class InfoReq {
        @ApiModelProperty(value = "徽章id")
        private Long itemId;
        @ApiModelProperty(value = "最近一次更新徽章信息时间")
        private Long lastModifyTime;
    }
}
