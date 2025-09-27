/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * 	Created on 17-Feb-04
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/action/FactProcess.java,v 1.2 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: FactProcess.java,v $
 *  Revision 1.2  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.1  2004/02/24 04:49:39  simon
 *  Don't list variables in 'perform' - needs to be a generic 'assert' and 'solve' action
 *  Move Processor to environment package.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.io.predicate;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Term;


/**
 * Prolog command processor which expects a "fact" and asserts it as such into the database.
 *
 * @author Simon
 */
public class FactProcessor extends Processor
{
    /**
     * Construct a 'Fact Processor' - name and arity are irrelevant.
     * @param name the processor name.
     */
    public FactProcessor( String name )
    {
        super( name, 0 );
    }

    /**
     * Executes the provided term by adding it to the database as an assertion.
     * Assumption is that the Fact processor name is empty so the whole term is asserted.
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        //	Assert a fact - it has no tail.
        db.assertz( term, null );
    }
}
