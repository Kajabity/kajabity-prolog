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

package com.kajabity.prolog.core.environment;

import com.kajabity.prolog.core.arithmetic.Function;
import com.kajabity.prolog.core.environment.operator.Operator;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;

public interface PrologContext {

    void asserta(Term head, Expression tail) throws PrologException;

    void assertz(Term head, Expression tail) throws PrologException;

    Relation findRelation(Term term);

    /**
     * Lookup the given name as an operator with the specified type - return
     * true if it exists and store it locally.
     *
     * @param name
     * @param position
     * @return
     */
    Operator findOperator(String name, Associativity.Position position);

    Function findFunction(String name, int arity);

    /**
     * Lookup a processor for a term. Some expected ones are: - assertClause
     * assertFact - default question quietQuestion - processing operations quit
     * consultList trace/no_trace, list (list predicates), etc.
     *
     * @param name
     * @param arity
     * @return
     */
    Processor findProcessor(String name, int arity);

    /**
     * @return Returns the terminated.
     */
    boolean isTerminated();

    /**
     * @param terminated The terminated to set.
     */
    void setTerminated(boolean terminated);

}
