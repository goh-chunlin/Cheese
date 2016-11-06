package gclprojects.chunlin.cheese;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;


public class TextTranslator extends AsyncTask<String, Void, String> {
    private Context mContext;
    private Activity mActivity;
    private String mQuery;
    private String mUrl;
    private String mTargetLanguage;
    private TextToSpeech mTTS;
    private ProgressDialog mProgress;

    public TextTranslator(Activity activity, String query, String targetedLanguage, TextToSpeech tts, ProgressDialog progress) {
        mContext = activity.getApplicationContext();
        mActivity = activity;
        mQuery = query;
        mTTS = tts;
        mProgress = progress;

        String encodedQuery = "";

        try {
            encodedQuery = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Log.e("Encode Query", ex.getMessage());
        }

        mUrl = "https://www.googleapis.com/language/translate/v2?" +
                "key=" + mContext.getString(R.string.GOOGLE_SPEECH_API_KEY) + "&" +
                "q=" + encodedQuery +
                "&source=zh&target=" + targetedLanguage;

        mTargetLanguage = targetedLanguage;
    }

    protected String doInBackground(String... textToTranslate) {

        return mTargetLanguage.equals("zh&region=guangdong") ? mQuery : Json.getJson(mUrl);

    }

    protected void onPostExecute(String jsonResult) {
        try {
            String translationResultText;

            if (!Json.isJsonValid(jsonResult)) {

                translationResultText = jsonResult;

            } else {
                JSONObject jsonObject = new JSONObject(jsonResult);

                JSONObject data = jsonObject.getJSONObject("data");

                JSONArray translations = data.getJSONArray("translations");

                translationResultText = translations.getJSONObject(0).getString("translatedText");
            }

            final String translatedText = translationResultText;

            TextView labelResultText = (TextView) mActivity.findViewById(R.id.labelResultText);
            labelResultText.setVisibility(View.VISIBLE);

            TextView resultText = (TextView) mActivity.findViewById(R.id.resultText);
            resultText.setText(translatedText);
            resultText.setVisibility(View.VISIBLE);

            mActivity.setProgressBarVisibility(false);

            switch (mTargetLanguage) {
                case "ja":
                    FileOutputStream outputStream = mContext.openFileOutput(mContext.getString(R.string.voice_file_name), Context.MODE_PRIVATE);
                    new JapaneseVoice(mContext, translatedText, mProgress).execute(outputStream);
                    break;

                case "vi":
                    getTextToSpeechFromGoogle(new Locale("vi_VN"), translatedText);
                    break;

                case "zh&region=guangdong":
                    getTextToSpeechFromGoogle(new Locale("yue", "HK"), translatedText);
                    break;

                case "en":
                    getTextToSpeechFromGoogle(Locale.UK, translatedText);
                    break;

                case "id":
                    getTextToSpeechFromGoogle(new Locale("id"), translatedText);
                    break;

            }


        } catch (JSONException ex) {

            Log.e("Parse JSON from Speech", ex.getMessage());

        } catch (FileNotFoundException ex) {

            Log.e(mContext.getString(R.string.voice_file_name) + " Not Found", ex.getMessage());

        }
    }

    private TextToSpeech getTextToSpeechFromGoogle(final Locale targetedLocale, final String translatedText) {
        mProgress.dismiss();

        return new TextToSpeech(mContext,
                new TextToSpeech.OnInitListener() {

                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
                            mTTS.setLanguage(targetedLocale);
                            String utteranceId = this.hashCode() + "";

                            mTTS.speak(translatedText, TextToSpeech.QUEUE_FLUSH, null, utteranceId);


                        }
                    }

                });
    }
}
