package com.authority.repository;

import com.authority.bean.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,String> {

    @Query("from UserRole u where u.userId=:userId")
    List<UserRole> findUserRoleByUserId(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query("delete from UserRole u where u.userId=?1 and u.roleId in (?2)")
    void deleteUserRoleForRoleIds(String userId, List<String> list);

    @Modifying
    @Transactional
    void deleteByUserIdEquals(String userId);

}
