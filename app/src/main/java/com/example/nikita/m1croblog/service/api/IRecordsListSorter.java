package com.example.nikita.m1croblog.service.api;

import com.example.nikita.m1croblog.model.UserRecord;

import java.util.List;

public interface IRecordsListSorter {
    List<UserRecord> sort(List<UserRecord> records);
}
