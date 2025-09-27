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

package com.kajabity.prolog.core.environment.operator;

import com.kajabity.prolog.core.environment.Associativity;

public class Operators
{

    // ========================================================================
    public final static Operator CONJUNCTION_OPERATOR = new ConjunctionOperator( ",", Associativity.xfy, 1000 );

    public final static Operator DISJUNCTION_OPERATOR = new SimpleOperator( ";", Associativity.xfy, 1100 );

    public final static Operator BAR_OPERATOR         = new BarOperator( "|", Associativity.xfy, 1000 );

    public final static Operator BRACKET_OPERATOR     = new BracketOperator( "(", Associativity.xfy, 9999 );

    public final static Operator TUPLE_OPERATOR       = new TupleOperator( "(", Associativity.xfy, 9999 );

    public final static Operator LIST_OPERATOR        = new ListOperator( "[", Associativity.xfy, 9999 );

}
