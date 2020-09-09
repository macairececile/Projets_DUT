package com.example.cecile.testfloat;

/**
 * Main window
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeWindow extends AppCompatActivity {

    // Storage Permissions
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;

    TextView text;
    TextView textView2, textView1;
    FloatingActionButton floatingActionButton;
    Spinner spinner;
    NumberPicker numberPicker;

    LinearLayout layout;
    LinearLayout.LayoutParams parms, ln;
    ArrayAdapter<String> list;

    AlertDialog builder;

    private String face;
    private String positiveText;

    Typeface font;

    private int time;

    private String[] expression = {"happy", "fear", "surprise", "angry", "disgust", "sad"};

    int numgraph1 = 0;
    int numgraph2 = 0;
    int numgraph3 = 0;
    boolean isChecked = false;

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("numberPicker", 1);
        savedInstanceState.putString("face", face);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_window);

        text = (TextView) findViewById(R.id.Text);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.instructions);

        //Font
        font = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");


        /**
         *  Instruction box when click on the floating button
         */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            // creation of alert dialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(HomeWindow.this);

            public void onClick(View v) {
                dialog.setTitle("How does it works ?");
                dialog.setMessage(getString(R.string.details));

                String positiveText = getString(android.R.string.ok);
                dialog.setPositiveButton(positiveText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog builder = dialog.create();
                // display dialog with an animation
                builder.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
                builder.show();
            }
        });

        /**
         * Permission to store images on the phone
         */
        int permission = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Permission to access the SD-CARD is required for this app to start the test.")
                        .setTitle("Permission required");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Log.i(TAG, "Clicked");
                        makeRequest();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else {
                makeRequest();
            }
        }


    }

    /**
     * Create menu in the toolbar with connection icon
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_connected, menu);

        return true;
    }

    /**
     * Run login activity when click on icon connection
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_user:
                Intent intent = new Intent(HomeWindow.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * permission to access to the storage folder
     */
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user");

                } else {

                    Log.i(TAG, "Permission has been granted by user");

                }
                return;
            }
        }

    }

    /**
     * know if there is an internet connection
     * @return
     */
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(HomeWindow.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null){
            if(netInfo.getType() == ConnectivityManager.TYPE_WIFI)
                haveConnectedWifi = true;
            if(netInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                haveConnectedMobile = true;
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    /**
     * Start button method
     * @param view
     */
    public void ButtonStart(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // get status of user
        if(user == null) { // if user not connected, show alert dialog
            new AlertDialog.Builder(HomeWindow.this)
                    .setTitle("Error !")
                    .setMessage("Please log in !")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if(!haveNetworkConnection()){ // if user don't have an internet connection, run a toast
            Toast.makeText(HomeWindow.this, "You're not connected to internet, you can't start the test !",
                    Toast.LENGTH_LONG).show();
        }
        else { // if user has an internet connection + is connected
            // creation of an alert dialog to choose settings (type of expression and time)
            AlertDialog.Builder dialog = new AlertDialog.Builder(HomeWindow.this);
            dialog.setTitle("Settings");

            // layout in the alert dialog
            layout = new LinearLayout(HomeWindow.this);
            parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(parms);
            layout.setGravity(Gravity.CLIP_VERTICAL);

            textView1 = new TextView(HomeWindow.this);
            textView1.setText(getString(R.string.settings1));
            textView1.setPadding(45, 15, 40, 15);
            textView1.setGravity(Gravity.LEFT);
            textView1.setTextSize(16);

            // spinner with a list to show the type of expression
            list = new ArrayAdapter<String>(HomeWindow.this, android.R.layout.simple_spinner_dropdown_item, expression);

            spinner = new Spinner(HomeWindow.this);
            spinner.setPadding(45, 15, 40, 15);
            spinner.setAdapter(list);

            textView2 = new TextView(HomeWindow.this);
            textView2.setText(getString(R.string.settings2));
            textView2.setPadding(45, 15, 40, 15);
            textView2.setGravity(Gravity.LEFT);
            textView2.setTextSize(16);

            // choose the time between 1 and 5
            numberPicker = new NumberPicker(HomeWindow.this);
            numberPicker.setPadding(45, 15, 40, 15);
            numberPicker.setGravity(Gravity.LEFT);
            numberPicker.setMinValue(200);
            numberPicker.setMaxValue(1000);

            // add to the layout the numberpicker, the spinner, and the 2 textviews
            ln = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView(textView1, ln);
            layout.addView(spinner, ln);
            layout.addView(textView2, ln);
            layout.addView(numberPicker, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            // add the layout in the alert dialog
            dialog.setView(layout);

            // save the selected type of expression into a variable
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int selected = parent.getSelectedItemPosition(); // a modifier
                    face = expression[selected];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // show button ok
            positiveText = getString(android.R.string.ok);
            dialog.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { // if click == true, run the second activity, to start the test with the selected parameters
                            time = numberPicker.getValue();
                            Intent intent = new Intent(HomeWindow.this, ImagesDisplayActivity.class);
                            intent.putExtra("graph1", numgraph1);
                            intent.putExtra("graph2", numgraph2);
                            intent.putExtra("graph3", numgraph3);
                            intent.putExtra("expression", face);
                            intent.putExtra("key", text.getText().toString());
                            intent.putExtra("time", time);
                            startActivity(intent);
                        }
                    }
            );

            builder = dialog.create();

            // display dialog
            builder.getWindow().getAttributes().windowAnimations = R.style.animationdialog;
            builder.show();


        }

    }

    /**
     * getter / setter
     * @return
     */
    public NumberPicker getNumberPicker() {
        return numberPicker;
    }

    public int getTime() {
        return time;
    }

    public String[] getExpression() {

        return expression;
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public int posSpinner(){
        return spinner.getSelectedItemPosition();
    }

    public String getFace() {
        return face;
    }

    public int numberPickerValue(){
        return numberPicker.getValue();
    }
}
