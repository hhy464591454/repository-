package com.authority.repository;

import com.authority.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

    List<Role> findRoleByIdIn(List<String> list);

    List<Role> findAllByAndEnableEquals(Integer enable);

    List<Role> findRoleByCodeContainingAndNameContaining(String code, String name);
}
