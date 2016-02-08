package com.example.kaanu.challangelayout;

/**
 * Created by Kaanu on 10/6/2015.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    Button btnBack;
    TextView txtOutput, txtTotal;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoactivity);

        btnBack = (Button) findViewById(R.id.btnBack);
        txtOutput = (TextView) findViewById(R.id.txtOutput);
        txtTotal = (TextView) findViewById(R.id.txtTotal);

        Bundle mrBunbun = getIntent().getExtras();
        String insultCount = mrBunbun.getString("mrBunbun");
        String totalCount = mrBunbun.getString("mrBunbunTotal");
        txtOutput.setText(getResources().getString(R.string.count_text) + " " + insultCount);
        txtTotal.setText(getResources().getString(R.string.count_total_text) + " " + totalCount);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/hurry_up.ttf");

        txtOutput.setTypeface(typeface);
        txtTotal.setTypeface(typeface);
        btnBack.setTypeface(typeface);
        ((TextView)findViewById(R.id.txtInfo)).setTypeface(typeface);
        ((TextView)findViewById(R.id.txtTitle)).setTypeface(typeface);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
    }

}
