package com.example.madassignment;

import static java.security.AccessController.getContext;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserData extends ViewModel {

    // TODO: PK Commenting
    public MutableLiveData<String> userName;
    public MutableLiveData<Integer> userIcon;

    public MutableLiveData<Long> userId;

    public MutableLiveData<Long> userId2;

    public MutableLiveData<String> userName2;
    public MutableLiveData<Integer> userIcon2;

    public MutableLiveData<Integer> userSymbol1;
    public MutableLiveData<Integer> userSymbol2;


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

        userSymbol1 = new MediatorLiveData<Integer>();
        userSymbol1.setValue(R.drawable.cross);

        userSymbol2 = new MediatorLiveData<Integer>();
        userSymbol2.setValue(R.drawable.circle);

        firstMove = new MediatorLiveData<Integer>();
        firstMove.setValue(0);
    }
    public void resetUserData() {
        userName.setValue("");
        userIcon.setValue(0);
        userName2.setValue("");
        userIcon2.setValue(0);
        userId.setValue(0L);
        userId2.setValue(0L);
        userSymbol1.setValue(R.drawable.cross);
        userSymbol2.setValue(R.drawable.circle);
        firstMove.setValue(0);
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

    public void setUserSymbol1(int value) { userSymbol1.setValue(value);}

    public int getUserSymbol1() {return userSymbol1.getValue();}

    public void setUserSymbol2(int value) { userSymbol2.setValue(value);}

    public int getUserSymbol2() {return userSymbol2.getValue();}

    public void setFirstMove(int value) {firstMove.setValue(value);}

    public int getFirstMove() {return firstMove.getValue();}



}
