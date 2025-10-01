/*
 * Copyright (c) 2003-2025 Simon J. Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
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
public class ProductFunction extends Function
{
    /**
     * @param functor
     */
    public ProductFunction( String name, int arity )
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
                    * right.doubleValue() );
        }
        else
        {
            result = new NumericConstant( left.longValue() * right.longValue() );
        }

        return result;
    }

}
