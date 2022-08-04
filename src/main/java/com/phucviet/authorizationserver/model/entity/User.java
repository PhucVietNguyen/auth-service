package com.phucviet.authorizationserver.model.entity;

import com.phucviet.authorizationserver.model.enums.ESocialProvider;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "image_url")
  private String imageUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider")
  private ESocialProvider provider;

  @Column(name = "provider_id")
  private String providerId;

  @Column(name = "enabled", columnDefinition = "boolean default true")
  private boolean enabled;

  @Column(name = "account_non_expired", columnDefinition = "boolean default true")
  private boolean accountNonExpired;

  @Column(name = "credentials_non_expired", columnDefinition = "boolean default true")
  private boolean credentialsNonExpired;

  @Column(name = "account_non_locked", columnDefinition = "boolean default true")
  private boolean accountNonLocked;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "role_user",
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
  private List<Role> roles;
}
