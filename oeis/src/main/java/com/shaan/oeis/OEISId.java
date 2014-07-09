package com.shaan.oeis;

/**
 * Id entry of an OEISSequence
 */
public class OEISId extends OEISTextAttribute {
    public OEISId(String idStr)
    {
       super(OEISSequence.ID_NAME, idStr);
    }
}
