package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
}
