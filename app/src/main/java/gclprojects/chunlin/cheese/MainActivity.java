package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int LANGUAGE_LIST_REQUEST_CODE = 100;

    Button btnVoiceInputTranslation;

    TextView labelSourceText;
    TextView sourceText;
    TextView labelResultText;
    TextView resultText;

    ProgressDialog progress;

    public static TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent languagePicker = new Intent(this, LanguageListActivity.class);

        btnVoiceInputTranslation = (Button) findViewById(R.id.btnVoiceInputTranslation);
        btnVoiceInputTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(languagePicker, LANGUAGE_LIST_REQUEST_CODE);
            }
        });

        labelSourceText = (TextView) findViewById(R.id.labelSourceText);

        labelResultText = (TextView) findViewById(R.id.labelResultText);

        sourceText = (TextView) findViewById(R.id.sourceText);

        resultText = (TextView) findViewById(R.id.resultText);

        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.CHINESE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == LANGUAGE_LIST_REQUEST_CODE)
            {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello!");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "zh");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
                intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, "zh");
                intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "zh");
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "zh");
                intent.putExtra(RecognizerIntent.EXTRA_RESULTS, "zh");

                startActivityForResult(intent, data.getIntExtra(LanguageListActivity.targetedLanguageRequestCode, -1));

            } else {

                String targetedLanguage = GetTargetedLanguage(requestCode);

                if (!targetedLanguage.isEmpty()) {
                    progress = ProgressDialog.show(this, "翻译中...", "请稍等，正在处理您的讯息。", true);

                    ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    if (results.size() == 0) {

                        Toast.makeText(this, "请问你说什么呢?", Toast.LENGTH_LONG).show();

                    } else {

                        labelSourceText.setVisibility(View.VISIBLE);
                        labelResultText.setVisibility(View.INVISIBLE);
                        resultText.setVisibility(View.INVISIBLE);

                        sourceText.setText(results.get(0));

                        try {

                            String query = URLEncoder.encode("请翻译" + results.get(0) + "去" + targetedLanguage, "utf-8");

                            new Cognitive(this, "https://api.projectoxford.ai/luis/v1/application?" +
                                    "id=" + this.getString(R.string.MICROSOFT_COGNITIVE_ID) + "&" +
                                    "subscription-key=" + this.getString(R.string.MICROSOFT_COGNITIVE_SUBSCRIPTION_KEY) + "&" +
                                    "q=" + query, tts, progress).execute();

                        } catch (UnsupportedEncodingException e) {

                            Log.e("Google Speech Input", e.getMessage());

                        }

                    }

                }
            }
        }
    }

    private String GetTargetedLanguage(int requestCode) {

        String[] languageNames = getResources().getStringArray(R.array.language_names);

        if (requestCode >= 0 && requestCode < languageNames.length)
        {
            return languageNames[requestCode];
        }

        return "";

    }
}
