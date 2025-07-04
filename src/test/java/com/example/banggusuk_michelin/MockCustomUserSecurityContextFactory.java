package com.example.banggusuk_michelin;

import com.example.banggusuk_michelin.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockCustomUserSecurityContextFactory implements WithSecurityContextFactory<MockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(MockCustomUser annotation) {
        String username = StringUtils.hasLength(annotation.username()) ? annotation.username() : annotation.value();
        Assert.notNull(username, () -> annotation + " cannot have null username on both username and value properties");

        User user = new User();
        user.setNickname(username);
//        user.setUid(UUID.randomUUID().toString());
        user.setKeyCode(UUID.randomUUID().toString());

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(user, "", settingRole(annotation));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);

        return securityContext;
    }

    private List<GrantedAuthority> settingRole(MockCustomUser annotation){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for(String role: annotation.roles()){
            Assert.isTrue(!role.startsWith("ROLE_"), () -> "roles cannot start with ROLE_ GOT" + role);
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        return grantedAuthorities;
    }
}
