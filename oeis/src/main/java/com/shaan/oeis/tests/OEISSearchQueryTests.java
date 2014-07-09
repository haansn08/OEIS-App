package com.shaan.oeis.tests;

import android.test.AndroidTestCase;

import com.shaan.oeis.OEISAttribute;
import com.shaan.oeis.OEISSearchQuery;
import com.shaan.oeis.OEISSequence;
import java.util.List;

public class OEISSearchQueryTests extends AndroidTestCase {
    public void testResultCount() throws Exception
    {
        MockDataProvider mockHttpClient = new MockDataProvider(getContext());
        OEISSearchQuery searchQuery = new OEISSearchQuery("FooBar", mockHttpClient);
        assertEquals(searchQuery.getAvaialableResults(), 10);
        assertEquals(searchQuery.getAllResultsCount(), 6886);
        assertEquals(mockHttpClient.getLastRequest(), "http://www.oeis.org/search?q=FooBar&fmt=text");
    }

    public void testBuildSequences() throws Exception
    {
        MockDataProvider mockHttpClient = new MockDataProvider(getContext());
        OEISSearchQuery searchQuery = new OEISSearchQuery("BarBar", mockHttpClient);
        List<OEISSequence> searchResults = searchQuery.getResults();
        assertEquals(10, searchResults.size());
        assertEquals(((OEISAttribute) searchResults.get(0).attributes.get(0)).getString(), "A000045");
    }
}
