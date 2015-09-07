package com.android.demo.receiver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.android.demo.constants.Constants;
import com.android.demo.service.DownloadService;

public class DownloadReceiver extends ResultReceiver {

    private ProgressDialog mProgressDialog;

    public DownloadReceiver(final Handler handler, final ProgressDialog progressDialog) {
        super(handler);
        mProgressDialog = progressDialog;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == DownloadService.UPDATE_PROGRESS) {
            int progress = resultData.getInt(Constants.PROGRESS);
            mProgressDialog.setProgress(progress);
            if (progress == 100) {
                mProgressDialog.dismiss();
            }
        }
    }
}