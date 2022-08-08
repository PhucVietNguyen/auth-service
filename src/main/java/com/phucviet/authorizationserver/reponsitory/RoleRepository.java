package com.phucviet.authorizationserver.reponsitory;

import com.phucviet.authorizationserver.model.entity.RoleEntity;
import com.phucviet.authorizationserver.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(ERole name);
}
