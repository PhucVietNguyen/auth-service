package com.phucviet.authorizationserver.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UserDetailsImpl extends User implements UserDetails, OAuth2User {

  private Map<String, Object> attributes;

  public UserDetailsImpl(User user) {
    super(
        user.getId(),
        user.getUsername(),
        user.getPassword(),
        user.getEmail(),
        user.getImageUrl(),
        user.getProvider(),
        user.getProviderId(),
        user.isEnabled(),
        user.isAccountNonExpired(),
        user.isCredentialsNonExpired(),
        user.isAccountNonLocked(),
        user.getRoles());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    getRoles()
        .forEach(
            role -> {
              grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
              role.getPermissions()
                  .forEach(
                      permission -> {
                        grantedAuthorities.add(
                            new SimpleGrantedAuthority(permission.getName().name()));
                      });
            });
    return grantedAuthorities;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public String getUsername() {
    return super.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return super.isEnabled();
  }

  @Override
  public String getName() {
    return String.valueOf(super.getId());
  }
}
