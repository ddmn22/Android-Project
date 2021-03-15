package com.example.donttouch_real_sus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MusicActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    Random random = new Random();
    int emotion_result;

    String[] happy_music = new String[]{
            "샤이니 - 줄리엣", "아이들 - 덤디덤디", "Vulfpeck - wait for the moment", "블랙핑크 - forever young", "싹쓰리 - 다시 한번 바닷가", "아이유-Blueming",
            "키썸 - 심상치않아", "테일러 스위프트 - 크루셜", "혁오-tomboy", "오마이걸 - 돌핀",
    };

    String[] calm_music = new String[]{
            "Lauv- love somebody", "Iann dior - sick and tired", "Stella Jang - Villain", "태연 - 스트레스", "볼빨간 사춘기 - 나비와 고양이",
            "폴킴 - 집돌이" , "크러쉬 - ohio", "Loote - Lost", "다린 - 가을", "아리아나 그란데 - one last time"
    };

    String[] surprised_music = new String[]{
            "박재범 - all i wanna do" , "폴킴 - 있잖아"
    };

    String[] disgusted_music = new String[]{
            "빅뱅 - 베베", "자우림-낙화", "앰비션 - 밴드", "Coldplay - clocks", "닐로 - 바보", "더콰이엇 - 귀감", "Zedd, Kehlani - Good Thing"
    };

    String[] confused_music = new String[]{
            "Khalid - hopeless", "Idle town - conan gray", "스텔라장 - 보통날의 기적", "kyle - ipsy", "billie eilish - you should see me in a crown",
            "크러쉬 - 나빠", "폴킴 - 길", "정승환 - 눈사람", "Pitbull - Celebrate", "비룡 - 신난다"
    };

    String[] angry_music = new String[]{
            "데이식스 - Shoot me", "방탄소년단-I'm fine", "슈가볼 - 농담반 진담반", "이해리 - 우는 법을 잊어버렸나요", "블랙핑크 - how you like that",
            "하현우 - 돌덩이", "한요한 - 범퍼카", "쇼팽 - 프렐류드 15번", "Lauv - Paris in the rain", "서영은 - 혼자가 아닌 나"
    };

    String[] sad_music = new String[]{
            "Lizzo - juice", "방탄소년단 - 소년", "이승철 - 서쪽하늘", "조용필 - 걷고싶다", "DeBarge - I like it", "아이유 - 자장가", "Ash island-paranoid",
            "릴러말즈 - 야망", "백현 - 공중정원", "넬 - counting pulse"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent intent = getIntent();
        emotion_result = intent.getExtras().getInt("emotion2");

        button = (Button) findViewById(R.id.music_btn);
        textView = (TextView) findViewById(R.id.music);
        button.setOnClickListener(t);

        switch (emotion_result) {
            case 0: //happy
                textView.setText(happy_music[random.nextInt(happy_music.length)]);
                break;
            case 1: //calm
                textView.setText(calm_music[random.nextInt(calm_music.length)]);
                break;
            case 2: //surprised
                textView.setText(surprised_music[random.nextInt(surprised_music.length)]);
                break;
            case 3: //disgusted
                textView.setText(disgusted_music[random.nextInt(disgusted_music.length)]);
                break;
            case 4: //fear
            case 5: //confused
                textView.setText(confused_music[random.nextInt(confused_music.length)]);
                break;
            case 6: //angry
                textView.setText(angry_music[random.nextInt(angry_music.length)]);
                break;
            case 7: //sad
                textView.setText(sad_music[random.nextInt(sad_music.length)]);
                break;
        }
    }


    Button.OnClickListener t = new Button.OnClickListener() {
        public void onClick(View v) {
            int index;
            switch(emotion_result) { //0-happy 1-calm 2-surprised 3-disgusted 4-confused 5-angry 6-sad
                case 0: //happy
                    index = random.nextInt(happy_music.length);
                    textView.setText(happy_music[index]);
                    break;
                case 1: //calm
                    index = random.nextInt(calm_music.length);
                    textView.setText(calm_music[index]);
                    break;
                case 2: //surprised
                    index = random.nextInt(surprised_music.length);
                    textView.setText(surprised_music[index]);
                    break;
                case 3: //disgusted
                    index = random.nextInt(disgusted_music.length);
                    textView.setText(disgusted_music[index]);
                    break;
                case 4: //fear
                case 5: //confused
                    index = random.nextInt(confused_music.length);
                    textView.setText(confused_music[index]);
                    break;
                case 6: //angry
                    index = random.nextInt(angry_music.length);
                    textView.setText(angry_music[index]);
                    break;
                case 7: //sad
                    index = random.nextInt(sad_music.length);
                    textView.setText(sad_music[index]);
                    break;
            }
        }
    };

}