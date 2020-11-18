package com.example.hw1_avishakuri;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    private ImageView picPlayerWin;
    private TextView scorePlayerWin;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        findView();
        picPlayerWin.setImageResource(getIntent().getIntExtra("EXTRA_KEY_ID_IMAGE",0));
        scorePlayerWin.setText("The Score : "+ getIntent().getIntExtra("EXTRA_KEY_SCORE",0));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findView() {
        picPlayerWin = findViewById(R.id.second_IMG_winner);
        scorePlayerWin = findViewById(R.id.second_TXT_score);
        btn =findViewById(R.id.second_BTN_newGame);
    }
}