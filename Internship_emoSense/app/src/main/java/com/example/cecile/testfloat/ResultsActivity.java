package com.example.cecile.testfloat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by cecile on 19/05/17.
 */

public class ResultsActivity extends AppCompatActivity implements View.OnClickListener {
    WebView webView; // graph
    String faceType;
    int timeDisplay;
    TextView expression, time, convergence0, convergence1, convergence2, threshold;
    ArrayList<Integer> percentage0 = new ArrayList<>();
    ArrayList<Integer> percentage1 = new ArrayList<>();
    ArrayList<Integer> percentage2 = new ArrayList<>();

    ArrayList<Integer> percentage3 = new ArrayList<>();
    ArrayList<Integer> percentage4 = new ArrayList<>();
    ArrayList<Integer> percentage5 = new ArrayList<>();

    ArrayList<Integer> percentage6 = new ArrayList<>();
    ArrayList<Integer> percentage7 = new ArrayList<>();
    ArrayList<Integer> percentage8 = new ArrayList<>();

    ArrayList<Integer> correcterror = new ArrayList<>();
    ArrayList<Integer> numFace = new ArrayList<>();
    ArrayList<Integer> morphingPercentage = new ArrayList<>();
    ArrayList<Integer> morphingPercentage0 = new ArrayList<>();
    ArrayList<Integer> correctErrorFinal = new ArrayList<>();
    ArrayList<Integer> numstairs = new ArrayList<>();

    ArrayList<Integer> percentages = new ArrayList<>();

    ArrayList<Integer> totaldata = new ArrayList<>();
    double success = 0;
    double numerototal;
    ArrayList<Double> nYes = new ArrayList<>();
    ArrayList<Double> nNo = new ArrayList<>();
    ArrayList<Double> certainProba = new ArrayList<>();
    ArrayList<Double> percentages2 = new ArrayList<>();
    double stdValue;
    double mean;
    RadioButton radioButton1, radioButton2, radioButton3;

    FirebaseUser user;
    double threshold1, threshold2, threshold3;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        findViewById(R.id.buttonRestart).setOnClickListener(this);
        webView = (WebView) findViewById(R.id.webView1);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioButton3 = (RadioButton) findViewById(R.id.radioButton3);

        threshold = (TextView) findViewById(R.id.threshold);

        numstairs = getIntent().getIntegerArrayListExtra("numstairs");

        // WebView to show the graph
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.requestFocusFromTouch();
        webView.addJavascriptInterface(new JavaScriptInterface(this), "android");


        expression = (TextView) findViewById(R.id.expression);
        time = (TextView) findViewById(R.id.time);

