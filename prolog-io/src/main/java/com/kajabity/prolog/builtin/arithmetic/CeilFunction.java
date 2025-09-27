/*
 * ******************************************************************************
 * Copyright :(c) 2003/4 Williams Technologies Limited Project : Java Prolog
 * Created on 28-Jan-04 $Header:
 * /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/arithmetic/CeilFunction.java,v
 * 1.3 2004/05/05 05:28:38 simon Exp $
 * ******************************************************************************
 * $Log: CeilFunction.java,v $ Revision 1.3 2004/05/05 05:28:38 simon Lots of
 * changes... Revision 1.2 2004/02/24 04:49:52 simon add standard header.
 * ******************************************************************************
 */
package com.kajabity.prolog.builtin.arithmetic;

import java.util.Stack;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.expression.NumericConstant;


public class CeilFunction extends Function
{
    public CeilFunction( String name, int arity )
    {
        super( name, arity );
    }


    public NumericConstant evaluate( Stack<NumericConstant> values )
    {
        NumericConstant right = (NumericConstant) values.pop();

        if( right.isReal() )
            return new NumericConstant( Math.ceil( right.doubleValue() ) );
        else
            return new NumericConstant( Math.ceil( right.longValue() ) );
    }
}
