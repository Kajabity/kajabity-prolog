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
package com.kajabity.prolog.core.engine;

import com.kajabity.prolog.core.environment.*;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DOCUMENT ME!
 *
 * @author Simon
 */
public class Solver {
    private final static Logger logger = Logger.getLogger(Solver.class);

    private final PrologContext database;

    private final Goal top;

    private Goal lastGoal;


    /**
     * Construct a solver to solve the goal given in 'expr' using assertions in
     * the 'database'.
     *
     * @param database a database of assertions to test the goal against.
     * @param terms    a list of terms to be solved.
     */
    public Solver(PrologContext database, Expression terms) {
        this.database = database;

        top = new Goal(null);
        Map<String, Variable> variables = new HashMap<String, Variable>();
        top.setChildren(terms.makeCopy(variables));
        top.setVariables(variables);
        lastGoal = (Goal) top.depthNext();
    }


    /**
     * Find the next solution.
     *
     * @return @throws PrologException
     * @throws PrologException
     * @throws IOException
     * @throws TokenException
     * @throws ParseException
     */
    public boolean hasSolution() throws TokenException, IOException,
            PrologException {
        //  Start from where we left off.
        Goal goal = lastGoal;

        boolean success = true;

        //  Repeat until there are no more goals to satisfy - success.
        while (goal != null) {
            logger.debug("Solver: goal " + goal.getTerm());

            Term term = goal.getTerm();
            List<Variable> substitutions;

            if (term == null) {
                //	Empty goal - we have reached the root in backtracking =>
                // FAIL.
                success = false;
                break;
            }

            goal.reset();

            // goal has a term
            IConsortIterator consortIterator = goal.getConsortIterator();

            if (consortIterator == null) {
                //	Always start the Goal afresh.
                Relation relation = database.findRelation(term);

                if (relation == null) {
                    throw new NoRelationException("No relation for term "
                            + term + "(" + term.getKey() + ")");
                }

                consortIterator = relation.getConsortIterator(goal);
                goal.setConsortIterator(consortIterator);
            }//end if consortIterator = null

            substitutions = consortIterator.hasNext();

            if (substitutions == null) {
                // Backtrack.
                do {
                    goal = (Goal) goal.depthPrev();
                    lastGoal = null;

                    if (goal == null) {
                        success = false;
                    } else {
                        goal.reset();
                    }
                } while (goal != null && goal.isCut());
            } else {
                //  Add the substitutions to the Goal - so they can
                // be undone later.
                goal.setSubstitutions(substitutions);

                Clause clause = consortIterator.getClause();
                if (clause.getTail() != null) {
                    goal.setChildren(clause.getTail().makeCopy(
                            goal.getVariables()));
                }

                //  Go on to the next goal.
                lastGoal = goal;
                goal = (Goal) goal.depthNext();
            }
        }

        return success;
    }


    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public Goal getTop() {
        return top;
    }


    public class NoRelationException extends PrologException {

        /**
         *
         */
        private static final long serialVersionUID = 7176036903579308954L;


        /**
         *
         */
        public NoRelationException() {
            super();
            // TODO Auto-generated constructor stub
        }


        /**
         * @param message
         */
        public NoRelationException(String message) {
            super(message);
            // TODO Auto-generated constructor stub
        }


        /**
         * @param message
         * @param cause
         */
        public NoRelationException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub
        }


        /**
         * @param cause
         */
        public NoRelationException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub
        }
    }
}
