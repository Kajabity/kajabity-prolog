/*
 * Copyright (c) 2003-2025 Simon J. Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */


package com.kajabity.utils.token;

/**
 * A token read from a TokenSource.
 */
public class Token {
    private int type = 0;
    private int subType = 0;

    private final StringBuffer buf = new StringBuffer();

    private char quote = (char) 0;
    private int line = 0;
    private int offset = 0;

    public Token() {
    }


    public void reset() {
        type = 0;
        subType = 0;
        quote = (char) 0;
        buf.setLength(0);
        line = 0;
        offset = 0;
    }


    public String toString() {
        return buf.toString();
    }


    public int getLine() {
        return line;
    }


    public void setLine(int line) {
        this.line = line;
    }


    public int getOffset() {
        return offset;
    }


    public void setOffset(int offset) {
        this.offset = offset;
    }


    public int getType() {
        return type;
    }


    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the quote.
     *
     * @return char
     */
    public char getQuote() {
        return quote;
    }

    /**
     * Sets the quote.
     *
     * @param quote The quote to set
     */
    public void setQuote(char quote) {
        this.quote = quote;
    }

    /**
     * @return
     */
    public int getSubType() {
        return subType;
    }

    /**
     * @param i
     */
    public void setSubType(int i) {
        subType = i;
    }


    /**
     * @param c
     */
    public void append(char c) {
        buf.append(c);
    }

}
