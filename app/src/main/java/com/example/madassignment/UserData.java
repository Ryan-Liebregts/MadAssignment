package com.example.madassignment;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserData extends ViewModel {

    public MutableLiveData<String> userName;
    public MutableLiveData<Integer> userIcon;

    public MutableLiveData<Long> userId;

    public MutableLiveData<Long> userId2;

    public MutableLiveData<String> userName2;
    public MutableLiveData<Integer> userIcon2;

    public MutableLiveData<Integer> cross;

    public MutableLiveData<Integer> firstMove;

    public UserData(){
        userName = new MediatorLiveData<String>();
        userName.setValue("");

        userIcon = new MediatorLiveData<Integer>();
        userIcon.setValue(0);

        userName2 = new MediatorLiveData<String>();
        userName2.setValue("");

        userIcon2 = new MediatorLiveData<Integer>();
        userIcon2.setValue(0);

        userId = new MediatorLiveData<Long>();
        userId.setValue(0L);

        userId2 = new MediatorLiveData<Long>();
        userId2.setValue(0L);

        cross = new MediatorLiveData<Integer>();
        cross.setValue(1);

        firstMove = new MediatorLiveData<Integer>();
        firstMove.setValue(1);
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

    public String getUserName2() {
        return userName2.getValue();
    }


    public void setUserName2(String value) {
        userName2.setValue(value);
    }

    public int getUserIcon2() {
        return userIcon2.getValue();
    }

    public void setUserIcon2(int value) {
        userIcon2.setValue(value);
    }

    public long getUserId() {
        return userId.getValue();
    }

    public void setUserId(long value) {
        userId.setValue(value);
    }

    public long getUserId2() {
        return userId2.getValue();
    }

    public void setUserId2(long value) {
        userId2.setValue(value);
    }


    public void setCross(int value) { cross.setValue(value);}


    public int getCross() {return cross.getValue();}

    public void setFirstMove(int value) {firstMove.setValue(value);}
    public int getFirstMove() {return firstMove.getValue();}

}
