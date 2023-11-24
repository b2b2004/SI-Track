package com.sitrack.sitrackbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @Setter
    @Column(length = 50, nullable = false)
    private String supplierName;

    @Setter
    @Column(length = 50, nullable = false)
    private String supplierCode;

    protected Supplier() {}

    public Supplier(String supplierName, String supplierCode) {
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
    }

    public Supplier(Long id, String supplierName, String supplierCode) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
    }

    public static Supplier of(String supplierName, String supplierCode){
        return new Supplier(supplierName, supplierCode);
    }

    public static Supplier of(Long id, String supplierName, String supplierCode){
        return new Supplier(id, supplierName, supplierCode);
    }
}
