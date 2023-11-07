package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
