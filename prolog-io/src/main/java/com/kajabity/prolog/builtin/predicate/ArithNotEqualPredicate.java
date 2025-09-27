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

package com.kajabity.prolog.builtin.predicate;

import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.arithmetic.ArithmeticExpression;
import com.kajabity.prolog.core.arithmetic.PrologArithmeticException;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;

/**
 * @author Simon J. Williams
 */
public class ArithNotEqualPredicate extends GroundLiteral
{
    public ArithNotEqualPredicate( Database database )
    {
        this( database, "=\\=", 2 );
    }

    public ArithNotEqualPredicate( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 2;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal ) throws PrologArithmeticException
    {
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Expression p1 = args.get( 0 );
            Expression p2 = args.get( 1 );

            ArithmeticExpression ae = new ArithmeticExpression( p1 );
            NumericConstant left = ae.eval( database );

            ae = new ArithmeticExpression( p2 );
            NumericConstant right = ae.eval( database );

            if( left.isReal() || right.isReal() )
            {
                if( left.doubleValue() != right.doubleValue() )
                {
                    return Collections.emptyList();
                }
            }

            if( left.longValue() != right.longValue() )
            {
                return Collections.emptyList();
            }
        }

        return null;
    }
}
