package com.shaan.oeis;

import android.view.View;

public class OEISTextAttribute implements OEISAttribute{
    private String data;
    private String name;
    public OEISTextAttribute( String name, String data)
    {
        this.data = data;
        this.name = name;
    }

    @Override
    public String getString() {
        return data;
    }

    @Override
    public String getName() {
        return name;
    }
}
