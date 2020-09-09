package com.example.cecile.testfloat;

import android.os.Handler;
import android.os.Looper;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;


/**
 * Created by cecile on 09/05/17.
 */

public class TestHandler {

    public static void test() throws Exception {
        ImagesDisplayActivity imagesDisplayActivity = new ImagesDisplayActivity();
        imagesDisplayActivity.displayImages();
    }

}
