package com.sitrack.sitrackbackend.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String supplierName;

    @Column(length = 50, nullable = false)
    private String supplierCode;

    protected Supplier() {}

    public Supplier(Long id, String supplierName, String supplierCode) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
    }

    public static Supplier of(Long id, String supplierName, String supplierCode){
        return new Supplier(id, supplierName, supplierCode);
    }
}
