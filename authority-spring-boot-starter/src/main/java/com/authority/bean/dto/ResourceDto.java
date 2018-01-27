package com.authority.bean.dto;

import com.authority.bean.Resource;

import java.util.List;

public class ResourceDto extends Resource {

    private List<ResourceDto> resourceDtoList;

    public List<ResourceDto> getResourceDtoList() {
        return resourceDtoList;
    }

    public void setResourceDtoList(List<ResourceDto> resourceDtoList) {
        this.resourceDtoList = resourceDtoList;
    }
}
