package com.example.cecile.testfloat;

/**
 * Created by cecile on 03/05/17.
 */

/**
 * Class to store data from the test
 */

public class DataImages {
    public int morphingPercentage; // percentage
    public int numstairs;
    public int isCorrect;


    public DataImages(int morphing, int numstairs, int isC) {
        this.morphingPercentage = morphing;
        this.numstairs = numstairs;
        this.isCorrect = isC;
    }

}
