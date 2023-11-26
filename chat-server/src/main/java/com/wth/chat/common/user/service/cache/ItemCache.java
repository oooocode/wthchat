package com.wth.chat.common.user.service.cache;

import com.wth.chat.common.user.dao.ItemConfigDao;
import com.wth.chat.common.user.domain.entity.ItemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wth
 * @Create: 2023/11/26 - 17:38
 */
@Component
public class ItemCache {

    @Autowired
    private ItemConfigDao itemConfigDao;

    @Cacheable(cacheNames = "item", key = "'itemByType:' + #itemType")
    public List<ItemConfig> getByType(Integer itemType) {
        return itemConfigDao.getByType(itemType);
    }

    @CacheEvict(cacheNames = "item", key = "'itemByType:' + #itemType")
    public void evictByType(Integer itemType) {
    }
}
