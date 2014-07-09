package com.shaan.oeis;

/**
 * Represents an entry of an OEISSequence
 */
public interface OEISAttribute {
    /**
     * Get a readable name/category for this entry
     */
    public String getName();

    /**
     * Get the content of this entry
     */
    public String getString();
}



