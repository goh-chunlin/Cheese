package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class ButtonChineseVoiceInput implements View.OnClickListener {

    static final int check = 999;

    private Activity mContext;

    public ButtonChineseVoiceInput(Activity activity){
        this.mContext = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello!");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_RESULTS, "zh");

        mContext.startActivityForResult(intent, check);
    }
}
