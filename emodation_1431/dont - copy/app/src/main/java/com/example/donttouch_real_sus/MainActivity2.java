package com.example.donttouch_real_sus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity2 extends AppCompatActivity {

    int emotion_result = 0; //감정 분석 결과 받아올 변수 0-happy 1-calm 2-surprised 3-disgusted 4-confused 5-angry 6-sad

    Button button;
    TextView textView1;
    TextView textView2;
    private Button btn_move;

    String[] happy_text = new String[]{
            "행복만이 우리가 평생 지켜야 할 것입니다.", "당신 곁에 있는 사랑은 대단히 편안하고 또한 매력적입니다.", "대부분의 사람들은 마음 먹은 만큼 행복합니다."
            ,"그 결과가 오랫동안 영향을 미칠 것입니다." , "주파수가 비슷한 사람과 함께 할 수 있다는 것은 축복이에요.", "행복에는 한계가 없습니다."
    };

    String[] calm_text = new String[]{
            "꿈도 기억나지 않을 깊은 잠을 자길 바라요.", "하루하루는 미묘하게 다르고, 그 차이는 당신이 만드는 것입니다.", "내가 가진 것 중에 오래된 것의 비결을 생각해보세요."
            ,"무슨 일이든 매일 반복하고 가다듬는 것이 필요해요." , "그 사람에 대해 깊이 알수록 더 넓은 감정을 나눌 수 있어요."
    };

    String[] surprised_text = new String[]{
            "때로는 설명하지 않는 것이 더 나을 때도 있어요.", "앞으로도 같이 살아가기 위한 예행연습이라고 생각해보는 건 어떨까요.", "잠시 동안 곤란을 겪거나 멈추어 있는 것은 그 나름대로의 가치가 있는 것입니다."
            ,"숭고한 생각을 가진 사람은 절대 혼자가 아닙니다." , "주변의 친한 이들과 상의해보세요. 분명 도움이 될 것입니다."
    };

    String[] disgusted_text = new String[]{
            "모든 사람들을 이해할 수는 없지만, 다양한 사람들이 존재한다는 걸 인정할 수 있어야 해요.", "도와주는 것과 소비되는 것에는 차이가 있어요."
            ,"그 사람을 미워하는 것이 스스로를 괴롭힐 수도 있어요." , "'이해보다 인정'. 때로는 이해할 수 없어도 인정해야 합니다."
    };

    String[] confused_text = new String[]{ // aws result: confused + fear
            "부족한 자기자신을 그대로 아껴줘야 해요.", "어디에 있는 가가 아니라 어느 쪽을 향해 가고 있는지 생각해보세요.", "나는 정말 다양한 나와 살아가고 있어요."
            ,"당신을 지키는 사람도 많고, 당신이 지키는 사람도 많습니다." , "왜 그렇게 되었는지 파고들어서 정답을 찾으려 하지 말아요.", "나의 모습은 어제도 다르고 오늘도 다르고 내일도 다를 거에요."
            ,"당신의 꿈은 계속될 것이고, 마침내 이루어 질 거에요." , "노력하고 있기 때문에 방황하는 것입니다.", "평생을 연구해도 부족한 사람이 바로 우리 자신이에요."
            ,"본인이 믿는 것에 대해 불안해하지 말아요."
    };

    String[] angry_text = new String[]{
            "세상을 바꾸려는 시도도 좋지만 스스로 조화로운 사람이 되는 것도 중요합니다.", "알면서도 이해하면서도 서로 부딪힐 때가 있습니다.", "표현되지 못한 감정은 사라지지 않고 예고도 없는 폭발이 됩니다."
            ,"이해할 수 없는 순간에는 다양한 사람이 있다는 것을 인정해야 합니다." , "싫은 소리를 해도 되는 상황에 하면 싫지 않은 소리가 될 수 있어요."
    };

    String[] sad_text = new String[]{
            "눈물을 참는 것이 아니라 눈물을 쏟아낼 수 있어야 해요.", "큰 사랑을 받은 사람은 어떤 고난도 이겨낼 수 있다고 들었습니다.", "마음속의 풍부한 감정들을 표현할 누군가가 당신에게 필요합니다."
            ,"위로해줘서가 아니라 아픔을 드러내줘서 위로가 되는 거에요." , "나의 슬픔을 남의 슬픔과 비교하지 말아요.", "가끔은 남들이 이해 못할 나의 슬픔이 있답니다."
            ,"마음속의 풍부한 감정들을 표현할 누군가가 당신에게 필요합니다." , "입버릇처럼 하는 괜찮다는 거짓말은 하지 말아요.", "작별 인사에 낙담하지 마세요. 재회에 앞서 작별은 꼭 필요합니다."
            ,"슬프게도 어떤 순간에는 진심이 아닌 말들도 진심처럼 말해야 할 때가 있는 것 같아요."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = (Button) findViewById(R.id.letter_button);
        textView1 = (TextView) findViewById(R.id.emotion);
        textView2 = (TextView) findViewById(R.id.letter);
        button.setOnClickListener(t);

        Intent intent = getIntent();
        emotion_result = intent.getIntExtra("emotion", 10);

        btn_move = findViewById(R.id.move_button);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.donttouch_real_sus.MainActivity2.this, RecommendActivity.class);
                intent.putExtra("emotion", emotion_result);
                startActivity(intent); // 액티비티 이동
            }
        });

        switch (emotion_result) {
            case 0: //happy
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'행복' 입니다"));
                break;
            case 1: //calm
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'평온' 입니다"));
                break;
            case 2: //surprised
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'놀람' 입니다"));
                break;
            case 3: //disgusted
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'혐오' 입니다"));
                break;
            case 4: //confused
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'두려움' 입니다"));
                break;
            case 5: //angry
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'혼란' 입니다"));
                break;
            case 6: //sad
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'화남' 입니다"));
                break;
            case 7: //confused
                textView1.setText(String.valueOf("당신의 현재 감정은 \n'슬픔' 입니다"));
                break;
        }
    }

    Button.OnClickListener t = new Button.OnClickListener() {
        public void onClick(View v) {
            Random random = new Random();
            int index;
            switch(emotion_result) { //0-happy 1-calm 2-surprised 3-disgusted 4-confused 5-angry 6-sad
                case 0: //happy
                    index = random.nextInt(happy_text.length);
                    textView2.setText(happy_text[index]);
                    break;
                case 1: //calm
                    index = random.nextInt(calm_text.length);
                    textView2.setText(calm_text[index]);
                    break;
                case 2: //surprised
                    index = random.nextInt(surprised_text.length);
                    textView2.setText(surprised_text[index]);
                    break;
                case 3: //disgusted
                    index = random.nextInt(disgusted_text.length);
                    textView2.setText(disgusted_text[index]);
                    break;
                case 4: //fear
                case 5: //confused
                    index = random.nextInt(confused_text.length);
                    textView2.setText(confused_text[index]);
                    break;
                case 6: //angry
                    index = random.nextInt(angry_text.length);
                    textView2.setText(angry_text[index]);
                    break;
                case 7: //sad
                    index = random.nextInt(sad_text.length);
                    textView2.setText(sad_text[index]);
                    break;
            }
        }
    };
}