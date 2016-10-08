package gclprojects.chunlin.cheese;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

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
    private String mQuery;
    private String mUrl;
    private String mTargetLanguage;
    private TextToSpeech mTTS;

    public TextTranslator(Context context, String query, String targetedLanguage, TextToSpeech tts) {
        mContext = context;
        mQuery = query;
        mTTS = tts;

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

            switch (mTargetLanguage) {
                case "ja":
                    FileOutputStream outputStream = mContext.openFileOutput("myvoice.mp3", Context.MODE_PRIVATE);
                    new JapaneseVoice(mContext, translatedText).execute(outputStream);
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

            Log.e("myvoice.mp4 Not Found", ex.getMessage());

        }
    }

    private TextToSpeech getTextToSpeechFromGoogle(final Locale targetedLocale, final String translatedText) {
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
