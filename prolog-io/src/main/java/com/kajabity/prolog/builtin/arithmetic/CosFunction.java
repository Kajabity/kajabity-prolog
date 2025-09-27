/*
 * ******************************************************************************
 * Copyright :(c) 2003/4 Williams Technologies Limited Project : Java Prolog
 * Created on 28-Jan-04 $Header:
 * /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/arithmetic/CosFunction.java,v
 * 1.3 2004/05/05 05:28:38 simon Exp $
 * ******************************************************************************
 * $Log: CosFunction.java,v $ Revision 1.3 2004/05/05 05:28:38 simon Lots of
 * changes... Revision 1.2 2004/02/24 04:49:52 simon add standard header.
 * ******************************************************************************
 */
package com.kajabity.prolog.builtin.arithmetic;

import java.util.Stack;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.expression.NumericConstant;


public class CosFunction extends Function
{
    public CosFunction( String name, int arity )
    {
        super( name, arity );
    }


    public NumericConstant evaluate( Stack<NumericConstant> values )
    {
        NumericConstant right = (NumericConstant) values.pop();
        return new NumericConstant( Math.cos( right.doubleValue() ) );
    }
}
