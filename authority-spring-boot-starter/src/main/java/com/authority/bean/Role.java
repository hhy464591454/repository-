package com.authority.bean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "role", schema = "bccurrency", catalog = "")
public class Role {
    private String id;
    private String code;
    private String name;
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
    @Column(name = "code", nullable = true, length = 30)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "sort_num", nullable = true, length = 30)
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
