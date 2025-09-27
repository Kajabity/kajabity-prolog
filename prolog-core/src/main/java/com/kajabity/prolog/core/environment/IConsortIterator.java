/*
 * Created on Jul 5, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.environment;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;

public interface IConsortIterator
{
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
    public List<Variable> hasNext() throws TokenException, IOException, PrologException;

    /**
     * For a located clause, return a working copy of the tail.
     */
    public Clause getClause();
}
