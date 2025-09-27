/*
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.environment.operator;

import java.util.Stack;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;





public class TupleOperator extends Operator
{
    public TupleOperator( String name, Associativity associativity,
            int precedence )
    {
        super( name, associativity, precedence );
    }


    public Expression apply( Stack<Expression> values ) throws PrologOperatorException
    {
        Expression args = (Expression) values.pop();
        Expression functor = (Expression) values.pop();

        if( !functor.isTerm() )
        {
            throw new PrologOperatorException(
                    "Can't create tuple with TermList args." );
        }

        if( args.isTerm() )
        {
            return new Tuple( (Term) functor, (Term) args );
        }
        else
        {
            return new Tuple( (Term) functor, (TermList) args );
        }
    }
}
