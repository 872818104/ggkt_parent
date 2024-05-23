package com.atguigu.ggkt.order.service.impl;

import com.atguigu.ggkt.model.order.OrderDetail;
import com.atguigu.ggkt.model.order.OrderInfo;
import com.atguigu.ggkt.order.mapper.OrderInfoMapper;
import com.atguigu.ggkt.order.service.OrderDetailService;
import com.atguigu.ggkt.order.service.OrderInfoService;
import com.atguigu.ggkt.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author caoruhao
 * @since 2024-05-21
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderDetailService orderDetailService;


    @Override
    public Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {

        // 查询匹配的条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();

        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(userId)) {
            wrapper.eq(OrderInfo::getUserId, userId);
        }

        if (!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq(OrderInfo::getOutTradeNo, outTradeNo);
        }

        if (!StringUtils.isEmpty(phone)) {
            wrapper.eq(OrderInfo::getPhone, phone);
        }

        if (!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.eq(OrderInfo::getCreateTime, createTimeBegin);
        }

        if (!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.eq(OrderInfo::getCreateTime, createTimeEnd);
        }

        if (!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq(OrderInfo::getOrderStatus, orderStatus);
        }

        //把查询结果放入分页中
        Page<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        long total = pages.getTotal();
        long pageCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords();
        records.forEach(this::getOrderDetail);

        //所有需要数据封装map集合，最终返回
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("pageCount", pageCount);
        map.put("records", records);
        return map;
    }

    // 查看订单详情信息
    private void getOrderDetail(OrderInfo orderInfo) {
        // 获取订单id
        Long id = orderInfo.getId();
        // 查询订单详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if (!StringUtils.isEmpty(orderDetail)) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName", courseName);
        }
    }
}
