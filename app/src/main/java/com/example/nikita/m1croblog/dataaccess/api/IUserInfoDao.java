package com.example.nikita.m1croblog.dataaccess.api;

import com.example.nikita.m1croblog.model.UserInformation;

public interface IUserInfoDao {
    void setInfo(UserInformation userInformation, String uId);
}
