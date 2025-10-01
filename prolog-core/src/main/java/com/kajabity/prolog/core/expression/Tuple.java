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
 * DOCUMENT ME!
 *
 * @author Simon J. Williams
 */
public final class Tuple
        extends Term {
    private final Term functor;
    private final TermList args;

    /**
     * DOCUMENT ME!
     *
     * @param functor
     * @param args
     */
    public Tuple(Term functor, TermList args) {
        super(TAG_TUPLE, functor.toString(), args.size());

        this.functor = functor;
        this.args = args;
    }

    /**
     * DOCUMENT ME!
     *
     * @param functor
     * @param args
     */
    public Tuple(Term functor, Term arg) {
        super(TAG_TUPLE, functor.toString(), 1);

        this.functor = functor;
        this.args = new TermList(arg);
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the args.
     */
    public TermList getArgs() {
        return args;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the functor.
     */
    public Term getFunctor() {
        return functor;
    }

    /**
     * equals if the functor and args are equal.
     *
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (obj instanceof Tuple) {
            return functor.equals(((Tuple) obj).functor) &&
                    args.equals(((Tuple) obj).args);
        }

        return false;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        //TODO: hash from func and args.
        return super.hashCode();
    }

    /**
     * @see Object#toString()
     */
    public String toString() {

        String buf = functor.toString() +
                "( " +
                args.toString() +
                " )";

        return buf;
    }

    public Expression makeCopy(Map<String, Variable> variables) {
        return new Tuple((Term) functor.makeCopy(variables),
                (TermList) args.makeCopy(variables));
    }

    /**
     * @see Expression#containsVariable(Variable)
     */
    public boolean containsVariable(Variable variable) {
        return functor.containsVariable(variable) ||
                args.containsVariable(variable);
    }
}
