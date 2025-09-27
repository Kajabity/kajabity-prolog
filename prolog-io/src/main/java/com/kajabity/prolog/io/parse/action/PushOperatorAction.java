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
import com.kajabity.prolog.io.parse.PrologParser;



public class PushOperatorAction extends Action
{
    public void act( PrologParser parser ) throws PrologException
    {
        parser.pushOperator( parser.getOperator(), false );
    }
}
