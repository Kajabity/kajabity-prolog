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
 * Created on Mar 24, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.core.expression;

/**
 * @author Simon
 * <p>
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TermKey {

    protected final String name;
    private final int arity;
    private final String key;
    /**
     * Contruct the abstract base Term class - provide and explicit tag value.
     *
     * @param tag   identifies the type of the Term.
     * @param name  DOCUMENT ME!
     * @param arity DOCUMENT ME!
     */
    public TermKey(String name, int arity) {
        this.name = name;
        this.arity = arity;
        this.key = name + "/" + arity;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the arity.
     */
    public int getArity() {
        return arity;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * DOCUMENT ME!
     *
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
}
