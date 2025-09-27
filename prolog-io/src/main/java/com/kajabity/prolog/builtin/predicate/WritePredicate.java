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

/*
 * Created on 18-Nov-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.kajabity.prolog.builtin.predicate;

import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;

/**
 * Class description here....
 *
 * @author Simon
 */
public class WritePredicate extends GroundLiteral
{
    public WritePredicate( Database database )
    {
        this( database, "write", 1 );
    }

    public WritePredicate( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 1;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        List<Variable> result = null;

        if( goal.getTerm().getFinalInstantiation() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm().getFinalInstantiation();

            List<Expression> args = tuple.getArgs().getTerms();
            Expression p1 = args.get( 0 );

            database.getCurrentOutputStream().print( p1.getFinalInstantiation().toString() );

            result = Collections.emptyList();
        }

        return result;
    }
}
