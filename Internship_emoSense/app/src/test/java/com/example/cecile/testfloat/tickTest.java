package com.example.cecile.testfloat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.app.*;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.runners.JUnit4;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;
import android.support.v4.app.Fragment;
import android.test.AndroidTestCase;
import android.test.ApplicationTestCase;
import android.test.InstrumentationTestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;


/**
 * Created by cecile on 03/05/17.
 */
// test for the timer
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class tickTest extends Application {

    @Test
    public void testFinishing() throws InterruptedException {
        ImagesDisplayActivity activity = new ImagesDisplayActivity();
        assertFalse(activity.isFinishing());
        activity.displayImages();
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        assertTrue(activity.isFinishing());

    }
}

