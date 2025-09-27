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
package com.kajabity.prolog.io.parse.match;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;



public class InfixOperatorMatcher extends Matcher
{
    public boolean match( PrologParser parser, int param )
    {
        if( parser.getTokeniser().getType() == Tokeniser.ATOM )
        {
            parser.setOperator( parser.getDatabase().findOperator(
                    parser.getTokeniser().toString(),
                    Associativity.Position.INFIX ) );
            return parser.getOperator() != null;
        }
        return false;
    }
}
