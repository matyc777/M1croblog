package com.example.nikita.m1croblog.dataaccess;

import com.example.nikita.m1croblog.dataaccess.api.IUserInfoDao;
import com.example.nikita.m1croblog.model.UserInformation;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoDao implements IUserInfoDao {
    private DatabaseReference databaseReference;

    @Override
    public void setInfo(UserInformation userInformation, String uId) {
        if(userInformation != null && !uId.isEmpty()) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(uId + "/UserInfo");
            databaseReference.setValue(userInformation);
        }
    }
}
