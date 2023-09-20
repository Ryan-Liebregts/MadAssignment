package com.example.madassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditUser extends ViewModel {

    // TODO: PK Commenting
    public MutableLiveData<String> userName;
    public MutableLiveData<Integer> userIcon;
    public MutableLiveData<Long> userId;

    public MutableLiveData<Long> deleteUserId;

    public MutableLiveData<Integer> deleteUserPosition;


    public EditUser(){
        userName = new MediatorLiveData<String>();
        userName.setValue("");

        userIcon = new MediatorLiveData<Integer>();
        userIcon.setValue(0);

        userId = new MediatorLiveData<Long>();
        userId.setValue(0L);

        deleteUserId = new MediatorLiveData<Long>();
        deleteUserId.setValue(0L);

        deleteUserPosition = new MediatorLiveData<Integer>();
        deleteUserPosition.setValue(0);
    }

    public String getUserName() {
        return userName.getValue();
    }


    public void setUserName(String value) {
        userName.setValue(value);
    }

    public int getUserIcon() {
        return userIcon.getValue();
    }

    public void setUserIcon(int value) {
        userIcon.setValue(value);
    }

    public long getUserId() {
        return userId.getValue();
    }

    public void setUserId(long value) {
        userId.setValue(value);
    }

    public long getDeleteUserId() {
        return deleteUserId.getValue();
    }

    public void setDeleteUserId(long value) {
        deleteUserId.setValue(value);
    }

    public int getDeleteUserPosition() {
        return deleteUserPosition.getValue();
    }

    public void setDeleteUserPosition(int value) {
        deleteUserPosition.setValue(value);
    }

}
