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
package uk.co.williams_technologies.prolog.builtin.predicate;

import java.util.Collections;
import java.util.List;

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.expression.Variable;

/**
 * @author Simon
 */
public class CutAtom extends GroundLiteral
{
    /**
     * @param database
     */
    public CutAtom( Database database )
    {
        super( database, "!", 0 );
    }

    /**
     * @param goal
     * @return
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(Goal)
     */
    protected List<Variable> execute( Goal goal )
    {
        goal.cut();

        return Collections.emptyList();
    }
}
