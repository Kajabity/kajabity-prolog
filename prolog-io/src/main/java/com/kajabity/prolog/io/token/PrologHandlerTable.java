/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/PrologHandlerTable.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: PrologHandlerTable.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.5  2004/01/25 05:51:15  simon
 *  Refactoring and general fixes.
 *
 *  Revision 1.4  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.3  2003/07/03 03:47:00  simon
 *  Fixes (not complete) to List handling.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */


/**
 * Created on 18-Mar-2003
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package com.kajabity.prolog.io.token;

import com.kajabity.utils.token.HandlerTable;

/**
 * This is a singleton class which provides a (hopefully) fast way to
 * tokenise from an input stream by storing a lookup table of type codes
 * and handler references.</p>
 *
 * It is not thread safe and is only available to the token package.
 *
 * @author Simon J. Williams
 */
public class PrologHandlerTable
    extends HandlerTable
{
    //  Character classifications
    //      Digit (0-9)
    //      Symbolic atom name part (...)
    //      Alpha atom and variable name part (0-9, A-Z, a-z, _)
    //      WS
    private static final int MASK_NAME_PART   = 0x01;
    private static final int MASK_SYMBOL_PART = 0x02;
    private static final int MASK_WS          = 0x04;
    private static final int MASK_DIGIT       = 0x08;

    /** Reference to the singleton _instance once it has been created. */
    private static PrologHandlerTable _instance = null;

    /**
     * Private constructor enforces singleton behaviour.
     */
    private PrologHandlerTable()
    {
        init();
    }

    private void init()
    {
        //  Handle end of file.
        setEOS( new SingleCharHandler( Tokeniser.END ), 0 );

        //  Set everything as invalid chars first.
        setChar( 0, 255, new SingleCharHandler( Tokeniser.INVALID ), 0 );

        //  Set whitespace handler.
        //TODO: be a bit more explicit about which characters are acceptable - space, tab, CR, LF
        setChar( 0, 32, new WhitespaceHandler( MASK_WS, Tokeniser.WS ), MASK_WS );


        // |,;()[] - SingleCharHandler (...)
        setChar( '!', new SingleCharHandler( Tokeniser.CUT ), 0 );
        setChar( ',', new SingleCharHandler( Tokeniser.CONJUNCTION ), 0 );
        setChar( ';', new SingleCharHandler( Tokeniser.DISJUNCTION ), 0 );
        setChar( '|', new SingleCharHandler( Tokeniser.BAR ), 0 );
        setChar( '(', new SingleCharHandler( Tokeniser.LEFT_BRACKET ), 0 );
        setChar( ')', new SingleCharHandler( Tokeniser.RIGHT_BRACKET ), 0 );
        setChar( '[', new SingleCharHandler( Tokeniser.LEFT_LIST ), 0 );
        setChar( ']', new SingleCharHandler( Tokeniser.RIGHT_LIST ), 0 );

        // Symbols - SymbolHandler(Atom or Span Comment)
        setChar( "+-*/\\<>=:.&~?#$@^",
                 new SymbolHandler( MASK_SYMBOL_PART, Tokeniser.ATOM, Tokeniser.COMMENT, Tokeniser.SYMBOLIC ),
                 MASK_SYMBOL_PART );

        // A-Z, _  - NameHandler (Variable)
        setChar( 'A', 'Z', new NameHandler( MASK_NAME_PART, Tokeniser.VARIABLE, Tokeniser.ALPHANUM ), MASK_NAME_PART );
        setChar( '_', new NameHandler( MASK_NAME_PART, Tokeniser.VARIABLE, Tokeniser.ALPHANUM ),
                 MASK_NAME_PART );

        // a-z     - NameHandler (Atom)
        setChar( 'a', 'z', new NameHandler( MASK_NAME_PART, Tokeniser.ATOM, Tokeniser.ALPHANUM ),
                 MASK_NAME_PART );

        // 0-9     - NumberHandler (Integer, Real)
        setChar( '0', '9', new NumberHandler( MASK_DIGIT, Tokeniser.INTEGER/*, Tokeniser.REAL*/ ),
                 MASK_DIGIT | MASK_NAME_PART );

        // '       - DelimitedHandler (Atom)
        setChar( '\'', new DelimitedHandler( Tokeniser.ATOM, Tokeniser.DELIMITED ), 0 );

        // "       - String Handler (String)
        setChar( '"', new DelimitedHandler( Tokeniser.STRING, Tokeniser.DELIMITED ), 0 );

        // %       - LineCommentHandler (Line Comment)
        setChar( '%', new LineCommentHandler( Tokeniser.COMMENT ), 0 );

        // do I need a dot handler? - number, Dot or other symbol.

    }

    public static PrologHandlerTable getInstance()
    {
        if( _instance == null )
        {
            synchronized( PrologHandlerTable.class )
            {
                if( _instance == null )
                {
                    _instance = new PrologHandlerTable();
                }
            }
        }

        return _instance;
    }

}
