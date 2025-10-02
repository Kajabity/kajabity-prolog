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
import com.kajabity.prolog.core.expression.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A comparison predicate which tests if two expressions are identical - they
 * have the same structure and uninstantiated variables are the same ones.
 *
 * @author Simon J. Williams
 */
public class EqualSamePredicate extends GroundLiteral {
    private final static Logger logger = LogManager.getLogger(EqualSamePredicate.class);

    /**
     * Construct and initialise this predicate.
     *
     * @param database
     */
    public EqualSamePredicate(Database database) {
        this(database, "==", 2);
    }

    public EqualSamePredicate(Database database, String name, int arity) {
        super(database, name, arity);
        assert arity == 2;
    }

    /**
     * Compare the two arguments using a similar technique to the 'unify'
     * mechanism but without any matching of variables.
     *
     * @see com.kajabity.prolog.core.environment.GroundLiteral#execute(Goal)
     */
    protected List<Variable> execute(Goal goal) {
        if (goal.getTerm() instanceof Tuple) {
            Tuple tuple = (Tuple) goal.getTerm();
            List<Expression> args = tuple.getArgs().getTerms();

            Expression p1 = args.get(0);
            Expression p2 = args.get(1);

            logger.debug("Comparing <<" + p1 + ">> and <<" + p2 + ">>");

            List<Expression> leftTerms = new ArrayList<Expression>();
            leftTerms.add(p1);

            List<Expression> rightTerms = new ArrayList<Expression>();
            rightTerms.add(p2);

            do {
                // Pop the two terms
                Expression left = leftTerms.remove(0);
                left = left.getFinalInstantiation();

                Expression right = rightTerms.remove(0);
                right = right.getFinalInstantiation();

                logger.debug("\tcomparing <<" + left + ">> and <<" + right + ">>");

                if (left.isTerm() != right.isTerm()) {
                    // Fail, one is a Term, the other isn't.
                    logger.debug("\tfailed: one is a Term, the other isn't.");
                    return null;
                }
                if (left.isTerm()) {
                    if (((Term) left).getTag() == ((Term) right).getTag()) {
                        if (((Term) left).getTag() == Term.TAG_TUPLE || ((Term) left).getTag() == Term.TAG_LIST) {
                            if (((Tuple) left).getArity() == ((Tuple) right).getArity()) {
                                // Two tuples - append the functor and args
                                // of
                                // each.
                                leftTerms.add(((Tuple) left).getFunctor());
                                leftTerms.add(((Tuple) left).getArgs());
                                rightTerms.add(((Tuple) right).getFunctor());
                                rightTerms.add(((Tuple) right).getArgs());
                            } else {
                                // fail
                                logger.debug("\tfailed: Left and Right are different arity.");
                                return null;
                            }
                        } else if (!left.equals(right)) {
                            // fail
                            logger.debug("\tfailed: Left and Right are different constants.");
                            return null;
                        }
                    } else {
                        // fail
                        logger.debug("\tfailed: Left and Right are different types.");
                        return null;
                    }
                } else {
                    // Both TermList - append.
                    if (((TermList) left).size() == ((TermList) right).size()) {
                        leftTerms.addAll(((TermList) left).getTerms());
                        rightTerms.addAll(((TermList) right).getTerms());
                    } else {
                        // fail
                        logger.debug("\tfailed: Right and left are different lengths.");
                        return null;
                    }
                }
            } while (!leftTerms.isEmpty());
        }

        // If we get here, we have succeded.
        return Collections.emptyList();
    }
}
