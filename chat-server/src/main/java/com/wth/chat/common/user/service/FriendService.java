package com.wth.chat.common.user.service;

import com.wth.chat.common.user.domain.vo.req.*;
import com.wth.chat.common.user.domain.vo.resp.*;

/**
 * <p>
 * 用户联系人表 服务类
 * </p>
 *
 * @author wth
 * @since 2023-12-25
 */
public interface FriendService {

    /**
     * 联系人列表
     * @param uid uid
     * @param request 翻页请求
     * @return
     */
    CursorPageBaseResp<FriendResp> friendList(Long uid, CursorPageBaseReq request);

    /**
     * 判断是否是自己的好友
     * @param uid
     * @param request
     * @return
     */
    FriendCheckResp check(Long uid, FriendCheckReq request);

    /**
     * 申请好友
     * @param uid
     * @param request
     */
    void apply(Long uid, FriendApplyReq request);

    /**
     * 删除好友
     * @param uid
     * @param targetUid
     */
    void deleteFriend(Long uid, Long targetUid);

    /**
     * 分页查询好友申请
     *
     * @param request 请求
     * @return {@link PageBaseResp}<{@link FriendApplyResp}>
     */
    PageBaseResp<FriendApplyResp> pageApplyFriend(Long uid, PageBaseReq request);

    /**
     * 申请列表的未读数
     * @param uid
     * @return
     */
    FriendUnreadResp unread(Long uid);

    /**
     * 同意好友申请
     *
     * @param uid     uid
     * @param request 请求
     */
    void applyApprove(Long uid, FriendApproveReq request);
}
