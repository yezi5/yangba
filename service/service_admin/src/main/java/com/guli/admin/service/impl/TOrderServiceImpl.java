package com.guli.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.admin.pojo.TOrder;
import com.guli.admin.mapper.TOrderMapper;
import com.guli.admin.pojo.info.OrderQuery;
import com.guli.admin.pojo.vo.OrderVo;
import com.guli.admin.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 叶子
 * @since 2021-04-09
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Override
    public Map<String, Object> pageQuery(Long page, Long limit, OrderQuery orderQuery) {
        Page<TOrder> pagePara = new Page<>(page,limit);
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();

        this.page(pagePara, wrapper);

        Map<String, Object> map = new HashMap<>();
        List<TOrder> records = pagePara.getRecords();
        List<OrderVo> orderVoList = new ArrayList<>(records.size()+1);

        records.forEach(item -> {
            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(item,orderVo);
            orderVoList.add(orderVo);
        });
        long current = pagePara.getCurrent();
        long size = pagePara.getSize();
        long total = pagePara.getTotal();
        long pages = pagePara.getPages();
        boolean hasNext = pagePara.hasNext();
        boolean hasPrevious = pagePara.hasPrevious();

        map.put("list",orderVoList);
        map.put("size",size);
        map.put("total",total);
        map.put("pages",pages);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        map.put("current",current);

        return map;
    }

    @Override
    public boolean updateStatus(String orderNo) {
        int count = baseMapper.updateStatus(orderNo);
        return count > 0;
    }
}
