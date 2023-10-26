package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    Long deleteByProductId(Long productId);
}
