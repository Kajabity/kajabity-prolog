/*
 * ******************************************************************************
 * Copyright :(c) 2003/4 Williams Technologies Limited Project : Java Prolog
 * Created on 28-Jan-04 $Header:
 * /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/arithmetic/AbsFunction.java,v
 * 1.3 2004/05/05 05:28:38 simon Exp $
 * ******************************************************************************
 * $Log: AbsFunction.java,v $ Revision 1.3 2004/05/05 05:28:38 simon Lots of
 * changes... Revision 1.2 2004/02/24 04:49:52 simon add standard header.
 * ******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin.arithmetic;

import java.util.Stack;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.expression.NumericConstant;

public class AbsFunction extends Function
{
    public AbsFunction( String name, int arity )
    {
        super( name, arity );
    }

    public NumericConstant evaluate( Stack<NumericConstant> values )
    {
        NumericConstant right = (NumericConstant) values.pop();

        if( right.isReal() )
            return new NumericConstant( Math.abs( right.doubleValue() ) );
        else
            return new NumericConstant( Math.abs( right.longValue() ) );
    }
}
