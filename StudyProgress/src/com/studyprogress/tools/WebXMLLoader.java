package com.studyprogress.tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.studyprogress.R;
import com.studyprogress.ListViewActivity;
import com.studyprogress.StudyProgressActivity;
import com.studyprogress.properties.GlobalProperties;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class WebXMLLoader extends AsyncTask<String, Void, Void> {
    private DefaultHttpClient httpClient;
    private XMLParser parser;
    private StudyProgressActivity activity;
    private ProgressDialog progressDialog;

    public WebXMLLoader(StudyProgressActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        httpClient = new DefaultHttpClient();
        parser = XMLParser.getInstance(null);
        progressDialog = ProgressDialog.show(activity, activity.getString(R.string.please_wait),
                activity.getString(R.string.download_in_progress), true);
        progressDialog.setCancelable(true);

    }

    @Override
    protected Void doInBackground(String... params) {
        HttpGet httpPostRequest = new HttpGet(GlobalProperties.WWW_DL_ADDRESS + params[0] +
                                                                    GlobalProperties.XML_EXTENSION);

        HttpResponse httpResponse;

        try {
            httpResponse = httpClient.execute(httpPostRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            BufferedHttpEntity buffer = new BufferedHttpEntity(httpEntity);
            InputStream is = buffer.getContent();
            parser.setInputStream(is);
            Log.d("t_loader", "Set inputstream to " + is.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        progressDialog.dismiss();

        super.onPostExecute(result);
        activity.initComponents();

    }

}
