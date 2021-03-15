package com.example.donttouch_real_sus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecommendActivity extends AppCompatActivity {

    private Button btn_move1;
    private Button btn_move2;
    private Button btn_move3;
    int emotion_result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Intent intent = getIntent();
        final int emotion_result = intent.getExtras().getInt("emotion");
        emotion_result2 = emotion_result;

        btn_move1 = findViewById(R.id.button_music);
        btn_move1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.donttouch_real_sus.RecommendActivity.this, MusicActivity.class);
                intent.putExtra("emotion2", emotion_result2);
                startActivity(intent); // 액티비티 이동
            }
        });

        btn_move2 = findViewById(R.id.button_movie);
        btn_move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.donttouch_real_sus.RecommendActivity.this, MovieActivity.class);
                intent.putExtra("emotion2", emotion_result2);
                startActivity(intent); // 액티비티 이동
            }
        });

        btn_move3 = findViewById(R.id.button_book);
        btn_move3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.donttouch_real_sus.RecommendActivity.this, BookActivity.class);
                intent.putExtra("emotion2", emotion_result2);
                startActivity(intent); // 액티비티 이동
            }
        });
    }
}