        convergence0 = (TextView) findViewById(R.id.convergence0);
        convergence1 = (TextView) findViewById(R.id.convergence1);
        convergence2 = (TextView) findViewById(R.id.convergence2);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            faceType = bundle.getString("expression");
            timeDisplay = bundle.getInt("timeDisplay");
            correcterror = bundle.getIntegerArrayList("correcterror");
            numFace = bundle.getIntegerArrayList("numFace");
            morphingPercentage = bundle.getIntegerArrayList("morphingPercentage");

        }

        for(int i = 0; i < morphingPercentage.size(); i++){
            if(numstairs.get(i) == 0){
                percentage0.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 1){
                percentage1.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 2){
                percentage2.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 3){
                percentage3.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 4){
                percentage4.add(morphingPercentage.get(i));
            }if(numstairs.get(i) == 5){
                percentage5.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 6){
                percentage6.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 7){
                percentage7.add(morphingPercentage.get(i));
            }
            if(numstairs.get(i) == 8){
                percentage8.add(morphingPercentage.get(i));
            }

        }

        for(int i = 0; i <= 80; i=i+5){
            percentages.add(i);
        }

        expression.setText(faceType);
        time.setText(""+ timeDisplay);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArray();
                try {
                    graphThreshold(3, threshold1);
                } catch (MathException e) {
                    e.printStackTrace();
                }
                showData(percentage0, percentage1, percentage2);
                webView.loadUrl("file:///android_asset/graph.html"); // graph. html to create the graph
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArray();
                try {
                    graphThreshold(6, threshold2);
                } catch (MathException e) {
                    e.printStackTrace();
                }
                showData(percentage3, percentage4, percentage5);
                webView.loadUrl("file:///android_asset/graph2.html"); // graph. html to create the graph
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearArray();
                try {
                    graphThreshold(7, threshold3);
                } catch (MathException e) {
                    e.printStackTrace();
                }
                showData(percentage6, percentage7, percentage8);
                webView.loadUrl("file:///android_asset/graph3.html"); // graph. html to create the graph
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    public void saveData() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName", user.getDisplayName());
            jsonObject.put("expression", faceType);
            jsonObject.put("time", timeDisplay);

            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < morphingPercentage.size(); i++){
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("morphingPercentage", morphingPercentage.get(i));
                jsonObject1.put("numstair", numstairs.get(i));
                jsonObject1.put("isCorrect", correcterror.get(i));
                jsonObject1.put("numFace", numFace.get(i));
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("images", jsonArray);
            jsonObject.put("threshold staircase 1", threshold1);
            jsonObject.put("threshold staircase 2", threshold2);
            jsonObject.put("threshold staircase 3", threshold3);

            String jsonString = jsonObject.toString();

            Writer output = null;

            File file = new File(Environment.getExternalStorageDirectory()+ "/Android/data/"
                    + getApplicationContext().getPackageName()
                    + "/files/"
                    + user.getDisplayName() + timeStamp + ".json");

            output = new BufferedWriter(new FileWriter(file));
            output.write(jsonString);
            output.close();

            Uri datafile = Uri.fromFile(file);
            StorageReference storage = FirebaseStorage.getInstance().getReference();
            StorageReference storageReference = storage.child("dataFiles/" + user.getDisplayName() + timeStamp + ".json");

            storageReference.putFile(datafile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri download = taskSnapshot.getDownloadUrl();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showData(ArrayList<Integer> arrayList1, ArrayList<Integer> arrayList2, ArrayList<Integer> arrayList3){
        convergence0.setText(""+ arrayList1.get(arrayList1.size()-1));
        convergence1.setText(""+ arrayList2.get(arrayList2.size()-1));
        convergence2.setText(""+ arrayList3.get(arrayList3.size()-1));
    }

    public void clearArray(){
        morphingPercentage0.clear();
        nYes.clear();
        nNo.clear();
        certainProba.clear();
        correctErrorFinal.clear();
    }

    public void graphThreshold(int imageanalysis, double threshold1) throws MathException {
        // get position of element where imageanalysis = 3
        for(int i = 0; i < numFace.size(); i++){
            if(numFace.get(i) == imageanalysis){
                int val = morphingPercentage.get(i);
                int val2 = correcterror.get(i);
                correctErrorFinal.add(val2);
                morphingPercentage0.add(val);
            }
        }

        // ALGO threshold

        for(int i = 0; i < percentages.size(); i++){
            success = 0;
            int val = percentages.get(i);
            for(int j = 0; j < morphingPercentage0.size(); j++){
                if((val-0.00001) < morphingPercentage0.get(j) && (val+0.00001) > morphingPercentage0.get(j)){
                    totaldata.add(j);
                }
            }

            for(int k = 0; k < totaldata.size(); k++){
                if(correctErrorFinal.get(totaldata.get(k)) == 1){
                    success++;
                }
            }

            numerototal = totaldata.size();
            nYes.add(i, success);
            nNo.add(i, numerototal - nYes.get(i));
            if(totaldata.size() != 0){
                certainProba.add(i, (success / totaldata.size()));
            }
            else{
                certainProba.add(i, 0.0);
            }
            totaldata.clear();
        }
        // start parameters
        mean = getMean();

        stdValue = getStdDev();
        //System.out.println(certainProba);
        System.out.println(mean + " and " + stdValue);

        for(double i = 0; i <= 80; i=i+0.01){
            percentages2.add(i);
        }

        NormalDistribution normcdf = new NormalDistributionImpl();
        if(stdValue < mean)
            threshold1 = normcdf.cumulativeProbability(stdValue, mean);
        threshold.setText(""+threshold1);
        System.out.println(threshold1);
    }

    // Method for the threshold
    double getMean(){
        for(int i = 0; i < percentages.size(); i++){
            double val = percentages.get(i) * certainProba.get(i);
            mean +=val;
        }
        return mean/=percentages.size();
    }

    double getVariance()
    {
        double temp = 0;
        for(int i = 0; i < percentages.size(); i++)
            temp += (percentages.get(i)-mean)*(percentages.get(i)-mean);
        return temp/percentages.size();
    }

    double getStdDev()
    {
        return Math.sqrt(getVariance());
    }

    /**
     * Go to the first activity when button back is pressed
     */
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(ResultsActivity.this, HomeWindow.class);
        startActivity(intent);
    }

    /**
     * CLick on button restart -> run 1st activity
     * @param v
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonRestart) {
            Intent intent = new Intent(ResultsActivity.this, HomeWindow.class);
            startActivity(intent);
        }
    }

    /**
     * JavaScrit class to get data and display them on the graĥ
     */
    public class JavaScriptInterface {
        Context mContext;

        public JavaScriptInterface(Context c){
            this.mContext = c;
        }

        @JavascriptInterface
        public int array0(int pos){
            return percentage0.get(pos);
        }
        @JavascriptInterface
        public int array1(int pos){
            return percentage1.get(pos);
        }
        @JavascriptInterface
        public int array2(int pos){
            return percentage2.get(pos);
        }
        @JavascriptInterface
        public int array3(int pos){ return percentage3.get(pos); }
        @JavascriptInterface
        public int array4(int pos){
            return percentage4.get(pos);
        }
        @JavascriptInterface
        public int array5(int pos){
            return percentage5.get(pos);
        }
        @JavascriptInterface
        public int array6(int pos){
            return percentage6.get(pos);
        }
        @JavascriptInterface
        public int array7(int pos){
            return percentage7.get(pos);
        }
        @JavascriptInterface
        public int array8(int pos){
            return percentage8.get(pos);
        }
        @JavascriptInterface
        public int numberTrials(int pos){
            return pos;
        }
        @JavascriptInterface
        public int getSizeArray(){
            return percentage0.size();
        }
        @JavascriptInterface
        public int morphingPercentage0(int pos){
            return morphingPercentage0.get(pos);
        }
        @JavascriptInterface
        public int getSizeData(){
            return certainProba.size();
        }
        @JavascriptInterface
        public double getCertainProbaElement(int pos){
            return certainProba.get(pos);
        }
        @JavascriptInterface
        public int getPercentagesElement(int pos)
        {
            return percentages.get(pos);
        }
    }


}



    /*Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Graph 1","Graph 2", "Graph 3"};
    int Numboftabs =3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);


        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/








