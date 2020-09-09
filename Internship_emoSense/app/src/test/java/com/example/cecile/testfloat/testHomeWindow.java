package com.example.cecile.testfloat;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by cecile on 09/05/17.
 */

public class testHomeWindow {
    HomeWindow homeWindow = new HomeWindow();
    @Test
    public void isExpressionTypeCorrect() throws Exception{

        if(homeWindow.spinner != null){
            if(homeWindow.posSpinner() == 1)
                assertEquals(homeWindow.getFace(), "happy");
            if(homeWindow.posSpinner() == 2)
                assertEquals(homeWindow.getFace(), "fear");
            if(homeWindow.posSpinner() == 3)
                assertEquals(homeWindow.getFace(), "surprise");
            if(homeWindow.posSpinner() == 4)
                assertEquals(homeWindow.getFace(), "angry");
            if(homeWindow.posSpinner() == 5)
                assertEquals(homeWindow.getFace(), "disgust");
            if(homeWindow.posSpinner() == 6)
                assertEquals(homeWindow.getFace(), "sad");
        }
    }

    public void testTime() throws Exception{
        if(homeWindow.numberPicker != null){
            if(homeWindow.numberPickerValue() == 200)
                assertEquals(homeWindow.getTime(), 200000);
        }
    }




}
