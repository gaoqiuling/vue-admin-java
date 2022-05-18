package com.itisacat.backend.demo;

import com.google.common.collect.Lists;
import com.itisacat.backend.demo.dto.CategoryResponse;
import com.itisacat.backend.demo.dto.OrderResponse;
import com.itisacat.backend.demo.dto.SupplierResponse;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Config {

    private static List<OrderResponse> list = Lists.newArrayList();

    static {
        int orderId = 1000000;
        List<Integer> categoryIds = Arrays.asList(7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20);
        for (int i = 0; i < 15; i++) {
            Date date = DateUtils.addDays(new Date(), -1 * i);
            OrderResponse order = new OrderResponse();
            order.setOrderId(orderId + i);
            order.setSupplierId((i + 1) % 5);
            order.setUpdateAuthor("test" + i);
            order.setAfterSaleStatus(i % 5);
            order.setAfterSaleTime(date);
            order.setCategoryId(categoryIds.get(i % 12));
            list.add(order);
        }
    }

    public static List<SupplierResponse> getSupplierList() {
        List<SupplierResponse> list = Lists.newArrayList();
        list.add(new SupplierResponse(1, "上海信息技术有限公司"));
        list.add(new SupplierResponse(2, "上海商贸有限公司"));
        list.add(new SupplierResponse(3, "常州电子商务优先公司"));
        list.add(new SupplierResponse(4, "深圳环保科技有限公司"));
        list.add(new SupplierResponse(5, "杭州通信设备有限公司"));
        return list;
    }

    public static List<CategoryResponse> getCategoryAll() {
        List<CategoryResponse> list = Lists.newArrayList();
        list.add(new CategoryResponse(1, "数码", 0));
        list.add(new CategoryResponse(2, "电脑办公", 0));
        list.add(new CategoryResponse(3, "家用电器", 0));

        list.add(new CategoryResponse(4, "摄影摄像", 1));
        list.add(new CategoryResponse(5, "影音娱乐", 1));
        list.add(new CategoryResponse(6, "数码配件", 1));

        list.add(new CategoryResponse(7, "数码单反", 4));
        list.add(new CategoryResponse(8, "单反相机", 4));
        list.add(new CategoryResponse(9, "摄像头", 4));

        list.add(new CategoryResponse(10, "MP3", 5));
        list.add(new CategoryResponse(11, "音箱", 5));
        list.add(new CategoryResponse(12, "耳机", 5));

        list.add(new CategoryResponse(13, "滤镜", 6));
        list.add(new CategoryResponse(14, "闪光灯", 6));
        list.add(new CategoryResponse(15, "存储器", 6));

        list.add(new CategoryResponse(16, "电脑整机", 2));
        list.add(new CategoryResponse(17, "笔记本", 16));

        list.add(new CategoryResponse(18, "生活电器", 3));
        list.add(new CategoryResponse(19, "吸尘器", 18));
        list.add(new CategoryResponse(20, "加湿器", 18));
        return list;
    }

    public static List<OrderResponse> getOrderList() {
        return list;
    }
}
