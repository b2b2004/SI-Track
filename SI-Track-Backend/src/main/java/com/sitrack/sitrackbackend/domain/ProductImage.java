package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @Setter
    @JoinColumn(name = "product_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    @Setter
    @Column(length = 100)
    private String originalName; // 기존 파일명

    @Setter
    @Column(length = 100)
    private String saveName; // 저장된 파일명

    @Setter
    @Column(length = 200)
    private String imagePath; // 파일 경로

    @Setter
    @Enumerated(value = EnumType.STRING)
    private ProductImageType imageType; // 이미지 타입 -> 썸네일, 서브네일 구분

    protected ProductImage(){}

    public ProductImage(Product product, String originalName, String saveName, String imagePath, ProductImageType imageType) {
        this.product = product;
        this.originalName = originalName;
        this.saveName = saveName;
        this.imagePath = imagePath;
        this.imageType = imageType;
    }

    public static ProductImage of(Product product, String originalName, String saveName, String imagePath, ProductImageType imageType){
        return new ProductImage(product, originalName, saveName, imagePath, imageType);
    }

}
