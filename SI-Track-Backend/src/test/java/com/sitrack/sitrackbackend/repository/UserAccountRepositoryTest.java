package com.sitrack.sitrackbackend.repository;

import com.sitrack.sitrackbackend.config.JpaConfig;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@DataJpaTest
public class UserAccountRepositoryTest {

//    @Autowired
//    private UserAccountRepositoryCustom userAccountRepositoryCustom;
//
//    @Test
//    public void startQuery(){
//        List<UserAccount> users = userAccountRepositoryCustom.find();
//        assertThat(users.size()).isEqualTo(10);
//    }

}
