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

/*
 * Created on Jul 5, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.environment;

import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IConsortIterator {
    /**
     * Search the list of clauses for one which matches the term and return a
     * List of substitutions if one is found. - Expressions.unify(
     * goal.getTerm(), head );
     *
     * @return
     * @throws PrologException
     * @throws IOException
     * @throws TokenException
     * @throws ParseException
     */
    List<Variable> hasNext() throws TokenException, IOException, PrologException;

    /**
     * For a located clause, return a working copy of the tail.
     */
    Clause getClause();
}
