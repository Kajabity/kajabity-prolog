/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/HandlerTable.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: HandlerTable.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.4  2003/11/19 07:56:56  simon
 *  Changed source directories
 *  Added PrologInput/OutputStreams
 *  Added Write predicate (not quite right yet).
 *
 *  Revision 1.3  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */


/**
 * Created on 18-Mar-2003
 */
package com.kajabity.utils.token;


/**
 * @author Simon J. Williams
 */
public class HandlerTable
{
    /** An array of flags for each character indicating type membership. */
    private int masks[] = new int[ 256 ];

    /** An array of handlers for individual characters. */
    private Handler handlers[] = new Handler[ 256 ];

    /**
     * An extra handler for end of input and, for simplicity, any invalid
     * character indices.
     */
    private Handler eosHandler = null;

    /** Returned type for invalid or eos inputs. */
    private int eosMask = 0;

    public Handler getHandler( int ch )
    {
        if( ch < 0 || ch >= handlers.length )
        {
            return eosHandler;
        }
        else
        {
            return handlers[ ch ];
        }
    }

    public boolean isChar( int ch, int mask )
    {
        if( ch < 0 || ch >= handlers.length )
        {
            return false;
        }
        else
        {
            return (masks[ ch ] & mask) != 0;
        }
    }

    public void setChar( int ch, Handler handler, int mask )
    {
        if( ch >= 0 && ch < handlers.length )
        {
            handlers[ ch ] = handler;
            masks[ ch ] |= mask;
        }
    }

    public void setChar( String chars, Handler handler, int mask )
    {
        if( chars != null )
        {
            for( int i = 0; i < chars.length(); i++ )
            {
                setChar( chars.charAt( i ), handler, mask );
            }
        }
    }

    public void setChar( int chFrom, int chTo, Handler handler, int mask )
    {
        if( chFrom < 0 )
        {
            chFrom = 0;
        }

        if( chTo >= handlers.length )
        {
            chTo = handlers.length - 1;
        }

        for( int ch = chFrom; ch <= chTo; ch++ )
        {
            handlers[ ch ] = handler;
            masks[ ch ] |= mask;
        }
    }

    public void setEOS( Handler handler, int mask )
    {
        eosHandler = handler;
        eosMask |= mask;
    }
}
