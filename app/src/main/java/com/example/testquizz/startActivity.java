package com.example.testquizz;

import static com.example.testquizz.resultActivity.resultActivityWorked;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class startActivity extends AppCompatActivity implements View.OnClickListener {

  private   LinearLayout food_btn,general_btn,history_btn,geography_btn;
  private  Bundle bundle = new Bundle();
  public  static final String QUESTION_TYPE = "questionType";
    private  long BackPassedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        loadView();

        food_btn.setOnClickListener(this);
        general_btn.setOnClickListener(this);
        history_btn.setOnClickListener(this);
        geography_btn.setOnClickListener(this);
    }

    private void loadView() {
        food_btn = findViewById(R.id.Food_btn);
        general_btn = findViewById(R.id.General_btn );
        history_btn = findViewById(R.id.History_btn);
        geography_btn = findViewById(R.id.Geography_btn);
    }
    @Override
    public void onBackPressed() {
        if(BackPassedTime + 3000>System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "Dasturdan chiqish uchun yana bir bor bosing", Toast.LENGTH_SHORT).show();
        }

        BackPassedTime = System.currentTimeMillis();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.Food_btn:{

                if(resultActivityWorked){
                    setResult(1);
                    startActivity.this.finish();
                    Toast.makeText(this, "startActivity yopildi", Toast.LENGTH_SHORT).show();

                }else {

                    Intent intent = new Intent(startActivity.this, quizActivity.class);

                    bundle.putInt(QUESTION_TYPE, 1);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
                break;

            }
            case R.id.General_btn:{

                if(resultActivityWorked){
                    setResult(2);
                    startActivity.this.finish();
                    Toast.makeText(this, "startActivity yopildi", Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(startActivity.this, quizActivity.class);

                    bundle.putInt(QUESTION_TYPE, 2);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
                break;
            }
            case R.id.History_btn:{

                if(resultActivityWorked){
                    setResult(3);
                    startActivity.this.finish();
                    Toast.makeText(this, "startActivity yopildi", Toast.LENGTH_SHORT).show();
                }else {

                    Intent intent = new Intent(startActivity.this, quizActivity.class);

                    bundle.putInt(QUESTION_TYPE, 3);
                    intent.putExtras(bundle);

                    startActivity(intent);
                }

                break;
            }
            case R.id.Geography_btn:{
                if(resultActivityWorked){
                    setResult(4);
                    startActivity.this.finish();
                    Toast.makeText(this, "startActivity yopildi", Toast.LENGTH_SHORT).show();
                }else {

                Intent intent = new Intent(startActivity.this, quizActivity.class);

                bundle.putInt(QUESTION_TYPE,4);
                intent.putExtras(bundle);

                startActivity(intent);
                }

                break;
            }

        }

    }
}