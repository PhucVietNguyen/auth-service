package com.phucviet.authorizationserver.model.entity;

import com.phucviet.authorizationserver.model.enums.ERole;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class RoleEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private ERole name;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "permission_role",
      joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
  private List<PermissionEntity> permissions;
}
