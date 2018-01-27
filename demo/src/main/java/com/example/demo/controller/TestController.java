package com.example.demo.controller;
import com.authority.service.IAuthorityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/test/")
@Api(value = "redchain user system",tags = "user-demo-Application")
public class TestController {

    @Autowired
    private IAuthorityService authorityService;


    @ApiOperation("根据用户获取菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户", paramType="query",required =true,dataType = "String")
    })
    @GetMapping("/test")
    public Object test(@Param("userId")String userId){
        return  authorityService.getUserAuthorityV2(userId);
    }
}
