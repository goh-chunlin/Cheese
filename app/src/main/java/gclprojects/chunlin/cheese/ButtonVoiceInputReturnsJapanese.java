package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;

public class ButtonVoiceInputReturnsJapanese extends ButtonVoiceInput implements View.OnClickListener {

    public ButtonVoiceInputReturnsJapanese(Activity activity){
        super(activity);
    }

    @Override
    public void onClick(View v) {
        check = GoogleSpeech.Language.JAPANESE.ordinal();

        mContext.startActivityForResult(CreateGoogleSpeechInput(), check);
    }
}
