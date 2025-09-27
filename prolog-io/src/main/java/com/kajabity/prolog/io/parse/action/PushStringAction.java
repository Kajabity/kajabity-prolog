/*
 * Copyright (c) 2004-2025 Simon J. Williams
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
package com.kajabity.prolog.io.parse.action;

import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.io.parse.PrologParser;



public class PushStringAction extends Action
{
    public void act( PrologParser parser ) throws PrologException
    {
        byte[] bytes = parser.getTokeniser().toString().getBytes();
        Term term = Expressions.NIL;
        for( int b = bytes.length - 1; b >= 0; b-- )
        {
            term = new Tuple( Expressions.NIL, new TermList( new NumericConstant(
                    bytes[ b ] ), term ) );
        }
        parser.pushValue( term );
    }
}
