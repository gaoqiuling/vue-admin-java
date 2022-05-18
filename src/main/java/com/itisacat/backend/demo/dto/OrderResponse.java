package com.itisacat.backend.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class OrderResponse implements Serializable {
    private Integer orderId;

    private Integer supplierId;

    private String updateAuthor;

    private Integer afterSaleStatus;

    private Date afterSaleTime;

    private Integer categoryId;

    private String supplierName;

    private String categoryName;
}
