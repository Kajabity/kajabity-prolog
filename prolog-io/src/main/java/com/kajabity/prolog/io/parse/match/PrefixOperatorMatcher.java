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

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;




public class PrefixOperatorMatcher extends Matcher
{
    public boolean match( PrologParser parser, int param )
    {
        if( parser.getTokeniser().getType() == Tokeniser.ATOM )
        {
            parser.setOperator(parser.getDatabase().findOperator(
                    parser.getTokeniser().toString(),
                    Associativity.Position.PREFIX ));
            return parser.getOperator() != null;
        }
        return false;
    }
}
