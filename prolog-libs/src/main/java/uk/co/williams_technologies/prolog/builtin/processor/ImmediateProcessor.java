/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *  Created on 16-Feb-2004
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/action/ImmediateProcessor.java,v 1.3 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: ImmediateProcessor.java,v $
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

import java.io.IOException;


import com.kajabity.prolog.core.engine.Solver;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.utils.token.TokenException;

/**
 * A command processor to solve a solution once only - with no output.  IT is used for
 * running immediate goals - e.g. when consulting a file.
 * @author Simon J. Williams
 */
public class ImmediateProcessor extends Processor
{

    /**
     * @param name
     */
    public ImmediateProcessor( String name )
    {
        super( name, 1 );
    }


    /**
     * @throws PrologException
     * @throws ParseException
     * @throws IOException
     * @throws TokenException
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database, com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws TokenException, IOException, PrologException
    {
        //  Get the list of goals from the given term.
        if( term.getTag() != Term.TAG_TUPLE )
        {
            throw new PrologException( "Solve: Expected a tuple: " + term );
        }

        TermList args = ((Tuple) term).getArgs();
        if( args.size() != 1 )
        {
            throw new PrologException(
                    "Solve: Expected a single arg (Term or TermList) : " + term );
        }

        Expression terms = args.getHead();
        Solver solver = new Solver( db, terms );

        //  Get the solution - ignore it!
        solver.hasSolution();
    }

}
