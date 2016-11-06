package gclprojects.chunlin.cheese;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JapaneseVoice extends AsyncTask<FileOutputStream, Void, BackgroundWork.Status> {
    private Context mContext;
    private String mTextToSpeak;
    private ProgressDialog mProgress;

    public JapaneseVoice (Context context, String textToSpeak, ProgressDialog progress) {
        mContext = context;
        mTextToSpeak = textToSpeak;
        mProgress = progress;
    }

    protected BackgroundWork.Status doInBackground(FileOutputStream... fileOutputStream) {
        try {
            String url = "https://api.voicetext.jp/v1/tts";
            Log.d("Japanese Voice", "The URL is " + url);

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            try {
                String postData = "text=" + mTextToSpeak + "&speaker=hikari&emotion=happiness";
                String userCredentials = mContext.getString(R.string.JAPANESE_VOICE_TEXT_API);
                String basicAuth = "Basic " + new String(Base64.encode(userCredentials.getBytes(), Base64.DEFAULT));
                conn.setRequestProperty("Authorization", basicAuth);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", "" + postData.getBytes().length);
                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());

                int responseCode = conn.getResponseCode();
                Log.d("Japanese Voice API", "The responseCode is " + responseCode);

                InputStream inputStream = conn.getInputStream();

                byte[] buffer = new byte[1024];
                int len1;
                while ((len1 = inputStream.read(buffer)) != -1) {
                    fileOutputStream[0].write(buffer, 0, len1);
                }
                fileOutputStream[0].close();
                inputStream.close();

                return BackgroundWork.Status.SUCCESS;

            } catch (Exception ex) {
                Log.d("Japanese Voice API", "The IO exception is " + ex.getMessage());
            } finally {
                conn.disconnect();
            }
        } catch (Exception e) {
            Log.d("Japanese Voice API", "The unknown exception is " + e.getMessage());
        }

        return BackgroundWork.Status.FAILURE;
    }

    protected void onPostExecute(BackgroundWork.Status i) {
        mProgress.dismiss();

        if (i != BackgroundWork.Status.FAILURE) {
            MediaPlayer mp = MediaPlayer.create(mContext, Uri.fromFile(mContext.getFileStreamPath("myvoice.mp3")));
            mp.setLooping(false);
            mp.start();
        }
    }
}