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
package uk.co.williams_technologies.prolog.builtin.processor;

import java.io.IOException;


import com.kajabity.prolog.core.engine.Solver;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.utils.token.TokenException;

/**
 * A command processor to solve a solution once only - with no output.  IT is used for
 * running immediate goals - e.g. when consulting a file.
 * @author Simon J. Williams
 */
public class ImmediateProcessor extends Processor
{

    /**
     * @param name
     */
    public ImmediateProcessor( String name )
    {
        super( name, 1 );
    }


    /**
     * @throws PrologException
     * @throws ParseException
     * @throws IOException
     * @throws TokenException
     * @see com.kajabity.prolog.core.environment.Processor#execute(com.kajabity.prolog.core.environment.Database, com.kajabity.prolog.core.expression.Term)
     */
    public void execute( Database db, Term term ) throws TokenException, IOException, PrologException
    {
        //  Get the list of goals from the given term.
        if( term.getTag() != Term.TAG_TUPLE )
        {
            throw new PrologException( "Solve: Expected a tuple: " + term );
        }

        TermList args = ((Tuple) term).getArgs();
        if( args.size() != 1 )
        {
            throw new PrologException(
                    "Solve: Expected a single arg (Term or TermList) : " + term );
        }

        Expression terms = args.getHead();
        Solver solver = new Solver( db, terms );

        //  Get the solution - ignore it!
        solver.hasSolution();
    }

}
