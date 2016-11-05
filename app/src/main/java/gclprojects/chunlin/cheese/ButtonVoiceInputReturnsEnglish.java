package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.view.View;

public class ButtonVoiceInputReturnsEnglish extends ButtonVoiceInput implements View.OnClickListener {

    public ButtonVoiceInputReturnsEnglish(Activity activity){
        super(activity);
    }

    @Override
    public void onClick(View v) {
        check = GoogleSpeech.Language.ENGLISH.ordinal();

        mContext.startActivityForResult(CreateGoogleSpeechInput(), check);
    }
}
