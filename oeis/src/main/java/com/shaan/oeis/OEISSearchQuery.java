package com.shaan.oeis;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class OEISSearchQuery {
    private RawDataProvider dataProvider;
    private int allResultsCount;
    private List<OEISSequence> results;
    public OEISSearchQuery(String query, RawDataProvider client) throws Exception
    {
        dataProvider = client;
        executeQuery(query);
    }
    private void executeQuery(String query) throws Exception
    {
        String requestUri = "http://www.oeis.org/search?fmt=text&q=" + query.trim();
        BufferedReader reader = dataProvider.getBufferedReader(requestUri);
        String line;
        OEISSequenceBuilder sequenceBuilder = new OEISSequenceBuilder();
        results = new ArrayList<OEISSequence>();
        while ((line = reader.readLine()) != null) {
            if (!line.startsWith("%"))
                interpretOther(line);
            else {
                sequenceBuilder.appendLine(line);
                if (sequenceBuilder.isResultReady()){
                    //Add finished sequence to results and reread line
                    results.add(sequenceBuilder.getResult());
                    sequenceBuilder = new OEISSequenceBuilder();
                    sequenceBuilder.appendLine(line);
                }
            }
        }
        sequenceBuilder.addSequenceData();
        results.add(sequenceBuilder.getResult());
        reader.close();
    }

    private void interpretOther(String line) throws TooManyResultsError, NoResultsError {
        if (line.isEmpty())
            return;
        if (line.startsWith("#"))
            return;
        if (line.startsWith("Showing"))
            readResultCount(line);
        if (line.startsWith("Too many results."))
            throw new TooManyResultsError();
        if (line.startsWith("No results."))
            throw new NoResultsError();
    }

    private void readResultCount(String line) {
        String parts[] = line.split(" ");
        allResultsCount = Integer.parseInt(parts[3]);
    }

    public int getAvaialableResults(){
        return results.size();
    }
    public int getAllResultsCount(){
        return allResultsCount;
    }

    public List<OEISSequence> getResults() {
        return results;
    }
}
