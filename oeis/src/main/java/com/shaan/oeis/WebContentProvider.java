package com.shaan.oeis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class WebContentProvider implements RawDataProvider {
    Context context;

    WebContentProvider(Context context)
    { this.context = context; }

    @Override
    public BufferedReader getBufferedReader(String location) throws Exception{
        if (!isConnectionAvailable())
            throw new UnsupportedOperationException("Not connected.");
        return new BufferedReader(
                new InputStreamReader(getResonponseStream(location))
        );
    }

    private InputStream getResonponseStream(String location) throws Exception {
        URLConnection urlConnection = (new URL(location)).openConnection();
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

    private boolean isConnectionAvailable()
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null) && (networkInfo.isConnected());
    }
}
