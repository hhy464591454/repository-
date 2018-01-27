package com.authority.service.impl;

import com.authority.base.ListUtils;
import com.authority.base.UUIDUtils;
import com.authority.bean.Resource;
import com.authority.bean.Role;
import com.authority.bean.RoleResource;
import com.authority.bean.UserRole;
import com.authority.bean.dto.ResourceDto;
import com.authority.bean.dto.RoleDto;
import com.authority.repository.ResourceRepository;
import com.authority.repository.RoleRepository;
import com.authority.repository.RoleResourceRepository;
import com.authority.repository.UserRoleRepository;
import com.authority.service.IAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.*;

@Service
public class AuthorityService implements IAuthorityService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleResourceRepository roleResourceRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<UserRole> findUserRole(String userId) {
        return userRoleRepository.findUserRoleByUserId(userId);
    }
    @Override
    public List<Role> findRoleByList(List<String> list) {
        return roleRepository.findRoleByIdIn(list);
    }

    @Override
    public List<ResourceDto>  getUserAuthorityV2(String userId){
        //获取到用户下所有角色
        List<UserRole> userRolePoList = userRoleRepository.findUserRoleByUserId(userId);
        //角色ID集合
        List<String> roleIdList = new ArrayList<>();
        if (userRolePoList!=null && userRolePoList.size()>0){
            for (UserRole userRole :userRolePoList) {
                roleIdList.add(userRole.getRoleId());
            }
        }

        List<RoleResource> tBcRoleResourcePoList = roleResourceRepository.findRoleResourceByRoleIdInOrderByRoleIdAsc(roleIdList);
        //资源ID集合
        List<String> resourceIdList = new ArrayList<>();
        for (RoleResource roleResourcePo:tBcRoleResourcePoList) {
            //剔重
            if(!resourceIdList.contains(roleResourcePo.getResourceId())){
                resourceIdList.add(roleResourcePo.getResourceId());
            }
        }
        short type = 1000;
        List<Resource> bcRoleResourcePoList = resourceRepository.findResourceByIdInAndTypeEqualsOrderByLevelDescSortNumAsc(resourceIdList,type);

        //List<TBcResourcePo> resourceDTO = JpaUtils.nativeQueryForList(TBcResourcePo.class,"select bre.* from t_bc_user_role bur,t_bc_role_resource brr,t_bc_resource bre where bur.role_id=brr.role_id and brr.id=bre.id and bre.type = 1000 and bur.user_id='1234567890' order by bre.level desc ");
        return dataHandler(ListUtils.listCopy(bcRoleResourcePoList,ResourceDto.class));
    }

    private List<ResourceDto> dataHandler(List<ResourceDto> resourceDTOList){

        List<ResourceDto> resultList = new ArrayList<>();
        Map<String,ResourceDto> map = ListUtils.listToMap(resourceDTOList,"getId",ResourceDto.class);
        Iterator<Map.Entry<String, ResourceDto>> entries = map.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry<String, ResourceDto> entry = entries.next();
            ResourceDto resourceDTO = entry.getValue();
            if(resourceDTO !=null ){
                String pid = resourceDTO.getParentId();
                if(resourceDTO.getLevel()!=null && resourceDTO.getLevel()>0){
                    if(!StringUtils.isEmpty(pid)){
                        ResourceDto r = map.get(pid);
                        List<ResourceDto> list = r.getResourceDtoList();
                        if(list==null || list.size()<0){
                            list = new ArrayList<>();
                        }
                        list.add(resourceDTO);
                        r.setResourceDtoList(list);
                        map.put(pid,r);
                    }
                }
            }
        }
        Iterator<Map.Entry<String, ResourceDto>> es = map.entrySet().iterator();
        while (es.hasNext()){
            Map.Entry<String, ResourceDto> e = es.next();
            ResourceDto resourceDTO = e.getValue();
            if(resourceDTO !=null ){
                if(resourceDTO.getLevel()!=null && resourceDTO.getLevel()>0 && resourceDTO.getLevel()==1){
                    resultList.add(resourceDTO);
                }
            }
        }
        return resultList;
    }

    @Override
    public RoleDto saveRole(RoleDto role) {
        Role rolePo = new Role();
        BeanUtils.copyProperties(role,rolePo);
        if(role.getId()==null || "".equals(role.getId())){
            rolePo.setId(UUIDUtils.insecureRandomString(32));
            rolePo.setEnable(1);
            rolePo.setCreateDate(new Timestamp(new Date().getTime()));
            rolePo.setCreateUser("1");
        }else{
            rolePo.setUpdateDate(new Timestamp(new Date().getTime()));
            rolePo.setUpdateUser("1");
        }
        try {
            Role r = roleRepository.save(rolePo);
            BeanUtils.copyProperties(r,role);
        }catch (Exception e){
            logger.error("保存角色失败. info:{}",e);
            throw new RuntimeException("保存角色失败");
        }
        return role;
    }

    @Override
    public ResourceDto saveResource(ResourceDto resource) {
        Resource resourcePo = new Resource();
        BeanUtils.copyProperties(resource,resourcePo);
        if(StringUtils.isEmpty(resource.getId())){
            resourcePo.setId(UUIDUtils.insecureRandomString(32));
            resourcePo.setEnable(1);
            resourcePo.setCreateUser("1");
            resourcePo.setCreateDate(new Timestamp(new Date().getTime()));
        }else{
            resourcePo.setUpdateDate(new Timestamp(new Date().getTime()));
            resourcePo.setUpdateUser("1");
        }
        try {
            Resource r = resourceRepository.save(resourcePo);
            BeanUtils.copyProperties(r,resourcePo);
        }catch (Exception e){
            logger.error("保存资源失败. info:{}",e);
            throw new RuntimeException("保存资源失败");
        }
        return resource;
    }

