package com.example.madassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateUser extends ViewModel {

    public MutableLiveData<String> userName;
    public MutableLiveData<Integer> userIcon;


    public CreateUser(){
        userName = new MediatorLiveData<String>();
        userName.setValue("");

        userIcon = new MediatorLiveData<Integer>();
        userIcon.setValue(0);

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


}
