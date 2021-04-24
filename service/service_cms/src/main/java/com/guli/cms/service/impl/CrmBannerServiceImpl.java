package com.guli.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.cms.pojo.CrmBanner;
import com.guli.cms.mapper.CrmBannerMapper;
import com.guli.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> getIndexBanner() {
        List<CrmBanner> crmBanners = baseMapper.selectList(new QueryWrapper<>());
        return crmBanners;
    }
}
