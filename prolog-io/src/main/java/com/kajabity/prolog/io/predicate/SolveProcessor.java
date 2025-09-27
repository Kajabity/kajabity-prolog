/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *  Created on 16-Feb-2004
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/action/SolveProcessor.java,v 1.3 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: SolveProcessor.java,v $
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

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;



import com.kajabity.prolog.core.engine.Solver;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.prolog.io.format.DisplayPrologFormat;
import com.kajabity.prolog.io.format.PrologFormat;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.utils.token.TokenException;

/**
 * A Term processor which expects to be handling a Goal to be solved - "?- goal,
 * goal, ... ."
 *
 * @author Simon
 */
public class SolveProcessor extends Processor
{
    //    private final static java.util.logging.Logger logger = Logger.getLogger(
    // SolveProcessor.class );

    /**
     * Construct the processor providing it's name. the Arity is always 1.
     *
     * @param name the Tuple name used by this processor (i.e. "?-").
     */
    public SolveProcessor( String name )
    {
        super( name, 1 );
    }


    /**
     * @throws IOException
     * @throws PrologException
     * @throws ParseException
     * @throws TokenException
     * @throws ParseException
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database,
     *      com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws TokenException,
            IOException, PrologException
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

        //	Track how many solutions we found.
        boolean solved = false;

        //  Keep solving while not "Solved" and there is another solution.
        while( !solved && solver.hasSolution() )
        {
            //  OK, we solved it once...
            solved = true;

            //  If this is a question we need to show variables and offer
            //  backtracking.
            Map<String, Variable> variables = solver.getTop().getVariables();

            //  Are there any variables?
            if( variables != null && !variables.isEmpty() )
            {
                PrologFormat format = new DisplayPrologFormat();
                //  Show the variables.
                Iterator<Variable> iVar = variables.values().iterator();
                while( iVar.hasNext() )
                {
                    Variable v = iVar.next();
                    if( !v.isAnonymous() )
                    {
                        db.getCurrentOutputStream()
                                .println(
                                        "\t" + v.getName() + " = "
                                                + format.format( v.getFinalInstantiation() ) );
                    }
                }

                if( wantMore( db.getCurrentOutputStream(), db
                        .getCurrentInputStream() ) )
                {
                    //  Try again..
                    solved = false;
                }
            }
            else
            {
                db.getCurrentOutputStream().println( ">> yes." );
            }
        }

        if( !solved )
        {
            db.getCurrentOutputStream().println( ">> no." );
        }
    }


    private String getLine( InputStream in ) throws IOException
    {
        StringBuffer linebuf = new StringBuffer();
        int ch;

        do
        {
            ch = in.read();
            if( ch == '\n' || ch == -1 )
            {
                break;
            }

            linebuf.append( (char) ch );
        } while( ch != '\n' && ch != -1 );

        String line = linebuf.toString().trim();

        return line;
    }

    private boolean wantMore( PrintStream out, InputStream in )
            throws IOException
    {
        out.println( "more? " );
        String line = getLine( in );
        //        logger.debug( "Read: [" + line + "]" );

        return line.trim().equals( ";" );
    }
}
