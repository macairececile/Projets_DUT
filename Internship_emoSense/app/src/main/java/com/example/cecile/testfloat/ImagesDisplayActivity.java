package com.example.cecile.testfloat;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ImagesDisplayActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    ImageView imageView; // contains the image from Firebase
    TextView textView; // contain the tet question
    FloatingActionButton actionTwo, actionOne; // button to answer the question
    AlertDialog alertDialog; //alert dialog for error messages / show yes - no

    private GestureDetector gd;

    // animations for the alert dialog
    protected AlphaAnimation fadeIn;
    protected AlphaAnimation fadeOut;

    // constant parameters for swipe mode
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    View v;

    private String expression; // type of expression (happy, fear, sad, ...)
    private int timeDisplay; // time to show an image

    // random numbers to sort array and know if neutral is shown 1st or not
    Random randomType;
    private int numberRandomType;

    private int numTypeFace; // type face (1 2 5) or (3 6 7)

    AlertDialog.Builder dialog; // builder to create alert dialog
    Timer timer; // time interval between the 2 images

    boolean isExpressionFirst = false;

    private int counter; // for number of images that have been shown

    private RelativeLayout relativeLayout;

    private ArrayList<Integer> numstairs = new ArrayList<>(); // array of integer beteen 1 and 9

    public ArrayList<Integer> correcteror = new ArrayList<>();
    public ArrayList<Integer> numFace = new ArrayList<>();
    public ArrayList<Integer> morphingPercentage = new ArrayList<>();

    int i = 0;
    int numCorrectAnswer = 0;

    private Integer[] typeImage = {3, 6, 7}; // or 1 - 2 - 5
    private ArrayList<Integer> percentage = new ArrayList<>(); // array with percentage numbers (size 9)
    private int staircase = 9;
    private boolean isClickedOne = false;
    private int total = 25;
    int isC = 0;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_display);

        relativeLayout = (RelativeLayout) findViewById(R.id.images_display);

        try {
            FirebaseApp.getInstance();
        } catch (IllegalStateException ex) {
            FirebaseApp.initializeApp(this, FirebaseOptions.fromResource(this));
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.textView);
        v = (View) findViewById(R.id.images_display);
        actionTwo = (FloatingActionButton) findViewById(R.id.two);
        actionOne = (FloatingActionButton) findViewById(R.id.one);
        gd = new GestureDetector(this, this);

        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeOut = new AlphaAnimation(1.0f, 0.0f);

        // Get data
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            expression = bundle.getString("expression"); // get the expression data
            timeDisplay = bundle.getInt("time"); // get the time data
        }

        dialog = new AlertDialog.Builder(ImagesDisplayActivity.this);

        counter = 0;

        generateRandomNumbers();

        // add values in percentage array
        percentage.add(0, 80);
        percentage.add(1, 40);
        percentage.add(2, 5);
        percentage.add(3, 80);
        percentage.add(4, 40);
        percentage.add(5, 5);
        percentage.add(6, 80);
        percentage.add(7, 40);
        percentage.add(8, 5);

        getImagesFromFirebase();

        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    /**
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {
    }

    /**
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /**
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {
    }

    /**
     * Gesture of swipe mode
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /**
     * Random number between a specific interval
     * @param rand
     * @param min
     * @param max
     * @return
     */
    public static int randomIntegerNumber(Random rand, int min, int max) {
        rand = new Random();

        int num = rand.nextInt((max - min) + 1) + min;

        return num;
    }

    /**
     * Create an array randomly sort
     * @return
     */
    public ArrayList<Integer> generateRandomNumbers(){
        // array of 225 numbers between 1 and 9
        for(int j = 0; j < total; j++){
            for (int i = 0; i < staircase ; i++) {
                numstairs.add(i);
            }

        }
        //System.out.println(numstairs + " ** " + numstairs.size());
        //System.out.println(numstairs.size());

        Collections.shuffle(numstairs);
        int counter = 0;
        for(int i = 0; i < numstairs.size(); i++){
            if(numstairs.get(i) == 8){
                counter++;
            }
        }
        return numstairs;


    }

    /**
     * Get integer array element at a specific position
     * @param tab
     * @param pos
     * @return
     */
    public Integer getElementArrayList(ArrayList<Integer> tab, int pos){
        return tab.get(pos);
    }

    /**
     * Get double array element at a specific position
     * @param tab
     * @param pos
     * @return
     */
    public Double getElementArrayFloatList(ArrayList<Double> tab, int pos){
        return tab.get(pos);
    }

    /**
     * Get images from Firebase with the generated random numbers
     * @throws StorageException
     */
    public void getImagesFromFirebase() {
        // Path
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files");

        if (!mediaStorageDir.exists()){
            mediaStorageDir.mkdirs();
        }

        // Create a reference to Google Cloud Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();


        displayNumTypeFace(i);
        final int a = getElementArrayList(numstairs, i);

        // Get file form firebase with parameters
        StorageReference image = storage.getReferenceFromUrl("gs://emosense-68e5b.appspot.com/" + expression + "/" + numTypeFace + "morphed" + percentage.get(a) + "_.png");
        System.out.println(numTypeFace + "morphed" + percentage.get(a));
        // Download the file from Firebase if enough space on the phone
        final File localFile = new File(mediaStorageDir, image.getName());
        FileDownloadTask task1 = image.getFile(localFile);
        if(getAvailableSpaceInKB() > 20000) {
            // if download succesful, donwload the neutral image
            task1.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    getImagesNeutral();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    errorMessage();
                }
            });
        }
        else{
            showAlertDialogForSpaceStorage();
        }
    }

    /**
     * Get images from Firebase that is neutral
     */
    public void getImagesNeutral(){
        // Path
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files");

        if (!mediaStorageDir.exists()){
            mediaStorageDir.mkdirs();
        }

        // Create a reference to Google Cloud Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        displayNumTypeFace(i);

        StorageReference imageNeutral = storage.getReferenceFromUrl("gs://emosense-68e5b.appspot.com/" + expression + "/" + numTypeFace + "morphed" + 0 + "_.png");

        // Download the file from Firebase if enough space on the phone
        final File localNeutral = new File(mediaStorageDir, imageNeutral.getName());
        FileDownloadTask task = imageNeutral.getFile(localNeutral);

        if(getAvailableSpaceInKB() > 20000) {
            // if the task is succesful, run the test
            task.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    displayImages();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    errorMessage();
                }
            });
        }
        else{
            showAlertDialogForSpaceStorage();
        }
    }
    /**
     * Get the file in the phone _ neutral image
     */
    public void getFileFromStorageNeutral() throws FileNotFoundException {
        displayNumTypeFace(i);
        String filename = numTypeFace + "morphed" + 0 + "_.png";
        // Path
        final File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files/" + filename);

        String finalfile = file.getAbsolutePath();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fond);

        // if file exists, display image in imageView
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(finalfile);
        }else{
            Log.v("error", "error");
        }

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth() * 2), (bitmap.getHeight() * 2), true);
        imageView.setImageBitmap(resized);
    }

    /**
     *  Alert dialog when not enough space in the phone
     */
    public void showAlertDialogForSpaceStorage(){
        new AlertDialog.Builder(ImagesDisplayActivity.this)
                .setTitle("Error !")
                .setMessage("You don't have enough space on your phone !")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ImagesDisplayActivity.this, HomeWindow.class);
                        startActivity(intent);
                    }
                }).show();
    }

    /**
     * Get the space available in the phone - only > API 18
     * @return
     */
    @TargetApi(18)
    public static long getAvailableSpaceInKB(){
        final long SIZE_KB = 1024L;
        long availableSpace = -1L;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
        return availableSpace/SIZE_KB;
    }

    /**
     * Get file in the phone - expression image
     * @param i
     */
    public void getFileFromStorage(int i) throws FileNotFoundException {
        displayNumTypeFace(i);

        int a = getElementArrayList(numstairs, i);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fond);
        // Get the num of the face and the morphing percentage of the image -> use in the 3rd activity
        numFace.add(numTypeFace);
        morphingPercentage.add(percentage.get(a));
        // Path
        String filename = numTypeFace + "morphed" + percentage.get(a) + "_.png";
        final File file = new File(Environment.getExternalStorageDirectory() + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files/" + filename);

        String finalfile = file.getAbsolutePath();
        // Show image in imageView if file exists
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(finalfile);
        }
        else{
            Log.v("error", "error");
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth() * 2), (bitmap.getHeight() * 2), true);
        imageView.setImageBitmap(resized);

    }

    /**
     * Num Type face according to the number from sortArray
     * @param a
     */
    public void displayNumTypeFace(int a){
        if((getElementArrayList(numstairs, a) == 0) || (getElementArrayList(numstairs, a) == 1) || (getElementArrayList(numstairs, a) == 2)){
            numTypeFace = typeImage[0];
        }
        else if((getElementArrayList(numstairs, a) == 3) || (getElementArrayList(numstairs, a) == 4) || (getElementArrayList(numstairs, a) == 5)){
            numTypeFace = typeImage[1];

        }
        else if((getElementArrayList(numstairs, a) == 6) || (getElementArrayList(numstairs, a) == 7) || (getElementArrayList(numstairs, a) == 8)){
            numTypeFace = typeImage[2];
        }
        else{
            numTypeFace = typeImage[0];
        }
    }

    /**
     * Show images one after another during a specific time and show the associated question
     */
    public void displayImages() {
        isExpressionFirst = false;
        // random number to know if neutral is shown 1st or not
        randomType = new Random();
        numberRandomType = randomIntegerNumber(randomType, 0, 1);
        relativeLayout.setBackgroundResource(R.color.backgroundImages);
        //displayNumTypeFace(i);


        if (numberRandomType == 0) {
            try {
                getFileFromStorageNeutral();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isExpressionFirst = false;
        } else {
            try {
                getFileFromStorage(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isExpressionFirst = true;
        }

        // show image

        //saveData();

        // remove image after delay = timeDisplay
        final Runnable runnablePause = new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(0);
            }
        };

        Handler handlerPause = new Handler();
        handlerPause.postDelayed(runnablePause, timeDisplay); // show after time display = 200 ms

        // Show 2nd image after timeDisplay + 200
        final Runnable runnableImage2 = new Runnable() {

            @Override
            public void run() {
                if(numberRandomType == 0){
                    try {
                        getFileFromStorage(i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        getFileFromStorageNeutral();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Handler handler = new Handler();

        int time = timeDisplay + 200; // show after 200ms + 200ms

        handler.postDelayed(runnableImage2, time);


        final Runnable runnablePause2 = new Runnable() {
            @Override
            public void run() {
                imageView.setImageResource(0);
            }
        };

        int tt = time + timeDisplay;
        Handler handlerPause2 = new Handler();
        handlerPause2.postDelayed(runnablePause2, tt); // show after 200 + 200 + 200

        // Remove 2nd picture when timer is finished (timeDisplay + time) + show question
        final Runnable runnableQuestion = new Runnable() { // after 200+200 + 200 + 200
            @Override
            public void run() {
                questions();
                //relativeLayout.setBackgroundColor(Color.TRANSPARENT);

                imageView.setImageResource(0);
                actionTwo.setVisibility(View.VISIBLE);
                actionOne.setVisibility(View.VISIBLE);
                counter +=1;
            }
        };

        Handler handlerQuestion = new Handler();
        int t = tt + 200;
        handlerQuestion.postDelayed(runnableQuestion, t);
    }

    /**
     * Delete images in the phone
     */
    public void deleteImages(){
        File dir = new File(Environment.getExternalStorageDirectory()+ "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files");
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }

    public void errorMessage(){
        new AlertDialog.Builder(ImagesDisplayActivity.this)
                .setTitle("Error !")
                .setMessage("The download has a problem, maybe your internet connection...")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteImages();
                        Intent intent = new Intent(ImagesDisplayActivity.this, HomeWindow.class);
                        startActivity(intent);
                    }
                }).show();
    }

    /**
     * Listing of files in the phone
     */
    public int showNumFilesFromStorage(){
        File dir = new File(Environment.getExternalStorageDirectory()+ "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/files");

        String[] children = dir.list();
        for (int i = 0; i < children.length; i++)
        {
            File file = new File(dir, children[i]);
            System.out.println(file);
        }
        return children.length;
    }

    /**
     * Swipe bottom
     */
    private void onSwipeBottom() {
    }

    /**
     * Show alertdialog when swiping to the right
     */
    public void onSwipeRight() {
        isClickedOne = true;
        /*alertDialog = dialog.create();
        alertDialog.setMessage("NUMBER ONE !");
        alertDialog.setCancelable(false);
        alertDialog.show();*/

        isCorrect(i);

        displayImagesAfterAnswer();

        actionOne.setVisibility(View.INVISIBLE);
        actionTwo.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        Log.v("numberChoose", "number one");

    }

    /**
     * Show alertdialog when swiping to the left
     */
    public void onSwipeLeft() {
        isClickedOne = false;
        /*alertDialog = dialog.create();
        alertDialog.setMessage("NUMBER TWO !");
        alertDialog.setCancelable(false);
        alertDialog.show();*/

        isCorrect(i);

        displayImagesAfterAnswer();

        actionOne.setVisibility(View.INVISIBLE);
        actionTwo.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        Log.v("numberChoose", "number two");
    }

    /**
     * Swipe top
     */
    public void onSwipeTop() {
    }

    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gd.onTouchEvent(event);
    }

    /**
     * When back button is pressed, go to the first activity
     */
    @Override
    public void onBackPressed() {
        finish();
        deleteImages();
        Intent intent = new Intent(ImagesDisplayActivity.this, HomeWindow.class);
        startActivity(intent);
    }

    /**
     * Action according to the pressed button (show the specific alertdialog + button next)
     * @param view
     * @throws IOException
     */
    public void ButtonAnswer(View view) throws IOException {
        switch (view.getId()) {
            case R.id.two:
                isClickedOne = false;
                //displayAlertDialog("NUMBER TWO !");
                actionOne.setVisibility(View.INVISIBLE);
                actionTwo.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                isCorrect(i);
                displayImagesAfterAnswer();
                Log.v("numberChoose", "number two");
                break;
            case R.id.one:
                isClickedOne = true;
                //displayAlertDialog("NUMBER ONE !");
                actionOne.setVisibility(View.INVISIBLE);
                actionTwo.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                isCorrect(i);
                displayImagesAfterAnswer();
                Log.v("numberChoose", "number one");
                break;
            default:
                break;
        }
    }

    /**
     * time when the alertdialog is shown
     */
    public void displayImagesAfterAnswer() {

        relativeLayout.setBackgroundResource(R.color.backgroundImages);
        //saveData();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                //alertDialog.dismiss();
                timer.cancel(); //this will cancel the timer of the system
            }
        }, 700); // delete alert dialog after 700 ms*/

        final Runnable run = new Runnable() {
            @Override
            public void run() {
                if(counter < numstairs.size()){
                    getImagesFromFirebase();
                }
                else{
                    runThirdActivity();
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(run, 700);

    }

    /**
     * Show the alert dialog with an animation
     * @param message
     */
    public void displayAlertDialog(String message) {
        alertDialog = dialog.create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * Save data in Firebase
     */
    public void saveData() {
        // Reference to DatabaseFirebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(user.getDisplayName());
        ref.child("expression").setValue(expression);
        ref.child("time").setValue(timeDisplay);
        if(i < numstairs.size()){
            ref.child("images" + i).setValue(new DataImages(percentage.get(getElementArrayList(numstairs, i)), getElementArrayList(numstairs, i), isC));
        }
    }

    /**
     * Method to know if the user's answer is right or wrong
     * @param a
     */
    public void isCorrect(int a){
        int value = percentage.get(getElementArrayList(numstairs, a)); // get percentage value of the image
        int numStairValue = getElementArrayList(numstairs, a);
        if(isExpressionFirst && !isClickedOne || !isExpressionFirst && isClickedOne){
            value+=5;
            isC = 0;
        }else{
            numCorrectAnswer++;
            value-=5;
            isC = 1;
        }

        if(value < 5)
            value = 5;
        if(value > 100)
            value = 100;

        // change value in percentage array according to user's answer
        percentage.set(getElementArrayList(numstairs, a), value);

        correcteror.add(isC);

        if(isExpressionFirst){
            Log.v("results", ""+numStairValue);
            Log.v("numCorrect", ""+numCorrectAnswer);
            Log.v("isCorrect", ""+isC);
        }

        i++;

    }

    /**
     * Question method
     */
    public void questions(){
        textView.setVisibility(View.VISIBLE);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(40);
        textView.setText("Which was stronger ?");
        /*switch (expression){
            case "happy":
                textView.setText(getString(R.string.questionHappy));
                break;
            case "sad":
                textView.setText(getString(R.string.questionSadness));
                break;
            case "disgust":
                textView.setText(getString(R.string.questionDisgust));
                break;
            case "angry":
                textView.setText(getString(R.string.questionAnger));
                break;
            case "fear":
                textView.setText(getString(R.string.questionFear));
                break;
            case "surprise":
                textView.setText(getString(R.string.questionSurprise));
                break;
            default:
                break;

        }*/
    }

    public void runThirdActivity(){
        // run third activity with data
        Intent intent = new Intent(ImagesDisplayActivity.this, ResultsActivity.class);
        intent.putExtra("correcterror", correcteror);
        intent.putExtra("numFace", numFace);
        intent.putExtra("morphingPercentage", morphingPercentage);
        intent.putExtra("expression", expression);
        intent.putExtra("timeDisplay", timeDisplay);
        intent.putExtra("numstairs", numstairs);
        deleteImages();
        startActivity(intent);
    }
}


