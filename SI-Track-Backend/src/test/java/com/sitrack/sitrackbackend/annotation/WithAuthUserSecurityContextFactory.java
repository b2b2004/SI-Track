package com.sitrack.sitrackbackend.annotation;

import com.sitrack.sitrackbackend.config.security.auth.PrincipalDetails;
import com.sitrack.sitrackbackend.domain.account.UserAccount;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthUserSecurityContextFactory implements WithSecurityContextFactory<WithAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithAuthUser annotation) {
        String userId = annotation.userId();
        String role = annotation.role();

        UserAccount userAccount = new UserAccount(userId, "userPassword", "userName", "userEmail", "userPhoneNumber");
        PrincipalDetails principalDetails = new PrincipalDetails(userAccount);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(principalDetails, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }

    /*
        @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        List<GrantedAuthority> grantedAuthorities = new ArrayList();
        grantedAuthorities.add(new SimpleGrantedAuthority("USER"));
        User principal = new User(customUser.username(), "1234", true, true, true, true,
            grantedAuthorities);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            principal, principal.getPassword(), principal.getAuthorities());

        authentication.setDetails(new Detail(customUser.username(), "aaaa"));
        context.setAuthentication(authentication);
        return context;
    }
     */
}
