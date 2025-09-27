/*
 * ******************************************************************************
 * Copyright : (c)2004 Williams Technologies Limited Project : Prolog_Core
 * Created on : Jul 20, 2004 $Header$
 * ******************************************************************************
 * $Log$
 * ******************************************************************************
 */
package com.kajabity.prolog.io.parse.action;

import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.io.parse.PrologParser;



public class PushStringAction extends Action
{
    public void act( PrologParser parser ) throws PrologException
    {
        byte[] bytes = parser.getTokeniser().toString().getBytes();
        Term term = Expressions.NIL;
        for( int b = bytes.length - 1; b >= 0; b-- )
        {
            term = new Tuple( Expressions.NIL, new TermList( new NumericConstant(
                    bytes[ b ] ), term ) );
        }
        parser.pushValue( term );
    }
}
