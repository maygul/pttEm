package com.maygul.order.interceptor;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;

public class JacksonCustomEscapeConfig extends CharacterEscapes {

    private final int[] _asciiEscapes;

    public JacksonCustomEscapeConfig() {
        _asciiEscapes = standardAsciiEscapesForJSON();
        // By default the ascii Escape table in jackson has " added as escape string
        // overwriting that here.
        _asciiEscapes['"'] = CharacterEscapes.ESCAPE_NONE;
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return _asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int i) {
        return null;
    }
}
