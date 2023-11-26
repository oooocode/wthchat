package com.wth.chat.common.user.service;

import com.wth.chat.common.user.domain.enums.IdempotentEnum;

/**
 * <p>
 * 用户背包表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-11-25
 */
public interface UserBackpackService {

    /**
     * 发放物品(幂等)
     *
     * @param uid            用户id
     * @param itemId         物品id
     * @param idempotentEnum 发放来源
     * @param businessId       业务id
     */
    void acquireItem(Long uid, Long itemId, IdempotentEnum idempotentEnum, String businessId);

}
