package com.example.donttouch_real_sus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.Attribute;
import com.amazonaws.services.rekognition.model.DetectFacesRequest;
import com.amazonaws.services.rekognition.model.DetectFacesResult;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AmazonS3 s3Client;
    private TransferUtility transferUtility;
    private AmazonRekognition rekognitionClient;

    private static Handler mHandler;

    private ImageView preview;
    private CardView recognizeActionSection;
    private View loadingSection;
    private View loadingSection2;
    private TextView percentView;
    private View resultSection;
    private TextView emotionText;
    private Button btnmove;
    private int sendemotion = 10;

    private Uri currentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preview = findViewById(R.id.preview);
        recognizeActionSection = findViewById(R.id.recognizeActionSection);
        loadingSection = findViewById(R.id.loadingSection);
        loadingSection2 = findViewById(R.id.spinkit2);
        percentView = findViewById(R.id.percent);
        resultSection = findViewById(R.id.resultSection);
        emotionText = findViewById(R.id.emotionDesc);
        btnmove = findViewById(R.id.btn_move);

        // callback method to call credentialsProvider method.
        s3credentialsProvider();

        btnmove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("emotion", sendemotion);
                startActivity(intent); // 액티비티 이동
            }
        });

    }

    public void onChooseImageButtonClick(View v) {
        final Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    public void onRecognizeActionClick(View v) {
        loadingSection2.setVisibility(View.VISIBLE);
        new DetectFaceTask(rekognitionClient, detectFaceCallback).execute();
    }

    public void onNextSection(View v) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            loadingSection2.setVisibility(View.GONE);
            resultSection.setVisibility(View.GONE);
            recognizeActionSection.setVisibility(View.GONE);

            Context context = getApplicationContext();
            Uri uri = data.getData();
            String path = uri_path(context, uri);
            currentUri = uri;

            GlideApp.with(this).load(uri).into(preview);

            File uploadToS3 = new File(path);
            transferUtility = new TransferUtility(s3Client, getApplicationContext());

            TransferObserver transferObserver = transferUtility.upload(
                    Values.BUCKET_NAME,     /* The bucket to upload to */
                    Values.TARGET_FILE_NAME,    /* The key for the uploaded object */ //이후 fileName으로 바꿔보기
                    uploadToS3       /* The file where the data to upload exists */
            );
            transferObserver.setTransferListener(transferListener);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String uri_path(Context context, Uri contentUri) {
        String fileName = getFileName(contentUri);
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(context.getFilesDir() + File.separator + fileName);
            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void s3credentialsProvider() {
        // Initialize the AWS Credential
        // Amazon Cognito 인증 공급자를 초기화합니다
        CognitoCachingCredentialsProvider cognitoCachingCredentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                Values.POOL_ID, // 자격 증명 풀 ID
                Regions.AP_NORTHEAST_2 // 리전
        );

        createAmazonS3Client(cognitoCachingCredentialsProvider);
    }

    /**
     * Create a AmazonS3Client constructor and pass the credentialsProvider.
     *
     * @param credentialsProvider
     */
    public void createAmazonS3Client(CognitoCachingCredentialsProvider credentialsProvider) {
        // Create an S3 client
        s3Client = new AmazonS3Client(credentialsProvider);
        // Set the region of your S3 bucket
        s3Client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));

        rekognitionClient = new AmazonRekognitionClient(credentialsProvider);
        rekognitionClient.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
    }

    private final TransferListener transferListener = new TransferListener() {
        @Override
        public void onStateChanged(int id, TransferState state) {
            Logger.e("id = " + id + " state = " + state);
            if (state == TransferState.COMPLETED) {
                Toast.makeText(MainActivity.this, "이미지를 업로드 했습니다", Toast.LENGTH_SHORT).show();
                recognizeActionSection.setVisibility(View.VISIBLE);
                loadingSection.setVisibility(View.GONE);
                GlideApp.with(MainActivity.this).load(currentUri).into(preview);
            }
        }

        @SuppressLint("DefaultLocale")
        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            if (loadingSection.getVisibility() == View.GONE) {
                loadingSection.setVisibility(View.VISIBLE);
            }
            int percent = (int) (((float) bytesCurrent / bytesTotal) * 100);
            percentView.setText(String.format("%d%%", percent));
        }

        @Override
        public void onError(int id, Exception ex) {
            ex.printStackTrace();
        }
    };

    private final DetectFaceCallback detectFaceCallback = new DetectFaceCallback() {
        @Override
        public void onComplete(List<FaceDetail> list) {
            loadingSection2.setVisibility(View.GONE);
            resultSection.setVisibility(View.VISIBLE);
            if (!list.isEmpty()) {
                final List<com.amazonaws.services.rekognition.model.Emotion> emotions = list.get(0).getEmotions();
                if (!emotions.isEmpty()) {
                    String emotionName = Emotion.valueOf(emotions.get(0).getType()).toString();
                    //0-happy 1-calm 2-surprised 3-disgusted 4-confused 5- fear 6-angry 7-sad 10-not detected
                    if(emotionName.equals("행복")){
                        sendemotion = 0;
                    }else if(emotionName.equals("평온")){
                        sendemotion = 1;
                    }else if(emotionName.equals("놀람")){
                        sendemotion = 2;
                    }else if(emotionName.equals("혐")){
                        sendemotion = 3;
                    }else if(emotionName.equals("혼란")){
                        sendemotion = 4;
                    }else if(emotionName.equals("두려움")){
                        sendemotion = 5;
                    }else if(emotionName.equals("화남")){
                        sendemotion = 6;
                    }else if(emotionName.equals("슬픔")){
                        sendemotion = 7;
                    }else {
                        sendemotion = 10;
                    }

                    if(emotionName.equals("두려움")){
                        emotionName = "혼란";
                    }
                    emotionText.setText("당신의 현재 감정은 " + emotionName + " 입니다");
                } else emotionText.setText(R.string.emotion_not_found);
            } else emotionText.setText(R.string.emotion_not_found);
        }
    };

    private static class DetectFaceTask extends AsyncTask<Void, Void, List<FaceDetail>> {
        private final WeakReference<AmazonRekognition> rekognitionRef;
        private final DetectFaceCallback callback;

        DetectFaceTask(AmazonRekognition client, DetectFaceCallback callback) {
            this.rekognitionRef = new WeakReference<>(client);
            this.callback = callback;
        }

        @Override
        protected List<FaceDetail> doInBackground(Void... voids) {
            //noinspection SpellCheckingInspection
            final AmazonRekognition rekognition = rekognitionRef.get();

            if (rekognition == null) {
                return new ArrayList<>();
            }

            final DetectFacesRequest request = new DetectFacesRequest()
                    .withImage(new Image()
                            .withS3Object(new S3Object()
                                    .withName(Values.TARGET_FILE_NAME)
                                    .withBucket(Values.BUCKET_NAME)))
                    .withAttributes(Attribute.ALL.toString());

            final DetectFacesResult result = rekognition.detectFaces(request);
            return result.getFaceDetails();
        }

        @Override
        protected void onPostExecute(List<FaceDetail> result) {
            super.onPostExecute(result);
            callback.onComplete(result);
        }
    }
}

enum Emotion {
    HAPPY("행복"),
    CALM("평온"),
    SURPRISED("놀람"),
    DISGUSTED("혐"),
    CONFUSED("혼란"),
    FEAR("혼란"),
    ANGRY("화남"),
    SAD("슬픔");


    private String value;

    private Emotion(String emotionName) {
        this.value = emotionName;
    }


    @Override
    public String toString() {
        return value;
    }
}

interface DetectFaceCallback {
    public void onComplete(List<FaceDetail> list);
}