/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/Tokeniser.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: Tokeniser.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.5  2004/02/17 07:01:54  simon
 *  Refactoring, debugging and simplification.
 *
 *  Revision 1.4  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-source - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.3  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */

/**
 * Created on 16-Mar-2003 To change this generated comment edit the template
 * variable "filecomment": Window>Preferences>Java>Templates. To enable and
 * disable the creation of file comments go to Window>Preferences>Java>Code
 * Generation.
 */
package com.kajabity.prolog.io.token;


import java.io.IOException;

import com.kajabity.utils.token.Handler;
import com.kajabity.utils.token.Token;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

/**
 * DOCUMENT ME!
 *
 * @author Simon To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class Tokeniser extends Token
{
    // Token Types returned.

    /** END of input token. */
    public static final int    END           = -1;

    /** INVALID character. */
    public static final int    INVALID       = 0;

    /** White Space. */
    public static final int    WS            = 1;

    /** An Atom - Alphanumeric, Symbolic or Quoted. */
    public static final int    ATOM          = 2;

    /** Integer constant. */
    public static final int    INTEGER       = 3;

    /** Real Constant. */

    //public static final int REAL = 4;
    /** Variable name. */
    public static final int    VARIABLE      = 5;

    /** Left bracket - for structure or expression grouping. */
    public static final int    LEFT_BRACKET  = 6;

    /** Closing right bracket. */
    public static final int    RIGHT_BRACKET = 7;

    /** Left List bracket - start of a List. */
    public static final int    LEFT_LIST     = 8;

    /** Left list bracket - end of the list. */
    public static final int    RIGHT_LIST    = 9;

    /** Bar atom - separates head of list from tail. */
    public static final int    BAR           = 10;

    /** Cut operator. */
    public static final int    CUT           = 11;

    /** DISJUNCTION operator - a semicolon. */
    public static final int    DISJUNCTION   = 12;

    /**
     * And operator - a comma used between terms, structure args and list
     * members.
     */
    public static final int    CONJUNCTION   = 13;

    /** A quoted string - will be converted to a list of characters. */
    public static final int    STRING        = 14;

    /** A comment - either a line or a C-style. */
    public static final int    COMMENT       = 15;

    /** A comment ('%') which is terminated by the end of the line. */

    //public static final int LINE_COMMENT = 14;
    /** A C style comment using '/' and '' characters. */

    //public static final int SPAN_COMMENT = 15;
    /** Special case of symbolic atom - marks the end of an assertion. */

    //public static final int DOT = 17;
    //  A set of constants for Atom (and other) sub types.
    public static final int    DELIMITED     = 1;

    public static final int    SYMBOLIC      = 2;

    public static final int    ALPHANUM      = 3;

    /**
     * Reference to the source stream - wrapped source a Buffer. This class does
     * not open or close the stream.
     */
    private TokenSource        source        = null;

    private PrologHandlerTable table         = null;


    public Tokeniser( TokenSource source )
    {
        this.source = source;
        this.table = PrologHandlerTable.getInstance();
    }


    /**
     * Read the next token from the input stream and return the type of token
     * read.
     *
     * @return the Token type of the token read.
     * @throws TokenException DOCUMENT ME!
     * @throws IOException DOCUMENT ME!
     */
    public int readToken() throws TokenException, IOException
    {
        //  Repeat until we have a real token (i.e. ignore comments).
        do
        {
            //  Erase the current contents.
            reset();

            //  Initialise the line number/offset.
            setLine( source.getLineNumber() );

            //TODO: Set the offset properly.
            setOffset( 0 );

            //  Get the token!
            int ch = source.peek();
            if( ch == -1 )
            {
                this.setType( END );
            }
            else
            {
                Handler handler = table.getHandler( ch );

                if( handler != null )
                {
                    handler.handle( source, this );
                }
                else
                {
                    throw new TokenException(
                            "Unexpected error in token at char(" + (char) ch
                                    + ")." );
                }
            }
        } while( getType() == COMMENT );

        return getType();
    }
}
