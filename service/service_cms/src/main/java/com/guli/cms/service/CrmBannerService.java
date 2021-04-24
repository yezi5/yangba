package com.guli.cms.service;

import com.guli.cms.pojo.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author 叶子
 * @since 2020-10-15
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getIndexBanner();
}
