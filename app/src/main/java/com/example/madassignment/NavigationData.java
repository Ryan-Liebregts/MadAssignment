package com.example.madassignment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NavigationData extends ViewModel {

    public MutableLiveData<Integer> clickedValue;
    public MutableLiveData<Integer> settingsClickedValue;

    public NavigationData(){
        clickedValue = new MediatorLiveData<Integer>();
        clickedValue.setValue(0);

        settingsClickedValue = new MediatorLiveData<Integer>();
        settingsClickedValue.setValue(0);

    }
    public int getClickedValue(){
        return clickedValue.getValue();
    }
    public void setClickedValue(int value){
        clickedValue.setValue(value);
    }

    public int getSettingsValue() { return settingsClickedValue.getValue();}

    public void setSettingsValue(int value) { settingsClickedValue.setValue(value);}



}
