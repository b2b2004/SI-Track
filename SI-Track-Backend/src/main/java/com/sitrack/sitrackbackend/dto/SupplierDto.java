package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Supplier;

import javax.validation.constraints.NotBlank;

public record SupplierDto(
         Long supplierId,

         @NotBlank(message = "supplierName is Null")
         String supplierName,

         @NotBlank(message = "supplierCode is Null")
         String supplierCode
) {

    public static SupplierDto of(Long supplierId, String supplierName, String supplierCode){
        return new SupplierDto(supplierId, supplierName, supplierCode);
    }

    public static SupplierDto from(Supplier entity){
        return new SupplierDto(
                entity.getId(),
                entity.getSupplierName(),
                entity.getSupplierCode()
        );
    }

    public Supplier toEntity(){
        return Supplier.of(
                supplierName,
                supplierCode
        );
    }

}
