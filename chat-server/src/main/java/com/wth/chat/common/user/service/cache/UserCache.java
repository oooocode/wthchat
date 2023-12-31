package com.wth.chat.common.user.service.cache;

import cn.hutool.core.collection.CollectionUtil;
import com.wth.chat.common.common.utils.RedisUtils;
import com.wth.chat.common.constants.RedisKey;
import com.wth.chat.common.user.dao.BlackDao;
import com.wth.chat.common.user.dao.UserRoleDao;
import com.wth.chat.common.user.domain.entity.Black;
import com.wth.chat.common.user.domain.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 17:38
 */
@Component
public class UserCache {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private BlackDao blackDao;


    @Cacheable(cacheNames = "user", key = "'roles:' + #uid")
    public Set<Long> listByUid(Long uid) {
        List<UserRole> userRoles = userRoleDao.listRoleByUid(uid);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

    @CacheEvict(cacheNames = "user", key = "'roles:' + #uid")
    public void evictByType(Long uid) {
    }

    @Cacheable(cacheNames = "user", key = "'blackMap'")
    public Map<Integer, Set<String>> listBlackMap() {
        List<Black> blackList = blackDao.list();
        if (CollectionUtil.isEmpty(blackList)) {
            return null;
        }
        Map<Integer, List<Black>> typeToBlackListMap = blackList.stream().collect(Collectors.groupingBy(Black::getType));
        Map<Integer, Set<String>> typeToTargetMap = new HashMap<>();
        typeToBlackListMap.forEach((type, black) -> {
            typeToTargetMap.put(type, black.stream().map(Black::getTarget).collect(Collectors.toSet()));
        });
        return typeToTargetMap;
    }

    @CacheEvict(cacheNames = "user", key = "'blackMap'")
    public void evictBlack() {
    }

    public List<Long> getUserModifyTime(List<Long> uidList) {
        List<String> keys = uidList.stream().map(uid -> RedisKey.getKey(RedisKey.USER_MODIFY_STRING, uid)).collect(Collectors.toList());
        return RedisUtils.mget(keys, Long.class);
    }
}
