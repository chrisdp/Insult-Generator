package com.example.kaanu.challangelayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;

import com.google.gson.Gson;

public class ChallengeLayout extends AppCompatActivity {

    private String[] arrNoun, arrAdj;
    private String msg, mark;
    private Generator insultGen;
    private EditText txtInput;
    private TextView txtOutput, txtInputTitle;
    private Button btnGo, btnClear, btnInfo;
    private Bundle msBunbun;
    private Intent intent;
    private InputMethodManager inputMethodManager;
    private Toast toast;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // TODO more comments! maybe? yha... i should add more.... some day....

        arrNoun = getResources().getStringArray(R.array.noun_arr);
        arrAdj = getResources().getStringArray(R.array.adj_arr);

        btnGo = (Button) findViewById(R.id.btnGo);
        btnClear = (Button)findViewById(R.id.btnClear);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnGo.setOnClickListener(onClickListener);
        btnClear.setOnClickListener(onClickListener);
        btnInfo.setOnClickListener(onClickListener);
        txtInputTitle = (TextView) findViewById(R.id.txtInputTitle);
        txtInput = (EditText)findViewById(R.id.txtInput);
        txtOutput = (TextView)findViewById(R.id.txtOutput);
        msg = getResources().getString(R.string.msg);
        mark = getResources().getString(R.string.mark);

        msBunbun = new Bundle();
        intent = new Intent("com.example.kaanu.InfoActivity");
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        // typeface object
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/hurry_up.ttf");

        txtInput.setTypeface(typeface);
        txtInputTitle.setTypeface(typeface);
        txtOutput.setTypeface(typeface);
        sharedPreferences = getSharedPreferences("StoredData", MODE_PRIVATE);
        gson = new Gson();


        if (savedInstanceState == null){
            if (sharedPreferences.contains("genAsJSON")) {
                String tempy = sharedPreferences.getString("genAsJSON", "");
                Log.d("chris", tempy);
                insultGen = gson.fromJson(tempy, Generator.class);
            } else insultGen = new Generator(arrNoun, arrAdj);
        } else {
            insultGen = (Generator)savedInstanceState.getParcelable("gen");
            txtOutput.setText(savedInstanceState.getString("txtout"));
            //txtOutput.setText(txtInput.getText().toString() + " " + msg + insultGen.getLastMsg() + mark);
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challange_layout, menu);
        return true;
    }
*/

    @Override
    public void onPause(){
        super.onPause();
        insultGen.resetCount();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String genAsJSON = gson.toJson(insultGen);
        editor.putString("genAsJSON", genAsJSON);
        editor.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        // store the current text in the bundle for management
        outState.putString("txtout", txtOutput.getText().toString());
        // store the current Generator
        outState.putParcelable("gen", (Parcelable) insultGen);
        // call super class event handler and pass along out modified bundle
        super.onSaveInstanceState(outState);
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
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btnGo:
                    if(txtInput.length() > 0) {
                        keyboard_hide();
                        txtOutput.setText(txtInput.getText() + " " + msg + insultGen.insultMe() + mark);
                    } else { toastMe(getResources().getString(R.string.input_error)); }
                    break;
                case R.id.btnClear:
                    cleanUp();
                    break;
                case R.id.btnInfo:
                    String tempy = String.valueOf(insultGen.getInsultCount());
                    msBunbun.putString("mrBunbun", tempy);
                    msBunbun.putString("mrBunbunTotal", String.valueOf(insultGen.getInsultTotal()));
                    startActivity(intent.putExtras(msBunbun));
                    break;
            }
        }

    };

    private void cleanUp() {
        txtInput.setText("");
        txtOutput.setText(getResources().getString(R.string.output));
        insultGen.resetCount();
        keyboard_hide();
    }

    private void toastMe(String msg){
        toast.setText(msg);
        toast.show();
    }

    private void keyboard_hide() {
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}
