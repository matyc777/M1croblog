package com.example.nikita.m1croblog.service;

import com.example.nikita.m1croblog.model.UserRecord;
import com.example.nikita.m1croblog.service.api.IRecordsListSorter;

import java.util.Collections;
import java.util.List;

public class RecordListSorter implements IRecordsListSorter {

    @Override
    public List<UserRecord> sort(List<UserRecord> records) {
        Collections.reverse(records);
        return records;
    }
}
