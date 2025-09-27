/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/parse/Matcher.java,v 1.2 2004/07/01 05:31:53 simon Exp $
 *******************************************************************************
 *  $Log: Matcher.java,v $
 *  Revision 1.2  2004/07/01 05:31:53  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.4  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.3  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.io.parse.match;

import com.kajabity.prolog.io.parse.PrologParser;

/**
 * DOCUMENT ME!
 *
 * @author Simon
 */
public abstract class Matcher
{
    public abstract boolean match( PrologParser parser, int param );
}
