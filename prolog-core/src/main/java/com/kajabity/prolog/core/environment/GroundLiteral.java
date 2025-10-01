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

import com.kajabity.prolog.core.engine.Goal;
import com.kajabity.prolog.core.expression.Variable;
import com.kajabity.utils.token.TokenException;

import java.io.IOException;
import java.util.List;

/**
 * @author Simon To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class GroundLiteral extends Relation {
    protected Database database;


    /**
     * @param name
     * @param arity
     */
    public GroundLiteral(Database database, String name, int arity) {
        super(name, arity);

        this.database = database;
    }


    /**
     * @see com.kajabity.prolog.core.environment.Relation#isBuiltIn()
     */
    public boolean isGroundLiteral() {
        return true;
    }


    /**
     * @param database
     * @param goal
     * @return @throws PrologException
     * @throws ParseException
     * @throws IOException
     * @throws TokenException
     */
    protected abstract List<Variable> execute(Goal goal) throws TokenException,
            IOException, PrologException;


    /**
     * @return Returns the dynamic.
     */
    public boolean isDynamic() {
        return false;
    }


    /**
     * @param dynamic The dynamic to set.
     */
    public void setDynamic(boolean dynamic) {
        throw new UnsupportedOperationException(
                "setDynamic is not implemented for GroundLiterals");
    }

    /**
     * @see com.kajabity.prolog.core.environment.Relation#getConsortIterator(Goal)
     */
    public IConsortIterator getConsortIterator(Goal goal) {
        // TODO Auto-generated method stub
        return new BuiltInConsortIterator(goal);
    }

    public void add(Clause clause) throws PrologException {
        throw new PrologException("Relation " + getName()
                + " is a GroundLiteral and is not Dynamic.");
    }

    public void insert(int index, Clause clause) throws PrologException {
        throw new PrologException("Relation " + getName()
                + " is a GroundLiteral and is not Dynamic.");
    }

    protected class BuiltInConsortIterator implements IConsortIterator {
        Goal goal;
        private boolean first = true;

        BuiltInConsortIterator(Goal goal) {
            this.goal = goal;
        }


        /**
         * @throws PrologException
         * @throws ParseException
         * @throws IOException
         * @throws TokenException
         * @throws Exception
         * @throws PrologException
         * @throws ParseException
         * @see com.kajabity.prolog.core.environment.IConsortIterator#hasNext()
         */
        public List<Variable> hasNext() throws TokenException, IOException,
                PrologException {
            if (first) {
                first = false;
                return execute(goal);
            }

            return null;
        }


        /**
         * @see com.kajabity.prolog.core.environment.IConsortIterator#getClause()
         */
        public Clause getClause() {
            return new Clause(null, null);
        }

    }
}
