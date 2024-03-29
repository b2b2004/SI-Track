package com.sitrack.sitrackbackend.domain;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.domain.constant.ProductImageType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Subnail;
import static com.sitrack.sitrackbackend.domain.constant.ProductImageType.Thumbnail;

@Getter
@ToString(callSuper = true)
@Entity
@Table(
        indexes = @Index(name = "idx_product_name", columnList = "productName")
)
public class Product extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Setter
    @JoinColumn(name = "user_id")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @Setter
    @JoinColumn(name = "category_id")
    @ManyToOne(optional = false)
    private Category category;

    @Setter
    @JoinColumn(name = "supplier_id")
    @ManyToOne(optional = false)
    private Supplier supplier;

    @Setter
    @Column(length = 50, nullable = false)
    private String productName;

    @Setter
    @Column(nullable = false)
    private Long productCost; // 상품 원가

    @Setter
    @Column(nullable = false)
    private Long productPrice; // 상품 판매 가격

    @Setter
    @Column(length = 1000)
    private String productDetail;

    @Setter
    @Column(nullable = false)
    private Long productStockQuantity; // 상품 재고 수량

    @Setter
    @Column(nullable = false)
    private Long productSalesQuantity; // 상품 판매 수량

    public Product(UserAccount userAccount, Category category, Supplier supplier, String productName,  Long productCost, Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity) {
        this.userAccount = userAccount;
        this.category = category;
        this.supplier = supplier;
        this.productName = productName;
        this.productCost = productCost;
        this.productPrice = productPrice;
        this.productDetail = productDetail;
        this.productStockQuantity = productStockQuantity;
        this.productSalesQuantity = productSalesQuantity;
    }

    public void addproductImage(ProductImage productImage){
        this.getProductImages().add(productImage);
    }

    public void addproductImages(List<ProductImage> productImage){
        this.getProductImages().addAll(productImage);
    }

    public void minusproductStockQuantity(Long amount) {
        this.setProductStockQuantity(this.getProductStockQuantity() - amount);
    }

    public String getThumbnailImage(){
        for(ProductImage productImage : this.productImages){
            if(productImage.getImageType() == Thumbnail){
                return productImage.getImagePath() + productImage.getSaveName();
            }
        }
        return null;
    }

    protected Product() {}

    public static Product of(UserAccount userAccount, Category category, Supplier supplier, String productName, Long productCost, Long productPrice, String productDetail, Long productStockQuantity, Long productSalesQuantity){
        return new Product(userAccount, category, supplier, productName, productCost, productPrice, productDetail, productStockQuantity, productSalesQuantity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
