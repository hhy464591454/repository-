package com.authority.controller;

import com.authority.base.AuthorityConstant;
import com.authority.base.CodeConstants;
import com.authority.base.ResponseModel;
import com.authority.bean.dto.ResourceDto;
import com.authority.bean.dto.RoleDto;
import com.authority.service.IAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Controller
@RequestMapping("/authority/")
@Api(value = "Rights management and update",tags = "authority")
public class AuthorityController {

//******************************************** authority 添加begin ***************************************************

    @Autowired
    private IAuthorityService authorityService;

    @ApiOperation("根据用户获取菜单列表")
    @GetMapping(value="/findMenus")
    @ResponseBody
    public <T> ResponseModel<T> findMenus(String userId){
        ResponseModel<T> responseModel = ResponseModel.build();
        if (StringUtils.isEmpty(userId)) {
            responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl);
            responseModel.setMessage("未获取到用户信息");
            return responseModel;
        }
        List<ResourceDto> list = authorityService.getUserAuthorityV2(userId);
        responseModel.setCode(CodeConstants.OK);
        responseModel.setData((T)list);
        return responseModel;
    }

    @ApiOperation("添加角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "角色编号（不可重复）",paramType="query",required =true,dataType = "String"),
            @ApiImplicitParam(name = "name", value = "角色名称",paramType="query",required =true,dataType = "String"),
            @ApiImplicitParam(name = "sortNum", value = "序号",paramType="query",required =false,dataType = "short"),
            @ApiImplicitParam(name = "id", value = "主键",paramType="query",required =false,dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "详细说明",paramType="query",required =false,dataType = "String")
    })
    @GetMapping("/saveRole")
    @ResponseBody
    public <T> ResponseModel<T> saveRole(@Param("code")String code, @Param("name")String name, @Param("id")String id, @Param("sortNum")Short sortNum, @Param("remark")String remark){

        ResponseModel<T> responseModel = ResponseModel.build();
        if (StringUtils.isEmpty(code)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("编号不能为空");
            return responseModel;
        }
        if (StringUtils.isEmpty(name)) { responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl);responseModel.setMessage("名称不能为空");
            return responseModel;
        }

        RoleDto role = new RoleDto();
        role.setCode(code);role.setName(name);role.setId(id);role.setRemark(remark);
        if(sortNum == null || sortNum<=0){
            sortNum = 1;
        }
        role.setSortNum(sortNum);

        RoleDto r = authorityService.saveRole(role);
        responseModel.setCode(CodeConstants.OK);
        responseModel.setData((T)r);
        return responseModel;
    }


    @ApiOperation("添加资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "资源编号",paramType="query",required =true,dataType = "String"),
            @ApiImplicitParam(name = "codeName", value = "资源名称",paramType="query",required =true,dataType = "String"),
            @ApiImplicitParam(name = "level", value = "级联级别(type=1000时有效，且必填)",paramType="query",required =false,dataType = "int"),
            @ApiImplicitParam(name = "type", value = "类型",paramType="query",required =true,dataType = "int"),
            @ApiImplicitParam(name = "sortNum", value = "序号",paramType="query",required =false,dataType = "int"),
            @ApiImplicitParam(name = "id", value = "id",paramType="query",required =false,dataType = "String"),
            @ApiImplicitParam(name = "parentId", value = "父级资源id",paramType="query",required =false,dataType = "String"),
            @ApiImplicitParam(name = "remark", value = "详细说明",paramType="query",required =false,dataType = "String")
    })
    @GetMapping("/saveResource")
    @ResponseBody
    public <T> ResponseModel<T> saveResource(@Param("code")String code, @Param("codeName")String codeName,@Param("level")Short level,
                                             @Param("type")Short type,@Param("sortNum")Short sortNum,@Param("parentId")String parentId,
                                             @Param("remark")String remark,@Param("id")String id
    ){

        ResponseModel<T> responseModel = ResponseModel.build();
        if (StringUtils.isEmpty(code)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("编号不能为空");
            return responseModel;
        }
        if (StringUtils.isEmpty(codeName)) { responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl);responseModel.setMessage("名称不能为空");
            return responseModel;
        }
        if (StringUtils.isEmpty(type)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("类型不能为空");
            return responseModel;
        }else{
            if(!AuthorityConstant.containResourceType(type)){
                responseModel.setCode(CodeConstants.PREREQUISITE_NOT_SUPPORT); responseModel.setMessage("资源类型只能是1000(菜单)、2000(操作)、3000(文件、API等)");
                return responseModel;
            }
            //类型为菜单是 级练级别不能为空
            if(type== AuthorityConstant.RESOURCE_TYPE_1000){
                if (StringUtils.isEmpty(level)) { responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl);responseModel.setMessage("级联级别不能为空");
                    return responseModel;
                }
            }
        }

        ResourceDto resource = new ResourceDto();
        resource.setCode(code);resource.setCodeName(codeName);resource.setRemark(remark);
        resource.setLevel(level);resource.setType(type);resource.setParentId(parentId);resource.setId(id);
        if(sortNum == null || sortNum<=0){
            sortNum = 1;
        }
        resource.setSortNum(sortNum);

        ResourceDto r = authorityService.saveResource(resource);
        responseModel.setCode(CodeConstants.OK);
        responseModel.setData((T)r);
        return responseModel;
    }

    @PostMapping("/saveRoleResource")
    @ApiOperation(value = "保存角色与资源的关系", notes = " for example : " +
            "{\"resourceIds\": [\"11111111\",\"222222222222\",\"33333333333\",\"444444444444\"],\"roleId\": \"asdfafdfd\",\"type\": 1000}")
    public <T> ResponseModel<T>  saveRoleResource(@RequestBody Map<String,Object> map){
        String roleId = String.valueOf(map.get("roleId"));
        List<String> resourceIds = (List<String>) map.get("resourceIds");
        Short type = Short.valueOf(String.valueOf(map.get("type")));

        ResponseModel<T> responseModel = ResponseModel.build();
        if (StringUtils.isEmpty(roleId)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("角色id不能为空");
            return responseModel;
        }
        if (!AuthorityConstant.containResourceType(type)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("资源类型只能是1000(菜单)、2000(操作)、3000(文件、API等)");
            return responseModel;
        }
        if (resourceIds==null || resourceIds.size()<=0) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("资源id列表不能为空");
            return responseModel;
        }

        Integer i = authorityService.saveRoleResource(map);
        responseModel.setCode(CodeConstants.OK);
        responseModel.setData((T) i);
        return responseModel;
    }


