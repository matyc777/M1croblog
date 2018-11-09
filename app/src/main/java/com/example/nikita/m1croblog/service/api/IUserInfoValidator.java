package com.example.nikita.m1croblog.service.api;

import com.example.nikita.m1croblog.model.UserInformation;

public interface IUserInfoValidator {
    Boolean isCorrect(UserInformation userInformation);
}
