package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.view.View;

public class ButtonVoiceInputReturnsVietnamese extends ButtonVoiceInput implements View.OnClickListener {

    public ButtonVoiceInputReturnsVietnamese(Activity activity){
        super(activity);
    }

    @Override
    public void onClick(View v) {
        check = GoogleSpeech.Language.VIETNAMESE.ordinal();

        mContext.startActivityForResult(CreateGoogleSpeechInput(), check);
    }
}
