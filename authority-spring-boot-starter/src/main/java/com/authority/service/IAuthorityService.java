package com.authority.service;


import com.authority.bean.Role;
import com.authority.bean.UserRole;
import com.authority.bean.dto.ResourceDto;
import com.authority.bean.dto.RoleDto;

import java.util.List;
import java.util.Map;

public interface IAuthorityService {
    /**
     * 根据用户查找角色
     * @param userId
     * @return
     */
    List<UserRole> findUserRole(String userId);

    /**
     * 根据roleId查找role
     * @param list
     * @return
     */
    List<Role> findRoleByList(List<String> list);

    /**
     * 根据用户查找菜单
     * @param userId
     * @return
     */
    List<ResourceDto> getUserAuthorityV2(String userId);

    /**
     * 保存角色
     * @param role
     * @return
     */
    RoleDto saveRole(RoleDto role);

    /**
     * 保存资源
     * @param resource
     * @return
     */
    ResourceDto saveResource(ResourceDto resource);

    /**
     * 保存角色与资源的关系
     * @param map
     * @return
     */
    Integer saveRoleResource(Map<String, Object> map);

    /**
     * 根据角色Id列表删除角色
     * @param roleIds
     */
    void deleteRoles(List<String> roleIds);

    /**
     * 根据资源ID列表删除资源
     * @param resourceIds
     */
    void deleteResources(List<String> resourceIds);

    /**
     * 删除用户下的某些角色
     * @param map
     */
    void deleteUserRoleForRoleIds(Map<String, Object> map);

    /**根据用户删除该用户下所有角色
     *
     * @param userId
     */
    void deleteUserRoleForUserId(String userId);

    /**
     * 删除该角色下某些资源
     * @param map
     */
    void deleteRoleResourceForResourceIds(Map<String, Object> map);

    /**
     * 删除该角色下所有资源
     * @param roleId
     */
    void deleteRoleResourceForRoleId(String roleId);

    /**
     * 通过角色编码或用户查找角色列表
     * @param code
     * @param name
     * @return
     */
    List<RoleDto> queryRoles(String code, String name);

    /**
     * 查找用户下搜友角色
     * @param userId
     * @return
     */
    List<RoleDto> queryRoleForUserId(String userId);

    /**
     * 查找角色下所有资源
     * @param roleId
     * @return
     */
    List<ResourceDto> queryResourceForRoleId(String roleId);

    /**
     * 查找资源
     * @param code
     * @param codeName
     * @param levels
     * @param types
     * @return
     */
    List<ResourceDto> queryResources(String code, String codeName, List<Short> levels, List<Short> types);

    /**
     * 获取系统中所有资源，并形成资源目录树
     * @return
     */
    List<ResourceDto> getAllResources();
}
