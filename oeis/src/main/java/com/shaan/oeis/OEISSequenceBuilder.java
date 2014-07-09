package com.shaan.oeis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OEISSequenceBuilder {
    OEISSequence sequence;
    boolean isReady;
    boolean hasId;
    ArrayList<Long> sequence_members = new ArrayList<Long>();
    static OEISAttributeBuilder attributeBuilder = new OEISAttributeBuilder();
    public OEISSequenceBuilder()
    {
        sequence = new OEISSequence();
    }
    public void appendLine(String line)
    {
        if (isReady)
            return;
        if (line.startsWith("%S") || line.startsWith("%T") || line.startsWith("%U")) {
            sequence_members.addAll(getMembers(line));
        }
        if (line.startsWith("%V")) {
            sequence_members.clear();
        }
        if (line.startsWith("%V") || line.startsWith("%W") || line.startsWith("%X")) {
            sequence_members.addAll(getMembers(line));
        }

        OEISAttribute attribute = attributeBuilder.fromString(line);
        if (attribute instanceof OEISId)
            if(hasId) {
                isReady = true;
                addSequenceData();
                return;
            }
            else
                hasId = true;
        if (attribute != null)
            sequence.attributes.add(attribute);
    }

    public void addSequenceData() {
        long sequence_members_array[] = new long[sequence_members.size()];
        for (int i = 0; i < sequence_members.size(); i++)
            sequence_members_array[i] = sequence_members.get(i);

        sequence.attributes.add(
                1,
                new OEISIntegerArrayAttribute(sequence_members_array)
        );
    }

    private Collection getMembers(String line) {
        List<Long> integerList = new ArrayList<Long>();
        for (String s : line.split(" ")[2].split(",")) {
            try {
                integerList.add(Long.parseLong(s));
            }
            catch (Exception e)
            {}
        }
        return integerList;
    }

    public boolean isResultReady()
    {
        return isReady;
    }
    public OEISSequence getResult()
    {
        return sequence;
    }
}
