package com.sitrack.sitrackbackend.config.security.auth;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.repository.UserAccountRepository;
import com.sitrack.sitrackbackend.repository.UserAccountRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserAccountRepositoryCustom userAccountRepositoryCustom;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        UserAccount userEntity = userAccountRepositoryCustom.findByUserId(userId)
                .orElseThrow(()-> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : " + userId));

        if(userEntity.getUserId() != null){
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}