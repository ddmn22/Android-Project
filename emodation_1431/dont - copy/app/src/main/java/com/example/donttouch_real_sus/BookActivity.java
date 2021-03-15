package com.example.donttouch_real_sus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class BookActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    Random random = new Random();
    int emotion_result;

    String[] happy_book = new String[]{
            "Joan K. Rowling 「Harry Potter Series」", "백석 「나와 나타샤와 흰당나귀」", "George R.R. Martin 「얼음과 불의 노래」", "스미노 요루 「너의 췌장을 먹고 싶어」"
    };

    String[] calm_book = new String[]{
            "히가시노 게이고 「악의」", "윤이수 「구르미 그린 달빛」", "신준모 「어떤하루」", "엠제이 드마코 「부의 추월차선」"
    };

    String[] surprised_book = new String[]{
            "이윤영「글쓰기가 만만해지는 하루 10분 메모 글쓰기」", "조앤 I. 로젠버그「인생을 바꾸는 90초」", "Alighieri Dante「신곡」"
    };

    String[] disgusted_book = new String[]{
            "히가시노 게이고「가면산장 살인사건」", "미나토 가나에「고백」", "이서윤, 홍주연「The Having」"
    };

    String[] confused_book = new String[]{
            "할 엘로드「미라클 모닝」", "Hermann Hesse「Demian」", "혜민스님「멈추면 비로소 보이는 것들」"
    };

    String[] angry_book = new String[]{
            "무라카미 하루키「노르웨이의 숲」", "히가시노 게이고「나미야 잡화점의 기적」", "「삼국지」", "미치오 슈스케「해바라기가 피지 않는 여름」"
    };

    String[] sad_book = new String[]{
            "이성복 시인「서해」", "김진애「한 번은 독해져라」", "「신춘문예 당선시집」","신경숙「깊은 슬픔」", "롤랑 바르트「애도일기」",
            "김초엽「원통 안의 소녀」","태수,문정「1cm 다이빙」", "이기주「언어의 온도」", "신준모「어떤 하루」"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        emotion_result = intent.getExtras().getInt("emotion2");

        button = (Button) findViewById(R.id.book_btn);
        textView = (TextView) findViewById(R.id.book);
        button.setOnClickListener(t);

        switch (emotion_result) {
            case 0: //happy
                textView.setText(happy_book[random.nextInt(happy_book.length)]);
                break;
            case 1: //calm
                textView.setText(calm_book[random.nextInt(calm_book.length)]);
                break;
            case 2: //surprised
                textView.setText(surprised_book[random.nextInt(surprised_book.length)]);
                break;
            case 3: //disgusted
                textView.setText(disgusted_book[random.nextInt(disgusted_book.length)]);
                break;
            case 4: //fear
            case 5: //confused
                textView.setText(confused_book[random.nextInt(confused_book.length)]);
                break;
            case 6: //angry
                textView.setText(angry_book[random.nextInt(angry_book.length)]);
                break;
            case 7: //sad
                textView.setText(sad_book[random.nextInt(sad_book.length)]);
                break;
        }
    }

    Button.OnClickListener t = new Button.OnClickListener() {
        public void onClick(View v) {
            int index;
            switch(emotion_result) { //0-happy 1-calm 2-surprised 3-disgusted 4-confused 5-angry 6-sad
                case 0: //happy
                    index = random.nextInt(happy_book.length);
                    textView.setText(happy_book[index]);
                    break;
                case 1: //calm
                    index = random.nextInt(calm_book.length);
                    textView.setText(calm_book[index]);
                    break;
                case 2: //surprised
                    index = random.nextInt(surprised_book.length);
                    textView.setText(surprised_book[index]);
                    break;
                case 3: //disgusted
                    index = random.nextInt(disgusted_book.length);
                    textView.setText(disgusted_book[index]);
                    break;
                case 4://fear
                case 5://confused
                    index = random.nextInt(confused_book.length);
                    textView.setText(confused_book[index]);
                    break;
                case 6: //angry
                    index = random.nextInt(angry_book.length);
                    textView.setText(angry_book[index]);
                    break;
                case 7: //sad
                    index = random.nextInt(sad_book.length);
                    textView.setText(sad_book[index]);
                    break;
            }
        }
    };

}