package com.wth.chat.common.user.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 17:57
 */
@Data
public class WearingBadgeReq {

    @NotNull
    private Long itemId;
}
