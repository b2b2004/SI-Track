package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
    List<CartItem> findByCartId(Long id);
}
