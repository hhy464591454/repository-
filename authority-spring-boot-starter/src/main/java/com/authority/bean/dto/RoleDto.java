package com.authority.bean.dto;

import com.authority.bean.Role;

import java.util.List;

public class RoleDto extends Role {

    private List<ResourceDto> resourceDtoList;

    public List<ResourceDto> getResourceDtoList() {
        return resourceDtoList;
    }

    public void setResourceDtoList(List<ResourceDto> resourceDtoList) {
        this.resourceDtoList = resourceDtoList;
    }
}
