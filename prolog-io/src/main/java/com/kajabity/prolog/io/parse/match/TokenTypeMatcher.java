/*
 *******************************************************************************
 *  Copyright   :	(c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Core
 *
 *  Created on 	:	Jul 19, 2004
 *
 *  $Header$
 *******************************************************************************
 *  $Log$
 *******************************************************************************
 */
package com.kajabity.prolog.io.parse.match;

import com.kajabity.prolog.io.parse.PrologParser;


public class TokenTypeMatcher extends Matcher
{
    public boolean match( PrologParser parser, int param )
    {
        return parser.getTokeniser().getType() == param;
    }
}
