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
