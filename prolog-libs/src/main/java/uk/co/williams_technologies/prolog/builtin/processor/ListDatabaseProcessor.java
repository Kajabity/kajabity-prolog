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
package uk.co.williams_technologies.prolog.builtin.processor;

import java.io.PrintStream;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.Relation;
import com.kajabity.prolog.core.environment.operator.Operator;
import com.kajabity.prolog.core.expression.Term;

/**
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ListDatabaseProcessor extends Processor
{
    /**
     * Construct this processor with a name.
     *
     * @param name
     *            the name this processor will handle.
     */
    public ListDatabaseProcessor( String name )
    {
        super( name, 0 );
    }

    /**
     * List the contents of the Database.
     *
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database,
     *      com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        PrintStream out = db.getCurrentOutputStream();

        out.println( "Database" );
        out.println( "========" );

        out.println( "Relations" );

        for( Relation r : db.getRelations().values() )
        {
            if( r.isGroundLiteral() )
            {
                out.println( "\t" + r.getKey() + " (Ground Literal)" );
            }
            else
            {
                out.println( "\t" + r.getKey() );
            }
        }

        out.println( "Operators" );

        for( Operator op : db.getOperators().values() )
        {
            out.println( "\t" + op );
        }

        out.println( "Arithmetic Functions" );

        for( Function func : db.getFunctions().values() )
        {
            out.println( "\t" + func.getKey() );
        }

        out.println( "Command Processors" );

        for( Processor proc : db.getProcessors().values() )
        {
            out.println( "\t" + proc.getKey() );
        }
    }

}
