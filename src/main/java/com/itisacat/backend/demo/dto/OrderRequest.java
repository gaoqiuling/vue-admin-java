package com.itisacat.backend.demo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Data
public class OrderRequest implements Serializable {
    private Integer orderId;

    private Integer supplierId;

    private String updateAuthor;

    private Integer afterSaleStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleStartTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleEndTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date afterSaleTime;

    private Integer categoryId;

    private Integer pageNum;

    private Integer pageSize;

    private Boolean closeOverTime;
}