//    @Transactional
//    @Override
//    public Integer saveUserRole(TBcUserDTO user) {
//        String userId = user.getId();
//        List<String> roleIds = user.getRoleIds();
//        List<TBcUserRolePo> list = new ArrayList<>();
//        roleIds.stream().forEach(
//            p ->{
//                TBcUserRolePo userRolePo = new TBcUserRolePo();
//                userRolePo.setId(UUIDUtils.insecureRandomString(32));
//                userRolePo.setUserId(userId);
//                userRolePo.setRoleId(p);
//                userRolePo.setEnable(1);
//                userRolePo.setCreateDate(new Timestamp(new Date().getTime()));
//                userRolePo.setUpdateDate(new Timestamp(new Date().getTime()));
//                userRolePo.setCreateUser("1");userRolePo.setUpdateUser("1");
//                list.add(userRolePo);
//            }
//        );
//        Integer i = 1;
//        if(list.size()>0){
//            try {
//                TBcUserPo u = new TBcUserPo();
//                BeanUtils.copyProperties(user,u);
//                u.setCreateDate(new Timestamp(new Date().getTime()));
//                u.setUpdateDate(new Timestamp(new Date().getTime()));
//                u.setCreateUser("1");u.setUpdateUser("1");
//                userRepository.save(u);
//            }catch (Exception e){
//                logger.error("保存用户失败. info:{}",e);
//                throw new RuntimeException("保存用户失败");
//            }
//
//            try {
//                //先删除所有的 在全部添加
//                userRoleRepository.deleteByUserIdEquals(userId);
//                userRoleRepository.save(list).size();
//            }catch (Exception e){
//                logger.error("保存用户关系失败. info:{}",e);
//                throw new RuntimeException("保存用户关系失败");
//            }
//        }
//        return i;
//    }

    @Transactional
    @Override
    public Integer saveRoleResource(Map<String, Object> map){
        String roleId = String.valueOf(map.get("roleId"));
        List<String> resourceIds = (List<String>) map.get("resourceIds");
        Short type = Short.valueOf(String.valueOf(map.get("type")));

        List<RoleResource> list = new ArrayList<>();
        resourceIds.stream().forEach(
                p ->{
                    RoleResource roleResourcePo = new RoleResource();
                    roleResourcePo.setId(UUIDUtils.insecureRandomString(32));
                    roleResourcePo.setRoleId(roleId);
                    roleResourcePo.setType(type);
                    roleResourcePo.setResourceId(p);
                    roleResourcePo.setEnable(1);
                    roleResourcePo.setCreateDate(new Timestamp(new Date().getTime()));
                    roleResourcePo.setUpdateDate(new Timestamp(new Date().getTime()));
                    roleResourcePo.setCreateUser("1");roleResourcePo.setUpdateUser("1");
                    list.add(roleResourcePo);
                }
        );
        Integer i = -1;
        if(list.size()>0){
            try {
                //删除角色下所有资源
                roleResourceRepository.deleteByRoleIdEquals(roleId);
                i = roleResourceRepository.save(list).size();
            }catch (Exception e){
                logger.error("保存资源关系失败. info:{}",e);
                throw new RuntimeException("保存资源关系失败");
            }
        }
        return i;
    }

    @Override
    public void deleteRoles(List<String> roleIds){

        List<Role> list = new ArrayList<>();
        roleIds.stream().forEach(
                p ->{ Role rolePo = new Role();rolePo.setId(p);list.add(rolePo);}
        );
        if(roleIds.size()>0){
            try {
                roleRepository.delete(list);
            }catch (Exception e){
                logger.error("删除角色失败. info:{}",e);
                throw new RuntimeException("删除角色失败");
            }
        }
    }

    @Override
    public void deleteResources(List<String> resourceIds){
        List<Resource> list = new ArrayList<>();
        resourceIds.stream().forEach(
                p ->{ Resource resourcePo = new Resource();resourcePo.setId(p);list.add(resourcePo);}
        );
        if(list.size()>0){
            try {
                resourceRepository.delete(list);
            }catch (Exception e){
                logger.error("删除资源失败. info:{}",e);
                throw new RuntimeException("删除资源失败");
            }
        }
    }

    @Override
    public void deleteUserRoleForRoleIds(Map<String, Object> map){
        if(map != null && map.size()>0){
            String userId = String.valueOf(map.get("userId"));
            List<String> roleIds = (List<String>) map.get("roleIds");
            try {
                userRoleRepository.deleteUserRoleForRoleIds(userId,roleIds);
            }catch (Exception e){
                logger.error("删除用户关系失败. info:{}",e);
                throw new RuntimeException("删除用户关系失败");
            }
        }

    }

    @Override
    public void deleteUserRoleForUserId(String userId) {
        try {
            userRoleRepository.deleteByUserIdEquals(userId);
        }catch (Exception e){
            logger.error("删除用户关系失败. info:{}",e);
            throw new RuntimeException("删除用户关系失败");
        }
    }

    @Override
    public void deleteRoleResourceForResourceIds(Map<String, Object> map) {
        String roleId = String.valueOf(map.get("roleId"));
        List<String> resourceIds = (List<String>) map.get("resourceIds");
        try {
            roleResourceRepository.deleteRoleResourceForResourceIds(roleId,resourceIds);
        }catch (Exception e){
            logger.error("删除资源关系失败. info:{}",e);
            throw new RuntimeException("删除资源关系失败");
        }
    }

    @Override
    public void deleteRoleResourceForRoleId(String roleId){
        try {
            roleResourceRepository.deleteByRoleIdEquals(roleId);
        }catch (Exception e){
            logger.error("删除用户关系失败. info:{}",e);
            throw new RuntimeException("删除用户关系失败");
        }
    }

    @Override
    public List<RoleDto> queryRoles(String code, String name){
        return ListUtils.listCopy(
                roleRepository.findRoleByCodeContainingAndNameContaining(code,name)
                ,RoleDto.class);
    }

    @Override
    public List<RoleDto> queryRoleForUserId(String userId) {
        List<RoleDto> dtoList = null;
        List<UserRole> pList = userRoleRepository.findUserRoleByUserId(userId);
        if(pList!= null && pList.size()>0){
            List<String> roleIds = new ArrayList<>();
            pList.stream().forEach(
                    p-> {roleIds.add(p.getRoleId());}
            );
            List<Role> poList = roleRepository.findRoleByIdIn(roleIds);
            if(poList!= null && poList.size()>0){
                dtoList = ListUtils.listCopy(poList,RoleDto.class);
            }
        }
        return dtoList;
    }

    @Override
    public List<ResourceDto> queryResourceForRoleId(String roleId) {
        List<ResourceDto> dtoList = null;
        List<RoleResource> pList = roleResourceRepository.findRoleResourceByRoleIdEquals(roleId);
        if(pList!= null && pList.size()>0){
            List<String> resourceIds = new ArrayList<>();
            pList.stream().forEach(
                    p-> {resourceIds.add(p.getResourceId());}
            );
            List<Resource> poList = resourceRepository.findResourceByIdIn(resourceIds);
            if(poList!= null && poList.size()>0){
                dtoList = ListUtils.listCopy(poList,ResourceDto.class);
            }
        }
        return dtoList;
    }

    @Override
    public List<ResourceDto> queryResources(String code, String codeName, List<Short> levels, List<Short> types){
        return ListUtils.listCopy(
                resourceRepository.
                        findResourceByCodeContainingAndCodeNameContainingAndLevelInAndTypeIn(code,codeName,levels,types)
        ,ResourceDto.class);
    }

    @Override
    public List<ResourceDto> getAllResources() {
        short type = 1000;
        List<Resource> bcRoleResourcePoList = resourceRepository.findResourceByTypeEqualsOrderByLevelDescSortNumAsc(type);
        return dataHandler(ListUtils.listCopy(bcRoleResourcePoList,ResourceDto.class));
    }

}
