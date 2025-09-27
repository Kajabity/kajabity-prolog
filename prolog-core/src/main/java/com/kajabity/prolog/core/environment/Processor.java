/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   Mar 24, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/Processor.java,v 1.2 2004/07/01 05:31:52 simon Exp $
 *******************************************************************************
 *  $Log: Processor.java,v $
 *  Revision 1.2  2004/07/01 05:31:52  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.environment;

import java.io.IOException;


import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermKey;
import com.kajabity.utils.token.TokenException;


/**
 *
 * @author simon
 *
 */
public abstract class Processor
    extends TermKey
{
    public Processor( String name, int arity )
    {
        super( name, arity );
    }

    public abstract void execute( Database db, Term term ) throws PrologException, IOException, TokenException;
}
