package com.shaan.oeis;

public class OEISAttributeBuilder
{
    public OEISAttribute fromString(String s)
    {
        String parts[] = s.split(" ",3);
        String lineIdentification = parts[0];
        if (lineIdentification.equals("%I"))
            return new OEISId(parts[1]);
        if (lineIdentification.equals("%N"))
            return new OEISTextAttribute(OEISSequence.NAME_NAME, parts[2]);
        if (lineIdentification.equals("%D"))
            return new OEISTextAttribute(OEISSequence.REFERENCE_NAME, parts[2]);
        if (lineIdentification.equals("%H"))
            return new OEISTextAttribute(OEISSequence.LINKS_NAME, parts[2]);
        if (lineIdentification.equals("%F"))
            return new OEISTextAttribute(OEISSequence.FORUMLA_NAME, parts[2]);
        if (lineIdentification.equals("%Y"))
            return new OEISTextAttribute(OEISSequence.CROSSREF_NAME, parts[2]);
        if (lineIdentification.equals("%A"))
            return new OEISTextAttribute(OEISSequence.AUTHOR_NAME, parts[2]);
        if (lineIdentification.equals("%O"))
            return new OEISTextAttribute(OEISSequence.OFFSET_NAME, parts[2]);
        if (lineIdentification.equals("%p"))
            return new OEISTextAttribute(OEISSequence.MAPLE_NAME, parts[2]);
        if (lineIdentification.equals("%t"))
            return new OEISTextAttribute(OEISSequence.MATHEMATICA_NAME, parts[2]);
        if (lineIdentification.equals("%o"))
            return new OEISTextAttribute(OEISSequence.OTHERPROGRAM_NAME, parts[2]);
        if (lineIdentification.equals("%E"))
            return new OEISTextAttribute(OEISSequence.ERRORS_NAME, parts[2]);
        if (lineIdentification.equals("%e"))
            return new OEISTextAttribute(OEISSequence.EXAMPLES_NAME, parts[2]);
        if (lineIdentification.equals("%K"))
            return new OEISTextAttribute(OEISSequence.KEYWORDS_NAME, parts[2]);
        if (lineIdentification.equals("%C"))
            return new OEISTextAttribute(OEISSequence.COMMENTS_NAME, parts[2]);
        return null;
    }
}
