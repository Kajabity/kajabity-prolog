/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header$
 *******************************************************************************
 *  $Log$
 *******************************************************************************
 */

package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * A comparison predicate which tests if two expressions are identical - they
 * have the same structure and uninstantiated variables are the same ones.
 *
 * @author Simon J. Williams
 */
public class EqualSamePredicate extends GroundLiteral
{
    private final static Logger logger = Logger
                                               .getLogger( EqualSamePredicate.class );


    /**
     * Construct and initialise this predicate.
     *
     * @param database
     */
    public EqualSamePredicate( Database database )
    {
        super( database, "==", 2 );
    }


    /**
     * Compare the two arguments using a similar technique to the 'unify'
     * mechanism but without any matching of variables.
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Expression p1 = (Expression) args.get( 0 );
            Expression p2 = (Expression) args.get( 1 );

            logger.debug( "Comparing <<" + p1 + ">> and <<" + p2 + ">>" );

            ArrayList<Expression> leftTerms = new ArrayList<Expression>();
            leftTerms.add( p1 );

            List<Expression> rightTerms = new ArrayList<Expression>();
            rightTerms.add( p2 );

            do
            {
                //  Pop the two terms
                Expression left = (Expression) leftTerms.remove( 0 );
                left = left.getFinalInstantiation();

                Expression right = (Expression) rightTerms.remove( 0 );
                right = right.getFinalInstantiation();

                logger.debug( "\tcomparing <<" + left + ">> and <<" + right
                        + ">>" );

                if( left.isTerm() != right.isTerm() )
                {
                    //  Fail, one is a Term, the other isn't.
                    logger.debug( "\tfailed: one is a Term, the other isn't." );
                    return null;
                }
                if( left.isTerm() )
                {
                    if( ((Term) left).getTag() == ((Term) right).getTag() )
                    {
                        if( ((Term) left).getTag() == Term.TAG_TUPLE
                                || ((Term) left).getTag() == Term.TAG_LIST )
                        {
                            if( ((Tuple) left).getArity() == ((Tuple) right)
                                    .getArity() )
                            {
                                // Two tuples - append the functor and args
                                // of
                                // each.
                                leftTerms.add( ((Tuple) left).getFunctor() );
                                leftTerms.add( ((Tuple) left).getArgs() );
                                rightTerms.add( ((Tuple) right).getFunctor() );
                                rightTerms.add( ((Tuple) right).getArgs() );
                            }
                            else
                            {
                                //  fail
                                logger.debug( "\tfailed: Left and Right are different arity." );
                                return null;
                            }
                        }
                        else if( !left.equals( right ) )
                        {
                            //  fail
                            logger.debug( "\tfailed: Left and Right are different constants." );
                            return null;
                        }
                    }
                    else
                    {
                        //  fail
                        logger.debug( "\tfailed: Left and Right are different types." );
                        return null;
                    }
                }
                else
                {
                    //  Both TermList - append.
                    if( ((TermList) left).size() == ((TermList) right).size() )
                    {
                        leftTerms.addAll( ((TermList) left).getTerms() );
                        rightTerms.addAll( ((TermList) right).getTerms() );
                    }
                    else
                    {
                        //  fail
                        logger.debug( "\tfailed: Right and left are different lengths." );
                        return null;
                    }
                }
            } while( !leftTerms.isEmpty() );
        }

        //  If we get here, we have succeeded.
        return Collections.emptyList();
    }
}
