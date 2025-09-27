/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *  Created on 16-Feb-2004
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/action/QuitProcessor.java,v 1.3 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: QuitProcessor.java,v $
 *  Revision 1.3  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.2  2004/02/24 04:49:39  simon
 *  Don't list variables in 'perform' - needs to be a generic 'assert' and 'solve' action
 *  Move Processor to environment package.
 *
 *  Revision 1.1  2004/02/17 08:35:43  simon
 *  Refactoring and adding Processor classes.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.io.predicate;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Term;


/**
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class QuitProcessor extends Processor
{

    /**
     * @param functor
     * @param arity
     */
    public QuitProcessor( String name )
    {
        super( name, 0 );
    }


    /**
     * Inidicate to the Prolog engine that the session is finished.
     *
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database,
     *      com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        db.setTerminated( true );
    }

}
