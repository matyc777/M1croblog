package com.example.nikita.m1croblog.dataaccess.api;

import com.example.nikita.m1croblog.model.UserRecord;

public interface IUserRecordDao {
    void addRecord(UserRecord userRecord, String uid);
}
