package com.cccuu.project.utils;

import com.cccuu.project.model.frontcore.User;

/**
 * 前台用户对象
 */
public class UserInfo {

    private String userId;//用户id

    private String userName;//用户名

    private String departId;//所属部门id

    private String name;//姓名

    private String extensionNum;//分机号

    public UserInfo() {
    }
    public UserInfo(User user) {
        this.userId = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.departId = user.getDepartId();
        this.extensionNum = user.getExtensionNum();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartId() {
        return departId;
    }

    public void setDepartId(String departId) {
        this.departId = departId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtensionNum() {
        return extensionNum;
    }

    public void setExtensionNum(String extensionNum) {
        this.extensionNum = extensionNum;
    }
}
