/*
 *******************************************************************************
 *  Copyright   :(c) 2003/4 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 * 	Created on 30-Nov-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/builtin/arithmetic/SubtractFunction.java,v 1.3 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: SubtractFunction.java,v $
 *  Revision 1.3  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.2  2004/02/24 04:49:52  simon
 *  add standard header.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.builtin.arithmetic;

import java.util.Stack;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.expression.NumericConstant;


/**
 * Class description here....
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SubtractFunction extends Function
{
    /**
     * @param functor
     */
    public SubtractFunction( String name, int arity )
    {
        super( name, arity );
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.IEvaluable#evaluate()
     */
    public NumericConstant evaluate( Stack<NumericConstant> values )
    {
        NumericConstant right = (NumericConstant) values.pop();
        NumericConstant left = (NumericConstant) values.pop();

        NumericConstant result;
        if( right.isReal() || left.isReal() )
        {
            result = new NumericConstant( left.doubleValue()
                    - right.doubleValue() );
        }
        else
        {
            result = new NumericConstant( left.longValue() - right.longValue() );
        }

        return result;
    }
}
