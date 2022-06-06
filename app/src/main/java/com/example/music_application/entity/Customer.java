package com.example.music_application.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "username")
    @NonNull
    public String username;

    @ColumnInfo(name = "emailAddress")
    @NonNull
    public String emailAddress;

    @ColumnInfo(name = "address")
    @NonNull
    public String address;

    public Customer( @NonNull String username, @NonNull String emailAddress, @NonNull String  address) {
        this.username=username;
        this.emailAddress=emailAddress;
        this.address = address;
    }

    @NonNull
    public int getUid() {
        return uid;
    }
}
