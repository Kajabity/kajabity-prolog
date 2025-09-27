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

import java.io.IOException;

/**
 * Classes extending the Handler class are used to fetch an individual token
 * from a TokenSource input stream. The tokeniser identifies the expecte type of
 * token from the first character and uses this to select a Handler instance to
 * read it.
 *
 * @author Simon J. Williams
 */

public abstract class Handler
{
    protected static final int EOF = -1;

    protected static final int NL  = '\n';


    /**
     * Called by a tokeniser to handle the reading of a single token.
     *
     * @param in the stream providing the characters for the token.
     * @param token used to hold token read from the input.
     * @throws TokenException if an incorrectly formed token is read.
     * @throws IOException if anything goes wrong with the input stream.
     */
    public abstract void handle( TokenSource in, Token token )
            throws TokenException, IOException;
}
