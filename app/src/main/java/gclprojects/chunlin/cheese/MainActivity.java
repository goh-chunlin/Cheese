package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnVoiceInputReturnsJapanese;
    Button btnVoiceInputReturnsCantonese;
    Button btnVoiceInputReturnsEnglish;
    Button btnVoiceInputReturnsIndonesian;
    Button btnVoiceInputReturnsVietnamese;

    public static TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVoiceInputReturnsJapanese = (Button) findViewById(R.id.btnVoiceInputReturnsJapanese);
        btnVoiceInputReturnsJapanese.setOnClickListener(new ButtonVoiceInputReturnsJapanese(this));

        btnVoiceInputReturnsCantonese = (Button) findViewById(R.id.btnVoiceInputReturnsCantonese);
        btnVoiceInputReturnsCantonese.setOnClickListener(new ButtonVoiceInputReturnsCantonese(this));

        btnVoiceInputReturnsEnglish = (Button) findViewById(R.id.btnVoiceInputReturnsEnglish);
        btnVoiceInputReturnsEnglish.setOnClickListener(new ButtonVoiceInputReturnsEnglish(this));

        btnVoiceInputReturnsIndonesian = (Button) findViewById(R.id.btnVoiceInputReturnsIndonesian);
        btnVoiceInputReturnsIndonesian.setOnClickListener(new ButtonVoiceInputReturnsIndonesian(this));

        btnVoiceInputReturnsVietnamese = (Button) findViewById(R.id.btnVoiceInputReturnsVietnamese);
        btnVoiceInputReturnsVietnamese.setOnClickListener(new ButtonVoiceInputReturnsVietnamese(this));

        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.CHINESE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            String targetedLanguage = GetTargetedLanguage(requestCode);

            if (!targetedLanguage.isEmpty()) {

                ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (results.size() == 0) {
                    Toast.makeText(this, "何と言いましたか？", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "あなたの言：" + results.get(0) , Toast.LENGTH_LONG).show();

                    try {
                        String query = URLEncoder.encode("请翻译" + results.get(0) + "去" + targetedLanguage, "utf-8");

                        new Json(this, "https://api.projectoxford.ai/luis/v1/application?" +
                                "id=" + this.getString(R.string.MICROSOFT_COGNITIVE_ID) + "&" +
                                "subscription-key=" + this.getString(R.string.MICROSOFT_COGNITIVE_SUBSCRIPTION_KEY) + "&" +
                                "q=" + query, tts).execute();

                    } catch (UnsupportedEncodingException e) {

                        Log.e("Google Speech Input", e.getMessage());

                    }

                }

            }

        }
    }

    private String GetTargetedLanguage(int requestCode) {

        if (requestCode == GoogleSpeech.Language.JAPANESE.ordinal())
            return "日文";

        if (requestCode == GoogleSpeech.Language.CANTONESE.ordinal())
            return "广东话";

        if (requestCode == GoogleSpeech.Language.ENGLISH.ordinal())
            return "英文";

        if (requestCode == GoogleSpeech.Language.INDONESIAN.ordinal())
            return "印尼文";

        if (requestCode == GoogleSpeech.Language.VIETNAMESE.ordinal())
            return "越南文";

        return "";

    }
}
