package com.example.nikita.m1croblog.service;

import com.example.nikita.m1croblog.model.UserInformation;
import com.example.nikita.m1croblog.service.api.IUserInfoValidator;

public class UserInfoValidator implements IUserInfoValidator {
    @Override
    public Boolean isCorrect(UserInformation userInformation) {
        if (userInformation.getAge() == null)
            return false;
        else if (userInformation.getEmail().isEmpty())
            return false;
        else if (userInformation.getGender().isEmpty())
            return false;
        else if (userInformation.getName().isEmpty())
            return false;
        else return !userInformation.getSurname().isEmpty();
    }
}
