package com.itisacat.backend.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse implements Serializable {
    private Integer categoryId;

    private String categoryName;

    private Integer parentId;
}
