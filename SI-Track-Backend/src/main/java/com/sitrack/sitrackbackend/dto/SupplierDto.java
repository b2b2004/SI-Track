package com.sitrack.sitrackbackend.dto;

import com.sitrack.sitrackbackend.domain.Supplier;

public record SupplierDto(
         Long supplierId,
         String supplierName,
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
                supplierId,
                supplierName,
                supplierCode
        );
    }

}
