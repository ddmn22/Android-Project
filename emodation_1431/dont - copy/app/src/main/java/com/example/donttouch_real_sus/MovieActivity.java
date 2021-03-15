package com.example.donttouch_real_sus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {

    Button button;
    ImageView mImageView;
    int emotion_result;

    int[] happy_images = new int[]{R.drawable.movie_happy1, R.drawable.movie_happy2, R.drawable.movie_happy3, R.drawable.movie_happy4, R.drawable.movie_happy5, R.drawable.movie_happy6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        emotion_result = intent.getExtras().getInt("emotion2");

        button = (Button) findViewById(R.id.movie_btn);
        mImageView = (ImageView) findViewById(R.id.movie);
        button.setOnClickListener(t);

        switch (emotion_result) { //0-happy 1-calm 2-surprised 3-disgusted 4-confused 5-angry 6-sad
            case 0: //happy
                int imageId = (int) (Math.random() * happy_images.length);
                mImageView.setBackgroundResource(happy_images[imageId]);
                break;
        }
    }

    Button.OnClickListener t = new Button.OnClickListener() {
        public void onClick(View v) {
            switch (emotion_result) { //0-happy 1-calm 2-surprised 3-disgusted 4-confused 5-angry 6-sad
                case 0: //happy
                    int imageId = (int) (Math.random() * happy_images.length);
                    mImageView.setBackgroundResource(happy_images[imageId]);
                    break;
            }
        }
    };
}