package gclprojects.chunlin.cheese;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

class Cognitive  extends AsyncTask<TextToSpeech, Void, String> {
    private Context mContext;
    private String mUrl;
    private TextToSpeech mTTS;

    Cognitive (Context context, String url, TextToSpeech tts) {
        mContext = context;
        mUrl = url;
        mTTS = tts;
    }

    @Override
    protected String doInBackground(TextToSpeech... params) {

        return Json.getJson(mUrl);

    }

    @Override
    protected void onPostExecute(String jsonResult) {
        super.onPostExecute(jsonResult);

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);

            JSONArray intents = jsonObject.getJSONArray("intents");

            if (intents.length() > 0) {

                JSONObject currentIntentObject = intents.getJSONObject(0);

                String intent = currentIntentObject.getString("intent");

                switch (intent) {
                    case "Translate":
                        try {
                            FileOutputStream outputStream = mContext.openFileOutput(
                                    mContext.getString(R.string.voice_file_name), Context.MODE_PRIVATE);

                            JSONArray actions = currentIntentObject.getJSONArray("actions");

                            if (actions.length() > 0) {

                                JSONObject currentActionObject = actions.getJSONObject(0);
                                JSONArray parameters = currentActionObject.getJSONArray("parameters");

                                if (parameters.length() > 1) {

                                    JSONObject currentParameterObjectForSentence = parameters.getJSONObject(0);
                                    JSONArray valueForSentence = currentParameterObjectForSentence.getJSONArray("value");

                                    JSONObject currentParameterObjectForTargetedLanguage = parameters.getJSONObject(1);
                                    JSONArray valueForTargetedLanguage = currentParameterObjectForTargetedLanguage.getJSONArray("value");


                                    if (valueForSentence.length() > 0) {

                                        JSONObject currentValueObject = valueForSentence.getJSONObject(0);
                                        String query = currentValueObject.getString("entity");

                                        String targetedLanguage;

                                        if (valueForTargetedLanguage.length() > 0) {
                                            currentValueObject = valueForTargetedLanguage.getJSONObject(0);
                                            String desiredTargetLanguage = currentValueObject.getString("entity");

                                            Log.d("Japanese Voice", "The desired target language is " + desiredTargetLanguage);

                                            if (desiredTargetLanguage.startsWith("日")) {
                                                targetedLanguage = "ja";

                                                new TextTranslator(mContext, query, targetedLanguage, mTTS).execute("");
                                            } else if (desiredTargetLanguage.startsWith("越南")) {
                                                targetedLanguage = "vi";

                                                new TextTranslator(mContext, query, targetedLanguage, mTTS).execute("");
                                            } else if (desiredTargetLanguage.startsWith("广东")) {
                                                targetedLanguage = "zh&region=guangdong";

                                                new TextTranslator(mContext, query, targetedLanguage, mTTS).execute("");
                                            } else if (desiredTargetLanguage.startsWith("英")) {
                                                targetedLanguage = "en";

                                                new TextTranslator(mContext, query, targetedLanguage, mTTS).execute("");
                                            } else if (desiredTargetLanguage.startsWith("印尼")) {
                                                targetedLanguage = "id";

                                                new TextTranslator(mContext, query, targetedLanguage, mTTS).execute("");
                                            }
                                        }



                                    } else {
                                        new JapaneseVoice(mContext, "わかりません").execute(outputStream);
                                    }

                                } else {
                                    new JapaneseVoice(mContext, "簡単に日本語でご利用ください").execute(outputStream);
                                }

                            } else {
                                new JapaneseVoice(mContext, "もう一回してください").execute(outputStream);
                            }


                        } catch (IOException ex) {
                            Log.d(mContext.getString(R.string.voice_file_name) + " Error", ex.getMessage());
                        }

                        break;
                }
            }
        } catch (JSONException ex) {
            Log.e("Cognitive JSON Error", ex.getMessage());
        }
    }
}
