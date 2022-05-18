package com.itisacat.backend.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse implements Serializable {
    private Integer supplierId;

    private String supplierName;
}
