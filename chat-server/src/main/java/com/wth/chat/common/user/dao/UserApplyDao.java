package com.wth.chat.common.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wth.chat.common.user.domain.entity.UserApply;
import com.wth.chat.common.user.domain.enums.ApplyReadStatusEnum;
import com.wth.chat.common.user.domain.enums.ApplyTypeEnum;
import com.wth.chat.common.user.mapper.UserApplyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wth.chat.common.user.domain.enums.ApplyReadStatusEnum.READ;
import static com.wth.chat.common.user.domain.enums.ApplyReadStatusEnum.UNREAD;
import static com.wth.chat.common.user.domain.enums.ApplyStatusEnum.AGREE;

/**
 * <p>
 * 用户申请表 服务实现类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
@Service
public class UserApplyDao extends ServiceImpl<UserApplyMapper, UserApply> {

    public UserApply getFriendApproving(Long uid, Long targetId) {
        return lambdaQuery()
                .eq(UserApply::getUid, targetId)
                .one();
    }

    public IPage<UserApply> friendApplyPage(Long uid, Page page) {
        return lambdaQuery()
                .eq(UserApply::getUid, uid)
                .eq(UserApply::getType, ApplyTypeEnum.ADD_FRIEND.getCode())
                .orderByDesc(UserApply::getCreateTime)
                .page(page);
    }

    public void readApples(Long uid, List<Long> applyIds) {
        lambdaUpdate()
                .set(UserApply::getReadStatus, READ.getCode())
                .eq(UserApply::getReadStatus, UNREAD.getCode())
                .in(UserApply::getId, applyIds)
                .eq(UserApply::getTargetId, uid)
                .update();
    }

    public Integer getUnReadCount(Long uid) {
        return lambdaQuery()
                .eq(UserApply::getUid, uid)
                .eq(UserApply::getReadStatus, UNREAD.getCode())
                .count();
    }

    public void agree(Long applyId) {
        lambdaUpdate()
                .set(UserApply::getStatus, AGREE.getCode())
                .eq(UserApply::getId, applyId)
                .update();
    }
}
