package com.shaan.oeis.tests;

import android.test.AndroidTestCase;

import com.shaan.oeis.OEISAttribute;
import com.shaan.oeis.OEISAttributeBuilder;
import com.shaan.oeis.OEISId;

public class OEISAttributeTests extends AndroidTestCase {
    public void testBuilder()
    {
        OEISAttributeBuilder attributeBuilder = new OEISAttributeBuilder();
        assertTrue(attributeBuilder.fromString("%I A012345") instanceof OEISId);
    }
    public void testNameAttr() {
        OEISAttributeBuilder attributeBuilder = new OEISAttributeBuilder();
        OEISAttribute nameAttribute = attributeBuilder.fromString("%N A000108 Catalan numbers: a(n) = C(2n,n)/(n+1) = (2n)!/(n!(n+1)!).");
        assertEquals(nameAttribute.getName(), "Name");
        assertEquals(nameAttribute.getString(), "Catalan numbers: a(n) = C(2n,n)/(n+1) = (2n)!/(n!(n+1)!).");
    }
    public void testRefAttr() {
        OEISAttributeBuilder attributeBuilder = new OEISAttributeBuilder();
        OEISAttribute referenceAttribute = attributeBuilder.fromString("%D A010109 I. G. Enting, A, J. Guttmann and I. Jensen, Low-Temperature Series Expansions");
        assertEquals(referenceAttribute.getName(), "References");
        assertEquals(referenceAttribute.getString(), "I. G. Enting, A, J. Guttmann and I. Jensen, Low-Temperature Series Expansions");
    }
}
