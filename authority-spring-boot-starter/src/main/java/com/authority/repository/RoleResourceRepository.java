package com.authority.repository;

import com.authority.bean.RoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoleResourceRepository extends JpaRepository<RoleResource,String> {

    List<RoleResource> findRoleResourceByRoleIdInOrderByRoleIdAsc(List<String> list);

    List<RoleResource> findRoleResourceByRoleIdEquals(String roleId);

    @Modifying
    @Transactional
    @Query("delete from RoleResource u where u.roleId=?1 and u.resourceId in (?2)")
    void deleteRoleResourceForResourceIds(String roleId, List<String> resourceIds);

    @Modifying
    @Transactional
    void deleteByRoleIdEquals(String roleId);
}