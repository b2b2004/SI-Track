package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.Cart;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUserAccount(UserAccount user);
}
