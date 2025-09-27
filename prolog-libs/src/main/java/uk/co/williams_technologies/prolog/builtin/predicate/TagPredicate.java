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

/**
 * Created on 16-Mar-2003 To change this generated comment edit the template
 * variable "filecomment": Window>Preferences>Java>Templates. To enable and
 * disable the creation of file comments go to Window>Preferences>Java>Code
 * Generation.
 */
package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.Iterator;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.Tuple;
import com.kajabity.prolog.core.expression.Variable;


/**
 * @author Simon To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class TagPredicate extends GroundLiteral
{
    public TagPredicate( Database database )
    {
        super( database, "tag", 2 );
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        if( goal.getTerm() instanceof Tuple )
        {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Iterator<Expression> iargs = args.iterator();
            Expression left = null;
            Expression right = null;

            if( iargs.hasNext() )
            {
                left = (Expression) iargs.next();

                if( iargs.hasNext() )
                {
                    right = (Expression) iargs.next();

                    NumericConstant result = new NumericConstant( ((Term) left.getFinalInstantiation()).getTag() );

                    return Expressions.unify( right, result );
                }
            }
        }

        return null;
    }
}
