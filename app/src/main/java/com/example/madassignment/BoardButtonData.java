package com.example.madassignment;

public class BoardButtonData {
    private int imageSource;
    private char markerSymbol;
    private boolean enabledState;

    /* -----------------------------------------------------------------------------------------
        Function: BoardButtonData
        Author: Jules
        Description: Information associated with each button in the adapter data
     ---------------------------------------------------------------------------------------- */
    public BoardButtonData(int imageResource, int position) {
        this.imageSource = 0;
        this.markerSymbol = '-';
        this.enabledState = true;
    }

    public int getImageResource() {
        return imageSource;
    }

    public void setImageResource(int pImageResource) {
        imageSource = pImageResource;
    }

    public char getMarkerSymbol(){
        return markerSymbol;
    }

    public void setMarkerSymbol(char pMarkerSymbol){
        markerSymbol =  pMarkerSymbol;
    }

    public boolean getEnabledState(){
        return enabledState;
    }

    public void setEnabledState(boolean pEnabledState){
        enabledState = pEnabledState;
    }

}