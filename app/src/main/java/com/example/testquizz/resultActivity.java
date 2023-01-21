package com.example.testquizz;

import static com.example.testquizz.startActivity.QUESTION_TYPE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class resultActivity extends AppCompatActivity {

    private Button totalTrues,totalWrongs,totalQuestions;
    public final static String KEY_RESULT = "key_result";
    private ResultData resultData;
    public static boolean resultActivityWorked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        resultActivityWorked = true;

        resultData = getIntent().getParcelableExtra(KEY_RESULT);
        loadView();
        showResults();

    }

    public void backButton(View view){
        Intent intent = new Intent(resultActivity.this,startActivity.class);
        resultLauncher.launch(intent);
    }
    public void finishButton(View view){
        setResult(0);
        finish();
    }
    private void  showResults(){
        totalTrues.setText(String.valueOf(resultData.getTotalTrues()));
        totalWrongs.setText(String.valueOf(resultData.getTotalWrongs()));
        totalQuestions.setText(String.valueOf(resultData.getTotalQuestions()));
    }
    private  void loadView (){
        totalQuestions = findViewById(R.id.total);
        totalTrues = findViewById(R.id.true_answers);
        totalWrongs = findViewById(R.id.wrong_answers);
    }
    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            int code = result.getResultCode();
            if(code == 1){
            setResult(1);
            finish();
            }else if(code == 2){
                setResult(2);
                finish();
            }else if(code == 3){
                setResult(3);
                finish();
            }
            else if(code == 4){
                setResult(4);
                finish();
            }
        }
    });
}