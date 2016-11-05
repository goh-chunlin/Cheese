package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;

public class ButtonVoiceInput {

    protected Activity mContext;

    protected static int check = -1;

    public ButtonVoiceInput(Activity activity){
        this.mContext = activity;
    }

    protected Intent CreateGoogleSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello!");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "zh");
        intent.putExtra(RecognizerIntent.EXTRA_RESULTS, "zh");

        return intent;
    }
}
