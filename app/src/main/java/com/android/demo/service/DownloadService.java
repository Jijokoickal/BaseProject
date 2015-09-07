package com.android.demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.android.demo.constants.Constants;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;


    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String urlToDownload = intent.getStringExtra(Constants.URL);
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra(Constants.RECEIVER);
        try {
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            // this will be useful so that you can show a typical 0-100% progress bar
            int fileLength = connection.getContentLength();

            // download the file
            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream("/sdcard/BarcodeScanner-debug.apk");

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                Bundle resultData = new Bundle();
                resultData.putInt(Constants.PROGRESS, (int) (total * 100 / fileLength));
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle resultData = new Bundle();
        resultData.putInt(Constants.PROGRESS, 100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }

    /**The below code need to be called from Activity
     *
     *
     * mProgressDialog.show();
     Intent intent = new Intent(this, DownloadService.class);
     intent.putExtra("url", "url of the file to download");
     intent.putExtra("receiver", new DownloadReceiver(new Handler(), mProgressDialog));
     startService(intent);
     */
}