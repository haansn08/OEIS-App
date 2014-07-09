package com.shaan.oeis;

public class OEISIntegerArrayAttribute implements OEISAttribute {
    long sequence_array[];

    OEISIntegerArrayAttribute(long sequence[])
    {
        sequence_array = sequence;
    }

    @Override
    public String getName() {
        return OEISSequence.SEQUENCE_NAME;
    }

    @Override
    public String getString() {
        String result = "";
        for (int i = 0; i < sequence_array.length; i++) {
            result += sequence_array[i];
            if (i != sequence_array.length - 1)
                result += ",";
        }
        return result;
    }
}
