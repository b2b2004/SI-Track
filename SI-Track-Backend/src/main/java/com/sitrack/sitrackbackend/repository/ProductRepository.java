package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
