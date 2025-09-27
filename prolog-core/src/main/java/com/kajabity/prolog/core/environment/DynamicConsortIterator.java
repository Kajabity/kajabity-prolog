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
package com.kajabity.prolog.core.environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;

/**
 * @author simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DynamicConsortIterator implements IConsortIterator
{
    private Goal     goal;

    private Iterator<Clause> iConsorts;

    private Clause   clause;

    public DynamicConsortIterator( List<Clause> consorts, Goal goal )
    {
        this.goal = goal;

        iConsorts = consorts.iterator();
    }

    /**
     * @see IConsortIterator#hasNext()
     */
    public List<Variable> hasNext() throws TokenException, IOException, PrologException
    {
        while( iConsorts.hasNext() )
        {
            clause = iConsorts.next();
            Map<String, Variable> variables = new HashMap<String, Variable>();

            Term head = (Term) clause.getHead().makeCopy( variables );

            // If it unifies
            List<Variable> substitutions = Expressions.unify( goal.getTerm(), head );

            if( substitutions != null )
            {
                goal.setVariables( variables );
                return substitutions;
            }
        }

        return null;
    }

    /**
     * @see IConsortIterator#getClause()
     */
    public Clause getClause()
    {
        return clause;
    }

}
