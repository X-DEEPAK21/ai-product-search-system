package com.ai.AiSearch.authservice;

import com.ai.AiSearch.entity.RealUser;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private RealUser realUser;

   public UserDetailsImpl(RealUser realUser){
      this.realUser=realUser;
   }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(realUser.getRole().name()));
    }

    @Override
    public @Nullable String getPassword() {
         return this.realUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.realUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.realUser.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.realUser.isVerified();
    }
}
