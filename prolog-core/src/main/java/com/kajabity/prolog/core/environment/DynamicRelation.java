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

import java.util.ArrayList;
import java.util.List;


/**
 * @author Simon To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DynamicRelation extends Relation {
    private final List<Clause> assertions = new ArrayList<Clause>();

    private boolean dynamic = true;


    /**
     * @param name
     * @param arity
     */
    public DynamicRelation(String name, int arity) {
        super(name, arity);
    }


    /**
     * @see com.kajabity.prolog.core.environment.Relation#isBuiltIn()
     */
    public boolean isGroundLiteral() {
        return false;
    }


    /**
     * @see com.kajabity.prolog.core.environment.Relation#getConsortIterator(com.kajabity.prolog.core.expression.Term)
     */
    public IConsortIterator getConsortIterator(Goal goal) {
        return new DynamicConsortIterator(assertions, goal);
    }


    /**
     * @see com.kajabity.prolog.core.environment.Relation#isDynamic()
     */
    public boolean isDynamic() {
        return dynamic;
    }


    /**
     *
     * @see com.kajabity.prolog.core.environment.Relation#setDynamic(boolean)
     */
    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
    }


    /**
     * @throws PrologException
     * @see com.kajabity.prolog.core.environment.Relation#add(com.kajabity.prolog.core.environment.Clause)
     */
    public void add(Clause clause) throws PrologException {
        if (dynamic) {
            this.assertions.add(clause);
        } else {
            throw new PrologException("Relation " + getName() + " is not Dynamic.");
        }
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.environment.Relation#insert(int,
     *      com.kajabity.prolog.core.environment.Clause)
     */
    public void insert(int index, Clause clause) throws PrologException {
        if (dynamic) {
            this.assertions.add(index, clause);
        } else {
            throw new PrologException("Relation " + getName() + " is not Dynamic.");
        }
    }
}
