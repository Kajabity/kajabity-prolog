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

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.GroundLiteral;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.*;
import com.kajabity.prolog.io.Prolog;
import com.kajabity.utils.token.TokenException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Simon To change this generated comment edit the template variable
 * "typecomment": Window>Preferences>Java>Templates. To enable and
 * disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ConsultPredicate extends GroundLiteral {
    private final static Logger logger = Logger.getLogger(ConsultPredicate.class);

    public ConsultPredicate(Database database) {
        this(database, "consult", 1);
    }

    public ConsultPredicate(Database database, String name, int arity) {
        super(database, name, arity);
        assert arity == 1;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(com.kajabity.prolog.core.environment.PrologDatabase,
     *      com.kajabity.prolog.io.solver.Goal)
     */
    protected List<Variable> execute(Goal goal) throws TokenException, IOException, PrologException {
        logger.debug("CONSULT: " + goal.getTerm());

        if (goal.getTerm() instanceof Tuple) {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Expression p1 = args.get(0);

            logger.debug("CONSULT: " + p1 + " - tag " + ((Term) p1).getTag());

            if (p1.isTerm() && ((Term) p1).getTag() == Term.TAG_ATOM) {
                String filename = ((Atom) p1).getName();
                database.getCurrentOutputStream().println("Consulting [" + filename + "].");

                Prolog.consult(database, filename);

                return Collections.emptyList();
            }
        }

        return null;
    }
}
