package com.phucviet.authorizationserver.reponsitory;

import com.phucviet.authorizationserver.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {}
