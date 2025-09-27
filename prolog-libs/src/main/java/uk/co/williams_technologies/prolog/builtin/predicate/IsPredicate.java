/*
 * Created on 01-Dec-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.Iterator;
import java.util.List;

import com.kajabity.prolog.core.arithmetic.ArithmeticExpression;
import com.kajabity.prolog.core.arithmetic.PrologArithmeticException;
import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * Class description here....
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class IsPredicate extends GroundLiteral
{

    /**
     * @param functor
     * @param arity
     */
    public IsPredicate( Database database )
    {
        super( database, "is", 2 );
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

            Iterator<Expression> iargs = args.iterator();
            Expression left = null;
            Expression arith = null;

            if( iargs.hasNext() )
            {
                left = (Expression) iargs.next();

                if( iargs.hasNext() )
                {
                    arith = (Expression) iargs.next();
                    ArithmeticExpression ae = new ArithmeticExpression( arith );

                    NumericConstant result = ae.eval( database );

                    return Expressions.unify( left, result );
                }
            }
        }

        return null;
    }

}
