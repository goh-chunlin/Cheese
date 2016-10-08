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

    Button btnChineseVoiceInput;

    public static TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChineseVoiceInput = (Button) findViewById(R.id.btnChineseVoiceInput);
        btnChineseVoiceInput.setOnClickListener(new ButtonChineseVoiceInput(this));

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

        if (requestCode == ButtonChineseVoiceInput.check && resultCode == Activity.RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (results.size() == 0) {
                Toast.makeText(this, "何と言いましたか？", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "あなたの言：" + results.get(0) , Toast.LENGTH_LONG).show();

                try {
                    String query = URLEncoder.encode(results.get(0), "utf-8");

                    new Json(this, "https://api.projectoxford.ai/luis/v1/application?" +
                            "id=5087c95c-e54d-4970-970e-7af4709cdb32&subscription-key=7a1b29b739a543e6ae63ae51aedcfbf0&" +
                            "q=" + query, tts).execute();

                } catch (UnsupportedEncodingException e) {

                    Log.e("Google Speech Input", e.getMessage());

                }

            }


        }
    }
}