//******************************************** authority 添加end ***************************************************


//******************************************** authority 删除begin *************************************************


    @ApiOperation(value = "批量删除角色",notes = "[\"7da9be5a-6011-4f8a-abb3-159fe9278852\",\"f49f301d-1f4f-42bc-8056-bb9bf1382\",\"f49f301d-1f4f-42bc-8056-bb9bf1382d72\",\"f49f301d-1f4f-42bc-8056-bb9bf1382d73\"]")
    @PostMapping("/deleteRoles")
    public <T> ResponseModel<T>  deleteRoles(@RequestBody List<String> roleIds){
        ResponseModel<T> responseModel = ResponseModel.build();
        if(roleIds == null || roleIds.size()<=0){responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("角色id列表不能为空");
            return responseModel;
        }
        authorityService.deleteRoles(roleIds);
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }

    @ApiOperation(value = "批量删除资源",notes = "[\"7da9be5a-6011-4f8a-abb3-159fe9278852\",\"f49f301d-1f4f-42bc-8056-bb9bf1382\",\"f49f301d-1f4f-42bc-8056-bb9bf1382d72\",\"f49f301d-1f4f-42bc-8056-bb9bf1382d73\"]")
    @PostMapping("/deleteResources")
    public <T> ResponseModel<T>  deleteResources(@RequestBody List<String> resourceIds){
        ResponseModel<T> responseModel = ResponseModel.build();
        if(resourceIds == null || resourceIds.size()<=0){responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("资源id列表不能为空");
            return responseModel;
        }
        authorityService.deleteResources(resourceIds);
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }



    @PostMapping("/deleteUserRoleForRoleIds")
    @ApiOperation(value = "通过roleId列表删除该用户下的角色", notes = " for example : " +
            "{\"roleIds\": [\"11111111\",\"222222222222\",\"33333333333\",\"444444444444\"],\"userId\": \"1234567890\"}")
    public <T> ResponseModel<T>  deleteUserRoleForRoleIds(@RequestBody Map<String,Object> map){

        ResponseModel<T> responseModel = ResponseModel.build();
        if(map != null && map.size()>0){
            String userId = String.valueOf(map.get("userId"));
            List<String> list = (List<String>) map.get("roleIds");
            if (StringUtils.isEmpty(userId)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("用户id不能为空");
                return responseModel;
            }
            if (list==null || list.size()<=0) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("角色id列表不能为空");
                return responseModel;
            }
            authorityService.deleteUserRoleForRoleIds(map);
            responseModel.setCode(CodeConstants.OK);
            return responseModel;
        }else{
            responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("参数不能为空");
            return responseModel;
        }
    }

    @GetMapping("/deleteUserRoleForUserId")
    @ApiOperation("删除该用户下所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id",paramType="query",required =true,dataType = "String")
    })
    public <T> ResponseModel<T>  deleteUserRoleForUserId(@Param("userId")String userId){
        ResponseModel<T> responseModel = ResponseModel.build();
        authorityService.deleteUserRoleForUserId(userId);
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }

    @PostMapping("/deleteRoleResourceForResourceIds")
    @ApiOperation(value = "通过resourceIds列表删除该角色下的资源", notes = " for example : " +
            "{\"resourceIds\": [\"11111111\",\"222222222222\",\"33333333333\",\"444444444444\"],\"roleId\": \"1234567890\"}")
    public <T> ResponseModel<T>  deleteRoleResourceForResourceIds(@RequestBody Map<String,Object> map){

        String roleId = String.valueOf(map.get("roleId"));
        List<String> resourceIds = (List<String>) map.get("resourceIds");

        ResponseModel<T> responseModel = ResponseModel.build();
        if (StringUtils.isEmpty(roleId)) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("角色id不能为空");
            return responseModel;
        }
        if (resourceIds==null || resourceIds.size()<=0) {responseModel.setCode(CodeConstants.PARAMETER_ERROR_NULl); responseModel.setMessage("资源id列表不能为空");
            return responseModel;
        }

        authorityService.deleteRoleResourceForResourceIds(map);
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }

    @GetMapping("/deleteRoleResourceForRoleId")
    @ApiOperation("删除该角色下所有资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id",paramType="query",required =true,dataType = "String")
    })
    public <T> ResponseModel<T>  deleteRoleResourceForRoleId(@Param("roleId")String roleId){
        ResponseModel<T> responseModel = ResponseModel.build();
        authorityService.deleteRoleResourceForRoleId(roleId);
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }

    @GetMapping("/queryRoles")
    @ApiOperation("根据条件查询角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "角色编码",paramType="query",required =false,dataType = "String"),
            @ApiImplicitParam(name = "name", value = "角色名称",paramType="query",required =false,dataType = "String")
    })
    public <T> ResponseModel<T>  queryRoles(@Param("code")String code,@Param("name")String name){
        code = AuthorityConstant.convertObjToString(code);
        name = AuthorityConstant.convertObjToString(name);
        ResponseModel<T> responseModel = ResponseModel.build();
        List<RoleDto> list = authorityService.queryRoles(code,name);
        responseModel.setCode(CodeConstants.OK);
        responseModel.setData((T) list);
        return responseModel;
    }

    @GetMapping("/queryResources")
    @ApiOperation("根据条件查询资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "资源编码",paramType="query",required =false,dataType = "String"),
            @ApiImplicitParam(name = "codeName", value = "资源名称",paramType="query",required =false,dataType = "String"),
            @ApiImplicitParam(name = "level", value = "资源级别",paramType="query",required =false,dataType = "int"),
            @ApiImplicitParam(name = "type", value = "资源类型",paramType="query",required =false,dataType = "int")
    })
    public <T> ResponseModel<T>  queryResources(@Param("code")String code,@Param("codeName")String codeName,
                                                @Param("level")Short level,@Param("type")Short type){

        ResponseModel<T> responseModel = ResponseModel.build();
        code = AuthorityConstant.convertObjToString(code);
        codeName = AuthorityConstant.convertObjToString(codeName);
        List<Short> levelList = new ArrayList<>();
        if(StringUtils.isEmpty(level)){
            levelList = AuthorityConstant.resourceLevelList();
        }else{
            if( !AuthorityConstant.containResourceLevel(level)){
                responseModel.setCode(CodeConstants.PREREQUISITE_NOT_SUPPORT); responseModel.setMessage("请输入正确的参数:1,2,3,4,5");
                return responseModel;
            }
            levelList.add(level);
        }
        List<Short> typeList = new ArrayList<>();
        //资源类型只能是1000(菜单)、2000(操作)、3000(文件、API等)
        if(StringUtils.isEmpty(type)){
            typeList = AuthorityConstant.resourceTypeList();
        }else{
            if(!AuthorityConstant.containResourceType(type)){
                responseModel.setCode(CodeConstants.PREREQUISITE_NOT_SUPPORT); responseModel.setMessage("资源类型只能是1000(菜单)、2000(操作)、3000(文件、API等)");
                return responseModel;
            }
            typeList.add(type);
        }

        List<ResourceDto> list = authorityService.queryResources(code,codeName,levelList,typeList);
        responseModel.setCode(CodeConstants.OK);
        responseModel.setData((T) list);
        return responseModel;
    }

    @GetMapping("/queryRoleForUserId")
    @ApiOperation("查询该用户下所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id",paramType="query",required =true,dataType = "String")
    })
    public <T> ResponseModel<T>  queryRoleForUserId(@Param("userId")String userId){
        ResponseModel<T> responseModel = ResponseModel.build();
        responseModel.setData((T)authorityService.queryRoleForUserId(userId));
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }

    @GetMapping("/queryResourceForRoleId")
    @ApiOperation("查询该角色下所有资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id",paramType="query",required =true,dataType = "String")
    })
    public <T> ResponseModel<T>  queryResourceForRoleId(@Param("roleId")String roleId){
        ResponseModel<T> responseModel = ResponseModel.build();
        responseModel.setData((T)authorityService.queryResourceForRoleId(roleId));
        responseModel.setCode(CodeConstants.OK);
        return responseModel;
    }

}
