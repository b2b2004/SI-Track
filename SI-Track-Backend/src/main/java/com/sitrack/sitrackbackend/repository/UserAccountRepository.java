package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
    Optional<UserAccount> findByUserId(String userId);
    Optional<UserAccount> findByUserNameAndUserEmail(String userName, String userEmail);
    Optional<UserAccount> findByUserIdAndUserEmail(String userId, String userEmail);
}
