/*
 * ******************************************************************************
 * Copyright :(c) 2003/4 Williams Technologies Limited Project : Java Prolog
 * Created on 28-Jan-04 $Header:
 * /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/arithmetic/EulersConstant.java,v
 * 1.2 2004/02/24 04:49:52 simon Exp $
 * ******************************************************************************
 * $Log: EulersConstant.java,v $ Revision 1.2 2004/02/24 04:49:52 simon add
 * standard header.
 * ******************************************************************************
 */
package uk.co.williams_technologies.prolog.builtin.arithmetic;

import java.util.Stack;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.expression.NumericConstant;


public class EulersConstant extends Function
{
    public EulersConstant( String name, int arity )
    {
        super( name, arity );
    }


    public NumericConstant evaluate( Stack<NumericConstant> values )
    {
        return new NumericConstant( Math.E );
    }
}
