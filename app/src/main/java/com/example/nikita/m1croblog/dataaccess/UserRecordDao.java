package com.example.nikita.m1croblog.dataaccess;

import com.example.nikita.m1croblog.dataaccess.api.IUserRecordDao;
import com.example.nikita.m1croblog.model.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRecordDao implements IUserRecordDao {
    private DatabaseReference databaseReference;

    @Override
    public void addRecord(UserRecord userRecord, String uid) {
        if (userRecord != null && !uid.isEmpty()) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(uid + "/Records");
            databaseReference.push().setValue(userRecord);
        }
    }
}
