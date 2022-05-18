package com.itisacat.backend.demo.controller;

import com.google.common.collect.Lists;
import com.itisacat.backend.demo.Config;
import com.itisacat.backend.demo.dto.*;
import com.itisacat.backend.demo.utils.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/backend/")
public class DemoController {
    @GetMapping(value = "get_supplier_list")
    public ResultBean<List<SupplierResponse>> getSupplierList() {
        return new ResultBean(Config.getSupplierList());
    }

    @GetMapping(value = "get_category_list")
    public ResultBean<List<CategoryResponse>> getCategoryList(@RequestParam("parentId") Integer parentId) {
        return new ResultBean(Config.getCategoryAll().stream().filter(t -> t.getParentId().equals(parentId)).collect(Collectors.toList()));
    }

    @GetMapping(value = "get_category_view")
    public ResultBean<CategoryViewResponse> getCategoryView(@RequestParam Integer categoryId) {
        List<CategoryResponse> categoryList = Config.getCategoryAll();
        CategoryViewResponse response = new CategoryViewResponse();
        response.setCategory1List(categoryList.stream().filter(t -> t.getParentId().equals(0)).collect(Collectors.toList()));

        CategoryResponse category3 = categoryList.stream().filter(t -> t.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        if (category3 == null) {
            return new ResultBean(response);
        }
        CategoryResponse category2 = categoryList.stream().filter(t -> t.getCategoryId().equals(category3.getParentId())).findFirst().orElse(null);
        if (category2 == null) {
            return new ResultBean(response);
        }
        CategoryResponse category1 = categoryList.stream().filter(t -> t.getCategoryId().equals(category2.getParentId())).findFirst().orElse(null);
        if (category1 == null) {
            return new ResultBean(response);
        }
        response.setCategoryId1(category1.getCategoryId());
        response.setCategoryId2(category2.getCategoryId());
        response.setCategoryId3(categoryId);
        response.setCategory2List(categoryList.stream().filter(t -> t.getParentId().equals(category2.getParentId())).collect(Collectors.toList()));
        response.setCategory3List(categoryList.stream().filter(t -> t.getParentId().equals(category3.getParentId())).collect(Collectors.toList()));
        return new ResultBean(response);
    }

    @PostMapping(value = "order/page_list")
    public ResultBean<PageInfo<OrderResponse>> getOrderPageList(@RequestBody OrderRequest request) {
        if (request == null || request.getPageNum() == null || request.getPageSize() == null) {
            throw new BusinessException(-40001, "param error");
        }
        List<OrderResponse> list = Config.getOrderList();
        if (request.getOrderId() != null) {
            list = list.stream().filter(t -> t.getOrderId().equals(request.getOrderId())).collect(Collectors.toList());
        }
        if (request.getSupplierId() != null) {
            list = list.stream().filter(t -> t.getSupplierId().equals(request.getSupplierId())).collect(Collectors.toList());
        }
        if (request.getAfterSaleStatus() != null) {
            list = list.stream().filter(t -> t.getAfterSaleStatus().equals(request.getAfterSaleStatus())).collect(Collectors.toList());
        }
        if (request.getCategoryId() != null) {
            list = list.stream().filter(t -> t.getCategoryId().equals(request.getCategoryId())).collect(Collectors.toList());
        }
        if (request.getUpdateAuthor() != null&& StringUtils.isNoneEmpty(request.getUpdateAuthor())) {
            list = list.stream().filter(t -> t.getUpdateAuthor().equals(request.getUpdateAuthor())).collect(Collectors.toList());
        }
        if (request.getAfterSaleStartTime() != null && request.getAfterSaleEndTime() != null) {
            Date endDate = DateUtils.addDays(request.getAfterSaleEndTime(), 1);
            list = list.stream().filter(t -> t.getAfterSaleTime().getTime() >= request.getAfterSaleStartTime().getTime() && t.getAfterSaleTime().getTime() < endDate.getTime()).collect(Collectors.toList());
        }
        if (request.getCloseOverTime() != null && request.getCloseOverTime()) {
            Date date = DateUtils.addDays(new Date(), -1);
            list = list.stream().filter(t -> t.getAfterSaleTime().getTime() < date.getTime()).collect(Collectors.toList());
        }
        PageInfo<OrderResponse> pageInfo = new PageInfo<>();
        pageInfo.setTotal(list.size());
        pageInfo.setList(Lists.newArrayList());
        if (list.size() <= (request.getPageNum() - 1) * request.getPageSize()) {
            return new ResultBean(pageInfo);
        }
        int toIndex = Math.min(list.size(), request.getPageNum() * request.getPageSize());
        list = list.subList((request.getPageNum() - 1) * request.getPageSize(), toIndex);
        pageInfo.setList(list);
        List<SupplierResponse> supplierList = Config.getSupplierList();
        List<CategoryResponse> categoryList = Config.getCategoryAll();
        pageInfo.getList().forEach(t -> {
            t.setSupplierName("");
            t.setCategoryName("");
            SupplierResponse supplierResponse = supplierList.stream().filter(q -> q.getSupplierId().equals(t.getSupplierId())).findFirst().orElse(null);
            if (supplierResponse != null) {
                t.setSupplierName(supplierResponse.getSupplierName());
            }
            CategoryResponse categoryResponse = categoryList.stream().filter(q -> q.getCategoryId().equals(t.getCategoryId())).findFirst().orElse(null);
            if (categoryResponse != null) {
                t.setCategoryName(categoryResponse.getCategoryName());
            }
        });
        return new ResultBean(pageInfo);
    }

    @GetMapping(value = "order/info")
    public ResultBean<OrderResponse> getOrderInfo(@RequestParam Integer orderId) {
        OrderResponse orderInfo = Config.getOrderList().stream().filter(t -> t.getOrderId().equals(orderId)).findFirst().orElse(null);
        if (orderInfo == null) {
            throw new BusinessException(-40300, "订单不存在");
        }
        return new ResultBean(orderInfo);
    }

    @PostMapping(value = "order/edit")
    public ResultBean<Integer> editOrder(@RequestBody OrderRequest request) {
        if (request == null || request.getOrderId() == null
                || request.getSupplierId() == null
                || request.getAfterSaleStatus() == null
                || request.getAfterSaleTime() == null
                || request.getCategoryId() == null) {
            throw new BusinessException(-40001, "param error");
        }
        List<OrderResponse> orderList = Config.getOrderList();
        OrderResponse orderInfo = orderList.stream().filter(t -> t.getOrderId().equals(request.getOrderId())).findFirst().orElse(null);
        if (orderInfo == null) {
            throw new BusinessException(-40300, "订单不存在");
        }
        orderInfo.setSupplierId(request.getSupplierId());
        orderInfo.setAfterSaleStatus(request.getAfterSaleStatus());
        orderInfo.setCategoryId(request.getCategoryId());
        orderInfo.setAfterSaleTime(request.getAfterSaleTime());
        return new ResultBean(1);
    }

    @PostMapping(value = "order/add")
    public ResultBean<Integer> addOrder(@RequestBody OrderRequest request) {
        if (request == null
                || request.getSupplierId() == null
                || request.getAfterSaleStatus() == null
                || request.getAfterSaleTime() == null
                || request.getCategoryId() == null) {
            throw new BusinessException(-40001, "param error");
        }
        List<OrderResponse> orderList = Config.getOrderList();
        OrderResponse lastOrderInfo = orderList.stream().max(Comparator.comparing(OrderResponse::getOrderId)).get();
        OrderResponse orderInfo = new OrderResponse();
        orderInfo.setOrderId(lastOrderInfo.getOrderId() + 1);
        orderInfo.setUpdateAuthor("test" + orderList.size());
        orderInfo.setSupplierId(request.getSupplierId());
        orderInfo.setAfterSaleStatus(request.getAfterSaleStatus());
        orderInfo.setCategoryId(request.getCategoryId());
        orderInfo.setAfterSaleTime(request.getAfterSaleTime());
        orderList.set(0, orderInfo);
        return new ResultBean(1);
    }

    @PostMapping(value = "order/delete")
    public ResultBean<Integer> deleteOrder(@RequestBody OrderRequest request) {
        if (request == null || request.getOrderId() == null) {
            throw new BusinessException(-40001, "param error");
        }
        List<OrderResponse> orderList = Config.getOrderList();
        OrderResponse orderInfo = orderList.stream().filter(t -> t.getOrderId().equals(request.getOrderId())).findFirst().orElse(null);
        if (orderInfo == null) {
            throw new BusinessException(-40300, "订单不存在");
        }
        orderList.remove(orderInfo);
        return new ResultBean(1);
    }
}