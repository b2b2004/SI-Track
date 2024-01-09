package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    Long deleteByProductId(Long productId);
    List<ProductImage> findByProductId(Long productId);
}
