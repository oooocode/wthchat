package com.wth.chat.common.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wth.chat.common.user.domain.entity.Black;
import com.wth.chat.common.user.domain.enums.BlackTypeEnum;
import com.wth.chat.common.user.mapper.BlackMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 黑名单 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-12-18
 */
@Service
public class BlackDao extends ServiceImpl<BlackMapper, Black> {

    public Black getByTargetAndType(String ip, BlackTypeEnum blackTypeEnum) {
        return lambdaQuery()
                .eq(Black::getTarget, ip)
                .eq(Black::getType, blackTypeEnum.getType())
                .one();
    }
}
