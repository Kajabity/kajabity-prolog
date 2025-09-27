/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *  Created on 16-Feb-2004
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/InitialiseProcessorBuiltins.java,v 1.3 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: InitialiseProcessorBuiltins.java,v $
 *  Revision 1.3  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.2  2004/02/24 04:52:20  simon
 *  add 'fact' action.
 *
 *  Revision 1.1  2004/02/17 08:35:44  simon
 *  Refactoring and adding Processor classes.
 *
 *******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;

import uk.co.williams_technologies.prolog.builtin.processor.AssertProcessor;
import uk.co.williams_technologies.prolog.builtin.processor.FactProcess;
import uk.co.williams_technologies.prolog.builtin.processor.ImmediateProcessor;
import uk.co.williams_technologies.prolog.builtin.processor.ListDatabaseProcessor;
import uk.co.williams_technologies.prolog.builtin.processor.QuitProcessor;
import uk.co.williams_technologies.prolog.builtin.processor.SolveProcessor;

/**
 * @author Simon
 */
public class InitialiseProcessorBuiltins
{
    public static void initialise( Database database ) throws PrologException
    {
    	// 	Add explicit assertion.
		database.add( new AssertProcessor( ":-" ) );

    	//	Add directive (immediate).
		database.add( new ImmediateProcessor( ":-" ) );

    	//	Add question (solve).
		database.add( new SolveProcessor( "?-" ) );

    	//	Add quit.
		database.add( new QuitProcessor( "quit" ) );

        //  Add list_db.
        database.add( new ListDatabaseProcessor( "list_db" ) );

        //  Add the default processor...
        database.setDefaultProcessor( new FactProcess( "" ) );
    }
}
