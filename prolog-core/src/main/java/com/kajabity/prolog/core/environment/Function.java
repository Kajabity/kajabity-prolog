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

import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.TermKey;

import java.util.Stack;


/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class Function
        extends TermKey {
    public Function(String name, int arity) {
        super(name, arity);
    }

    public abstract NumericConstant evaluate(Stack<NumericConstant> values);
}
