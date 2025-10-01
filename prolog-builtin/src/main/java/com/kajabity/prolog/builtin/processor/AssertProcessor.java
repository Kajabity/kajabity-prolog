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
package com.kajabity.prolog.builtin.processor;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;


/**
 * Assert a "Horn" clause of the form: -<term>:- <term>, <term>.
 *
 * @author Simon J. Williams
 */
public class AssertProcessor extends Processor
{

    /**
     * @param functor
     * @param arity
     */
    public AssertProcessor( String name )
    {
        super( name, 2 );
    }


    /**
     * Add the expression as an assertion
     *
     * @throws PrologException
     * @see com.kajabity.prolog.io.builtin.processor.Processor#perform()
     */
    public void execute( Database db, Term term ) throws PrologException
    {
        //  It should be a tuple ":-( head, tail ).
        if( term.getTag() == Term.TAG_TUPLE )
        {
            TermList args = ((Tuple) term).getArgs();

            if( args.size() != 2 )
            {
                throw new PrologException( "Assert must have a tuple with 2 args: " + term );
            }

            Expression head = (Expression) args.getTerms().get( 0 );
            Expression tail = (Expression) args.getTerms().get( 1 );

            if( !head.isTerm() )
            {
                throw new PrologException( "Assert must have a Term as the head: " + term );
            }

            db.assertz( (Term) head, tail );
        }
        else
        {
            throw new PrologException( "Assert must have a tuple: " + term );
        }
    }

}
