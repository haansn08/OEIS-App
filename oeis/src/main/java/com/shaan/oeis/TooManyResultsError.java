package com.shaan.oeis;

public class TooManyResultsError extends Exception {
    TooManyResultsError()
    {
        super("Too many results. Please narrow search query.");
    }
}
