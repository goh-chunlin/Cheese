package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;

public class ButtonVoiceInputReturnsCantonese extends ButtonVoiceInput implements View.OnClickListener {

    public ButtonVoiceInputReturnsCantonese(Activity activity){
        super(activity);
    }

    @Override
    public void onClick(View v) {
        check = GoogleSpeech.Language.CANTONESE.ordinal();

        mContext.startActivityForResult(CreateGoogleSpeechInput(), check);
    }
}
