/*
 * Created on 16-Jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.kajabity.prolog.builtin.predicate;

import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.arithmetic.ArithmeticExpression;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;

/**
 * @author Simon To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PutPredicate extends GroundLiteral
{
    public PutPredicate( Database database )
    {
        this( database, "put", 1 );
    }

    public PutPredicate( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 1;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal ) throws PrologException
    {
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();

            List<Expression> args = tuple.getArgs().getTerms();
            Expression p1 = args.get( 0 );

            ArithmeticExpression ae = new ArithmeticExpression( p1 );
            NumericConstant result = ae.eval( database );

            database.getCurrentOutputStream().print( (char) result.longValue() );

            return Collections.emptyList();
        }

        return null;
    }
}
