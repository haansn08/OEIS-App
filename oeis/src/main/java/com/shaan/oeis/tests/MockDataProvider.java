package com.shaan.oeis.tests;

import android.content.Context;

import com.shaan.oeis.RawDataProvider;
import com.shaan.oeis.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MockDataProvider implements RawDataProvider {
    String lastRequest;
    Context context;
    public MockDataProvider(Context context)
    {
        this.context = context;
    }
    @Override
    public BufferedReader getBufferedReader(String location) {
        lastRequest = location.toString();
        return new BufferedReader(
                new InputStreamReader(context.getResources().openRawResource(R.raw.search))
        );
    }
    public String getLastRequest(){
        return lastRequest;
    }
}
