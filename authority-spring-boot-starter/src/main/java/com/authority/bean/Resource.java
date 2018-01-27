package com.authority.bean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "resource", schema = "bccurrency", catalog = "")
public class Resource {
    private String id;
    private String parentId;
    private Short level;
    private String code;
    private String codeName;
    private Short type;
    private Short sortNum;
    private Integer enable;
    private String remark;
    private String createUser;
    private Timestamp createDate;
    private String updateUser;
    private Timestamp updateDate;

    @Id
    @Column(name = "id", nullable = false, length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parent_id", nullable = true, length = 250)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "level", nullable = true)
    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 500)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "code_name", nullable = true, length = 50)
    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    @Basic
    @Column(name = "sort_num", nullable = true)
    public Short getSortNum() {
        return sortNum;
    }

    public void setSortNum(Short sortNum) {
        this.sortNum = sortNum;
    }

    @Basic
    @Column(name = "enable", nullable = true)
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 500)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "create_user", nullable = true, length = 250)
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "update_user", nullable = true, length = 250)
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Basic
    @Column(name = "update_date", nullable = true)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

}
