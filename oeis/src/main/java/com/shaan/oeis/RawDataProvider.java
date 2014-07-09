package com.shaan.oeis;

import java.io.BufferedReader;

public interface RawDataProvider {
    public BufferedReader getBufferedReader(String location) throws Exception;
}
