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
package com.kajabity.prolog.core.expression;

import java.util.Map;

/**
 * An abstract base class for all of the Prolog Terms.
 *
 * @author Simon J. Williams
 */
public abstract class Term extends TermKey implements Expression {
    public final static int TAG_VARIABLE = 0;

    public final static int TAG_INTEGER = 1;

    public final static int TAG_REAL = 2;

    public final static int TAG_ATOM = 3;

    public final static int TAG_NIL = 4;

    public final static int TAG_LIST = 5;

    public final static int TAG_TUPLE = 6;

    private int tag;


    /**
     * Contruct the abstract base Term class - provide and explicit tag value.
     *
     * @param tag   identifies the type of the Term.
     * @param name  DOCUMENT ME!
     * @param arity DOCUMENT ME!
     */
    public Term(int tag, String name, int arity) {
        super(name, arity);

        this.tag = tag;

        // Check for Lists...
        if (name.equals(".")) {
            if (arity == 0) {
                this.tag = TAG_NIL;
            } else if (arity == 2) {
                this.tag = TAG_LIST;
            }
        }
    }


    /**
     * DOCUMENT ME!
     *
     * @return Returns the tag.
     */
    public int getTag() {
        return tag;
    }


    /**
     * A method for combining comma separated Terms into a List. It assumes that
     * the first expression might be either a Term or an existing List, but adds
     * the second to the list whichever it is. This is to support operators such
     * as the implication (":-") which have a higher precedence than the comma
     * (",").
     *
     * @param expr A Term or List of terms to be extended.
     * @return a List of Terms.
     */
    public TermList append(Expression expr) {
        return new TermList(this, expr);
    }


    /**
     * @see Expression#containsVariable(Variable)
     */
    public boolean containsVariable(Variable variable) {
        return false;
    }


    /**
     * @see Expression#getFinalInstantiation()
     */
    public Expression getFinalInstantiation() {
        return this;
    }


    /*
     * (non-Javadoc)
     *
     * @see com.kajabity.prolog.core.expression.Expression#makeCopy(java.util.Map)
     */
    public Expression makeCopy(Map<String, Variable> variables) {
        return this;
    }


    /**
     * @see Expression#isTerm()
     */
    public boolean isTerm() {
        return true;
    }
}
