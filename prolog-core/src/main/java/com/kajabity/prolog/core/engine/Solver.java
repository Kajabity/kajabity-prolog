/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/solver/Solver.java,v 1.13 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: Solver.java,v $
 *  Revision 1.13  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.12  2004/02/17 08:35:44  simon
 *  Refactoring and adding Processor classes.
 *
 *  Revision 1.11  2004/01/15 07:10:49  simon
 *  refactoring and arithmetic implementation.
 *
 *  Revision 1.10  2003/11/11 04:49:16  simon
 *  Starting Visual front end, adding logging.
 *
 *  Revision 1.9  2003/07/07 03:39:20  simon
 *  List handling improved.  First built-ins working.  Cut works!
 *
 *  Revision 1.8  2003/07/03 03:47:00  simon
 *  Fixes (not complete) to List handling.
 *
 *  Revision 1.7  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.engine;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.environment.Clause;
import com.kajabity.prolog.core.environment.IConsortIterator;
import com.kajabity.prolog.core.environment.PrologContext;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.Relation;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;

/**
 * DOCUMENT ME!
 *
 * @author Simon
 */
public class Solver
{
    private final static Logger logger = Logger.getLogger( Solver.class );

    private PrologContext            database;

    private Goal                top;

    private Goal                lastGoal;


    /**
     * Construct a solver to solve the goal given in 'expr' using assertions in
     * the 'database'.
     *
     * @param database a database of assertions to test the goal against.
     * @param terms a list of terms to be solved.
     */
    public Solver( PrologContext database, Expression terms )
    {
        this.database = database;

        top = new Goal( null );
        Map<String, Variable> variables = new HashMap<String, Variable>();
        top.setChildren( terms.makeCopy( variables ) );
        top.setVariables( variables );
        lastGoal = (Goal) top.depthNext();
    }


    /**
     * Find the next solution.
     *
     * @return @throws PrologException
     * @throws PrologException
     * @throws IOException
     * @throws TokenException
     * @throws ParseException
     */
    public boolean hasSolution() throws TokenException, IOException,
            PrologException
    {
        //  Start from where we left off.
        Goal goal = lastGoal;

        boolean success = true;

        //  Repeat until there are no more goals to satisfy - success.
        while( goal != null )
        {
            logger.debug( "Solver: goal " + goal.getTerm() );

            Term term = goal.getTerm();
            List<Variable> substitutions;

            if( term == null )
            {
                //	Empty goal - we have reached the root in backtracking =>
                // FAIL.
                success = false;
                break;
            }

            goal.reset();

            // goal has a term
            IConsortIterator consortIterator = goal.getConsortIterator();

            if( consortIterator == null )
            {
                //	Always start the Goal afresh.
                Relation relation = database.findRelation( term );

                if( relation == null )
                {
                    throw new NoRelationException( "No relation for term "
                            + term + "(" + term.getKey() + ")" );
                }

                consortIterator = relation.getConsortIterator( goal );
                goal.setConsortIterator( consortIterator );
            }//end if consortIterator = null

            substitutions = consortIterator.hasNext();

            if( substitutions == null )
            {
                // Backtrack.
                do
                {
                    goal = (Goal) goal.depthPrev();
                    lastGoal = null;

                    if( goal == null )
                    {
                        success = false;
                    }
                    else
                    {
                        goal.reset();
                    }
                } while( goal != null && goal.isCut() );
            }
            else
            {
                //  Add the substitutions to the Goal - so they can
                // be undone later.
                goal.setSubstitutions( substitutions );

                Clause clause = consortIterator.getClause();
                if( clause.getTail() != null )
                {
                    goal.setChildren( clause.getTail().makeCopy(
                            goal.getVariables() ) );
                }

                //  Go on to the next goal.
                lastGoal = goal;
                goal = (Goal) goal.depthNext();
            }
        }

        return success;
    }


    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public Goal getTop()
    {
        return top;
    }


    public class NoRelationException extends PrologException
    {

        /**
         *
         */
        private static final long serialVersionUID = 7176036903579308954L;


        /**
         *
         */
        public NoRelationException()
        {
            super();
            // TODO Auto-generated constructor stub
        }


        /**
         * @param message
         */
        public NoRelationException( String message )
        {
            super( message );
            // TODO Auto-generated constructor stub
        }


        /**
         * @param message
         * @param cause
         */
        public NoRelationException( String message, Throwable cause )
        {
            super( message, cause );
            // TODO Auto-generated constructor stub
        }


        /**
         * @param cause
         */
        public NoRelationException( Throwable cause )
        {
            super( cause );
            // TODO Auto-generated constructor stub
        }
    }
}
