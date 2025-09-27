/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/parse/Action.java,v 1.2 2004/07/01 05:31:53 simon Exp $
 *******************************************************************************
 *  $Log: Action.java,v $
 *  Revision 1.2  2004/07/01 05:31:53  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.3  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.io.parse.action;

import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;



/**
 * This is an abstract base class for 'Actions' to be performed when a parser
 * network matches a token in the input stream.
 *
 * @author Simon
 */
public abstract class Action
{
    public abstract void act( PrologParser parser ) throws PrologException,
            PrologOperatorException, ParseException;
}
