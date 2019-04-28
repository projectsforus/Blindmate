package org.tensorflow.lite.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.demo.ClassifierActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OutputActivity extends AppCompatActivity {

    ImageView ImageV;
    TextView TextV;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        ImageV = findViewById(R.id.iv_output_img);
        TextV = findViewById(R.id.tv_output_answer);

        String Answer = getIntent().getExtras().getString("Answer");
        List<String> Out = resultSubStr(Answer);
        TextV.setText(Out.get(0));
        /*for (String out:Out){
            TextV.setText(TextV.getText() + out + "\n");
        }*/


        Bitmap Img = (Bitmap) getIntent().getExtras().get("Img");
        ImageV.setImageBitmap(Img);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.US);
                tts.speak(TextV.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(OutputActivity.this, ClassifierActivity.class);
                finish();
                startActivity(intent);
            }
        }, 3000);
    }

    public List<String> resultSubStr(String Result){
        String[] arr;
        List<String> Out = new ArrayList<>();
        arr = Result.split(",");
        for(String ar :arr){
            Out.add(ar.substring(ar.indexOf("] ") + 2, ar.indexOf(" (")));
        }
        return Out;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ClassifierActivity.class);
        finish();
        startActivity(intent);

    }
}
