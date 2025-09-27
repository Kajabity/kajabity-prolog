/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Utilities
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/Handler.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
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
