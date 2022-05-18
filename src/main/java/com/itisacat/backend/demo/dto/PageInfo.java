package com.itisacat.backend.demo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class PageInfo<T> implements Serializable {
    private int pageNum;
    private int pageSize;
    private int total;
    private List<T> list;
}
