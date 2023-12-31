package com.sitrack.sitrackbackend.config.security.auth;

import com.sitrack.sitrackbackend.domain.account.UserAccount;
import com.sitrack.sitrackbackend.dto.UserAccountDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final UserAccount user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<GrantedAuthority>();
        collectors.add(()->{return "ROLE_"+user.getRoleType();});
        return collectors;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    public UserAccountDto todto(){
        return UserAccountDto.of(
                user.getUserId(),
                user.getUserPassword(),
                user.getUserName(),
                user.getUserEmail(),
                user.getUserPhoneNumber(),
                user.getRoleType()
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}