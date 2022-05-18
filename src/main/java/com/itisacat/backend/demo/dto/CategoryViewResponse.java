package com.itisacat.backend.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryViewResponse implements Serializable {
    private Integer categoryId1;
    private Integer categoryId2;
    private Integer categoryId3;
    private List<CategoryResponse> category1List;
    private List<CategoryResponse> category2List;
    private List<CategoryResponse> category3List;
}
