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
