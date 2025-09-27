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
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;



public class PopListAction extends Action
{
    public void act( PrologParser parser ) throws PrologException,
            PrologOperatorException, ParseException
    {
        parser.popTill( "[" );
    }
}
