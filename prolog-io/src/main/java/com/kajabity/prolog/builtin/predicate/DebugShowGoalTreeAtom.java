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
package com.kajabity.prolog.builtin.predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.prolog.io.format.DisplayPrologFormat;
import com.kajabity.prolog.io.format.PrologFormat;


/**
 * @author Simon
 */
public class DebugShowGoalTreeAtom extends GroundLiteral
{
    public DebugShowGoalTreeAtom( Database database )
    {
        this( database, "debug_show_goal_tree", 0 );
    }

    public DebugShowGoalTreeAtom( Database database, String name, int arity )
    {
        super( database, name, arity );
        assert arity == 0;
    }


    /**
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        List<String> lines = new ArrayList<String>();
        StringBuffer line = new StringBuffer();

        PrologFormat format = new DisplayPrologFormat();
        Goal g = goal;
        while( g != null )
        {
            line.insert( 0, format.format( g.getTerm() ) );

            if( g.getPrevSibling() != null )
            {
                g = (Goal) g.getPrevSibling();
                line.insert( 0, ", " );
            }
            else
            {
                lines.add( 0, line.toString() );
                line.setLength( 0 );

                g = (Goal) g.getParent();
            }
        }

        Iterator<String> iLine = lines.iterator();
        while( iLine.hasNext() )
        {
            database.getCurrentOutputStream().println(
                    line.toString() + iLine.next() );
            line.append( ". " );
        }
        return Collections.emptyList();
    }
}
