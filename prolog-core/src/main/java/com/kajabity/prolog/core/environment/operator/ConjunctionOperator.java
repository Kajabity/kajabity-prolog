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
import com.kajabity.prolog.core.expression.TermList;





public class ConjunctionOperator extends Operator
{
    public ConjunctionOperator( String name, Associativity associativity,
            int precedence )
    {
        super( name, associativity, precedence );
    }


    public Expression apply( Stack<Expression> values ) throws PrologOperatorException
    {
        Expression right = (Expression) values.pop();
        Expression left = (Expression) values.pop();
        return TermList.concatenate( left, right );
    }
}
