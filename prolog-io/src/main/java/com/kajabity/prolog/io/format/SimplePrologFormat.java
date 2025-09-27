/*
 * Created on May 11, 2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.kajabity.prolog.io.format;

import com.kajabity.prolog.core.expression.Expression;

/**
 * Simple Prolog expression formatter.
 *
 * @author Simon J. Williams
 */
public class SimplePrologFormat extends PrologFormat
{

    /**
     * @see PrologFormat#format(com.kajabity.prolog.core.expression.Expression)
     */
    public String format( Expression expr )
    {
        if( expr == null )
            return "";
        else
            return expr.toString(); // + "."
    }
}
