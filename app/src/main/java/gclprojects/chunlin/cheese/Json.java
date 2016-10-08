package gclprojects.chunlin.cheese;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class Json extends AsyncTask<TextToSpeech, Void, String> {
    private Context mContext;
    private String mUrl;
    private TextToSpeech mTTS;

    public Json (Context context, String url, TextToSpeech tts) {
        mContext = context;
        mUrl = url;
        mTTS = tts;
    }

    @Override
    protected String doInBackground(TextToSpeech... params) {

        return getJson(mUrl);

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
                    case "问候":
                        mTTS = new TextToSpeech(mContext,
                                new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if(status != TextToSpeech.ERROR){
                                            mTTS.setLanguage(Locale.CHINESE);
                                            String utteranceId = this.hashCode() + "";
                                            mTTS.speak("你好！", TextToSpeech.QUEUE_FLUSH, null, utteranceId);


                                        }
                                    }
                                });

                        break;

                    case "叹气":
                        mTTS = new TextToSpeech(mContext,
                                new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if(status != TextToSpeech.ERROR){
                                            mTTS.setLanguage(Locale.CHINESE);
                                            String utteranceId = this.hashCode() + "";
                                            mTTS.speak("加油！加油！", TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                        }
                                    }
                                });

                        break;

                    case "GetTime":
                        mTTS = new TextToSpeech(mContext,
                                new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int status) {
                                        if(status != TextToSpeech.ERROR){
                                            mTTS.setLanguage(Locale.CHINESE);
                                            String utteranceId = this.hashCode() + "";

                                            Calendar calendar = Calendar.getInstance();
                                            int hour = calendar.get(Calendar.HOUR);
                                            int minute = calendar.get(Calendar.MINUTE);
                                            int second = calendar.get(Calendar.SECOND);

                                            if (hour == 0) {
                                                hour = 12;
                                            }

                                            mTTS.speak("现在的时间是" + hour + "点" + minute + "分" + second + "秒",
                                                    TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                                        }
                                    }
                                });

                        break;

                    case "Translate":
                        try {
                            FileOutputStream outputStream = mContext.openFileOutput("myvoice.mp3", Context.MODE_PRIVATE);

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
                            Log.d("myvoice.mp3 Error", ex.getMessage());
                        }

                        break;
                }
            }
        } catch (JSONException ex) {
            Log.e("Cognitive JSON Error", ex.getMessage());
        }
    }

    public static String getJson(String url) {
        HttpURLConnection connection = null;

        try{
            URL desiredUrl = new URL(url);
            connection = (HttpURLConnection)desiredUrl.openConnection();
            connection.connect();

            int status = connection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line)
                                .append("\n");
                    }
                    reader.close();

                    return stringBuilder.toString();
            }
        } catch (IOException ex) {

            Log.e("JSON Parsing Error", ex.getMessage());

        } finally {

            if (connection != null) {

                connection.disconnect();

            }

        }

        return "";
    }

    public static boolean isJsonValid(String input) {
        try {

            new JSONObject(input);

        } catch (JSONException ex) {

            try {

                new JSONArray(input);

            } catch (JSONException ex1) {

                Log.e("JSON Validity Error", ex.getMessage());

                return false;

            }
        }

        return true;
    }
}
