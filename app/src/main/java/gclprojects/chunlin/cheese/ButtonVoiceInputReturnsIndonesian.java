package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.view.View;

public class ButtonVoiceInputReturnsIndonesian extends ButtonVoiceInput implements View.OnClickListener {

    public ButtonVoiceInputReturnsIndonesian(Activity activity){
        super(activity);
    }

    @Override
    public void onClick(View v) {
        check = GoogleSpeech.Language.INDONESIAN.ordinal();

        mContext.startActivityForResult(CreateGoogleSpeechInput(), check);
    }
}
