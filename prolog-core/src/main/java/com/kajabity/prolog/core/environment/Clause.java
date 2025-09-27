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

import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;

/**
 * @author simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class Clause
{
    private Term       head;

    private Expression tail;


    public Clause( Term head, Expression tail )
    {
        this.head = head;
        this.tail = tail;
    }


    /**
     * @return Returns the head.
     */
    public Term getHead()
    {
        return head;
    }


    /**
     * @return Returns the tail.
     */
    public Expression getTail()
    {
        return tail;
    }
}
