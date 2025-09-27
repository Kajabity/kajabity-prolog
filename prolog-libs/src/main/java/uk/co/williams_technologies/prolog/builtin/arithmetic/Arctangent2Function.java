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
package uk.co.williams_technologies.prolog.builtin.arithmetic;

import java.util.Stack;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.expression.NumericConstant;


public class Arctangent2Function extends Function
{
    public Arctangent2Function( String name, int arity )
    {
        super( name, arity );
    }


    public NumericConstant evaluate( Stack<NumericConstant> values )
    {
        NumericConstant right = (NumericConstant) values.pop();
        NumericConstant left = (NumericConstant) values.pop();

        return new NumericConstant( Math.atan2( left.doubleValue(), right
                .doubleValue() ) );
    }
}
