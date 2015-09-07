package com.android.demo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;

import com.android.demo.log.AppLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context mContext;
    private PowerManager.WakeLock mWakeLock;
    private ProgressDialog mProgressDialog;
    private boolean mProgressVisible;
    private File mTargetFile;


    public DownloadTask(final Context context, final boolean isProgressVisible, final File targetFile) {
        mContext = context;
        mProgressVisible = isProgressVisible;
        this.mTargetFile = targetFile;
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            if(null!=mTargetFile){
                output = new FileOutputStream(mTargetFile);
            }else{
                output = new FileOutputStream("/sdcard/file_name.extension");
            }


            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) { // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException ignored) {
                AppLog.logString(ignored.getMessage());
            }
        }

        if (connection != null) {
            connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        if (mProgressVisible) {
            mProgressDialog = new ProgressDialog(mContext);
            mProgressDialog.show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        if (mProgressVisible) {
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mWakeLock.release();
        if (mProgressVisible) {
            mProgressDialog.dismiss();
        }
        if (result != null)
            Toast.makeText(mContext, "Download error: " + result, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(mContext, "File downloaded", Toast.LENGTH_SHORT).show();
    }
}