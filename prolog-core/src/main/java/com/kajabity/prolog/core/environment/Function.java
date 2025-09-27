/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Environment
 *
 *  Created     :   Mar 24, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/Environment/src/uk/co/williams_technologies/prolog/environment/Function.java,v 1.2 2004/07/01 05:31:52 simon Exp $
 *******************************************************************************
 *  $Log: Function.java,v $
 *  Revision 1.2  2004/07/01 05:31:52  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.environment;

import java.util.Stack;

import com.kajabity.prolog.core.expression.NumericConstant;
import com.kajabity.prolog.core.expression.TermKey;




/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public abstract class Function
    extends TermKey
{
    public Function( String name, int arity )
    {
    	super( name, arity );
    }

    public abstract NumericConstant evaluate( Stack<NumericConstant> values );
}
