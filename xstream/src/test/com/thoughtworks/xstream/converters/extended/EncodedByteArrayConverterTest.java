/*
 * Copyright (C) 2004, 2005 Joe Walnes.
 * Copyright (C) 2006, 2007, 2018 XStream Committers.
 * All rights reserved.
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 *
 * Created on 06. March 2004 by Joe Walnes
 */
package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.acceptance.AbstractAcceptanceTest;
import com.thoughtworks.acceptance.objects.StandardObject;


public class EncodedByteArrayConverterTest extends AbstractAcceptanceTest {

    public void testMarshallsCharArrayAsSingleString() {
        final byte[] input = {0, 120, -124, 22, 33, 0, 5};
        final String expected = "<byte-array>AHiEFiEABQ==</byte-array>";

        assertBothWays(input, expected);
    }

    public void testUnmarshallsOldByteArraysThatHaveNotBeenEncoding() {
        // for backwards compatibility
        final String input = ""
            + "<byte-array>\n"
            + "  <byte>0</byte>\n"
            + "  <byte>120</byte>\n"
            + "  <byte>-124</byte>\n"
            + "  <byte>22</byte>\n"
            + "  <byte>33</byte>\n"
            + "  <byte>0</byte>\n"
            + "  <byte>5</byte>\n"
            + "</byte-array>";

        final byte[] expected = {0, 120, -124, 22, 33, 0, 5};
        assertByteArrayEquals(expected, (byte[])xstream.fromXML(input));
    }

    public void testUnmarshallsEmptyByteArrays() {
        final byte[] input = {};
        final String expected = "<byte-array></byte-array>";

        assertBothWays(input, expected);
    }

    public static class TestObject extends StandardObject {
        private static final long serialVersionUID = 200504L;
         byte[] data;
         boolean something;
    }

    public void testUnmarshallsEmptyByteArrayAsFieldOfAnotherObject() {
        // exposes a weird bug that was in the XML pull readers.
        final TestObject in = new TestObject();
        in.data = new byte[0];

        xstream.alias("TestObject", TestObject.class);
        final String expectedXml = ""
            + "<TestObject>\n"
            + "  <data></data>\n"
            + "  <something>false</something>\n"
            + "</TestObject>";
        assertBothWays(in, expectedXml);
    }

}
