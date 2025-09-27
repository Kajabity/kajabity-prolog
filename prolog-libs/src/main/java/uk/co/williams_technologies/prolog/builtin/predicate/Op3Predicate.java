/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * Created on 26-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/predicate/Op3Predicate.java,v 1.2 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: Op3Predicate.java,v $
 *  Revision 1.2  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.1  2004/01/25 05:48:12  simon
 *  Moved into predicate package and other updates.
 *
 *  Revision 1.6  2004/01/15 07:07:54  simon
 *  Adding support for arithmetic.
 *
 *  Revision 1.5  2003/11/22 04:12:26  simon
 *  Added basic arithmetic functionality - still need to implement functions, operators and predicats
 *
 *  Revision 1.4  2003/11/20 04:45:58  simon
 *  Implementing PrologInput/OutputStreams
 *  New "write( Term )" builtin.
 *
 *  Revision 1.3  2003/07/03 03:47:00  simon
 *  Fixes (not complete) to List handling.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.kajabity.prolog.core.arithmetic.ArithmeticExpression;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.SimpleOperator;
import com.kajabity.prolog.core.expression.Atom;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * Define an operator.
 *
 * @author Simon J. Williams
 */
public class Op3Predicate extends GroundLiteral
{
    private final static Logger logger       = Logger
                                                     .getLogger( Op3Predicate.class );

    private final static String KEY          = "Associativity";

    private final static int    MAX_PRIORITY = 1200;

    private final static int    MIN_PRIORITY = 1;


    /**
     * Constructor for Op3Predicate.
     *
     * @param database
     */
    public Op3Predicate( Database database )
    {
        super( database, "op", 3 );

        Atom atom;

        atom = Atom.find( "fx" );
        atom.setProperty( KEY, Associativity.fx );

        atom = Atom.find( "fy" );
        atom.setProperty( KEY, Associativity.fy );

        atom = Atom.find( "xfx" );
        atom.setProperty( KEY, Associativity.xfx );

        atom = Atom.find( "xfy" );
        atom.setProperty( KEY, Associativity.xfy );

        atom = Atom.find( "yfx" );
        atom.setProperty( KEY, Associativity.yfx );

        atom = Atom.find( "xf" );
        atom
                .setProperty(
                        KEY,
                        com.kajabity.prolog.core.environment.Associativity.xf );

        atom = Atom.find( "yf" );
        atom.setProperty( KEY, Associativity.yf );
    }


    /**
     * Op/3 parameters: -
     * <li>Priority (IntegerNumber)
     * <li>Associativity (Atom)
     * <li>Name (atom)
     *
     * @param goal
     * @return List of substitutions - or null for a fail. In this op it will be
     *         an empty list if the operator is added ok or null if anything is
     *         wrong.
     * @throws PrologException
     * @see GroundLiteral#execute(Goal)
     */
    protected List<Variable> execute( Goal goal ) throws PrologException
    {
        //  the goal should contain a Tuple (op/3).
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            if( tuple.getArity() == 3 )
            {
                List<Expression> args = tuple.getArgs().getTerms();

                Expression p1 = (Expression) args.get( 0 );
                Expression p2 = (Expression) args.get( 1 );
                Expression p3 = (Expression) args.get( 2 );

                ArithmeticExpression ae = new ArithmeticExpression( p1 );
                NumericConstant result = ae.eval( database );

                int priority = (int) result.longValue();
                if( priority < MIN_PRIORITY || priority > MAX_PRIORITY )
                {
                    priority = 0;
                }

                Associativity ass = null;
                if( p2 instanceof Atom )
                {
                    ass = (Associativity) ((Atom) p2).getProperty( KEY );
                }

                if( ass == null )
                {
                    throw new PrologException(
                            getKey()
                                    + ": Second parameter must specify associativity - "
                                    + goal.getTerm() );
                }

                if( p3 instanceof Atom )
                {
                    Atom opName = (Atom) p3;
                    SimpleOperator op = new SimpleOperator( opName.getName(),
                            ass, priority );
                    database.add( op );

                    logger.debug( getKey() + ": Added operator " + op );

                    return Collections.emptyList();
                }

                throw new PrologException( getKey()
                        + ": Third parameter must be the operator name - "
                        + goal.getTerm() );
            }

            throw new PrologException( getKey()
                    + ": Goal should have 3 parameters - " + goal.getTerm() );
        }

        throw new PrologException( getKey() + ": Goal should be a Tuple - "
                + goal.getTerm() );
    }
}
