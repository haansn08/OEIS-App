package com.shaan.oeis;

import java.util.ArrayList;

/**
 * An Sequence entry of the OEIS Database
 */
public class OEISSequence {
    public static final String SEQUENCE_NAME = "Sequence";
    public static final String ID_NAME = "Sequence ID";
    public static final String NAME_NAME = "Name";
    public static final String REFERENCE_NAME = "References";
    public static final String LINKS_NAME = "Links";
    public static final String FORUMLA_NAME = "Formula";
    public static final String CROSSREF_NAME = "Cross References";
    public static final String AUTHOR_NAME = "Author";
    public static final String OFFSET_NAME = "Offset";
    public static final String MAPLE_NAME = "Maple Program";
    public static final String MATHEMATICA_NAME = "Mathematica Program";
    public static final String OTHERPROGRAM_NAME = "Other Computer Program";
    public static final String ERRORS_NAME = "Extensions & Errors";
    public static final String EXAMPLES_NAME = "Examples";
    public static final String KEYWORDS_NAME = "Keywords";
    public static final String COMMENTS_NAME = "Comments";


    public ArrayList<OEISAttribute> attributes;
    public OEISSequence()
    {
        attributes = new ArrayList<OEISAttribute>();
    }

    /**
     * Get the content of the first item with the given name
     * @param name
     * @return "" when name was not found
     */
    public String getStringByName (String name)
    {
        for (OEISAttribute attribute : attributes) {
            if(attribute.getName().equals(name))
                return attribute.getString();
        }
        return "";
    }
}
