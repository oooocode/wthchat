package com.wth.chat.common.user.dao;

import com.wth.chat.common.user.domain.entity.ItemConfig;
import com.wth.chat.common.user.mapper.ItemConfigMapper;
import com.wth.chat.common.user.service.IItemConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 功能物品配置表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-11-25
 */
@Service
public class ItemConfigDao extends ServiceImpl<ItemConfigMapper, ItemConfig> implements IItemConfigService {

}
