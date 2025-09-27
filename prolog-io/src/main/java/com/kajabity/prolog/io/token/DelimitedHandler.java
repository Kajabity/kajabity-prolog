/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/token/DelimitedHandler.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: DelimitedHandler.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.4  2004/01/25 05:51:15  simon
 *  Refactoring and general fixes.
 *
 *  Revision 1.3  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */


package com.kajabity.prolog.io.token;

import java.io.IOException;

import com.kajabity.utils.token.Handler;
import com.kajabity.utils.token.Token;
import com.kajabity.utils.token.TokenSource;



/**
Character Escaping
In quoted atoms, double-quote delimited lists or base 0 notation, escape sequences can be used to denote characters. This is used to avoid ambiguity and to enter unusual characters. ICP has the following escape sequences:


\a
alarm/bell (ASCII 7)
\b
backspace (ASCII 8)
\t
tab (ASCII 9)
\n
newline (ASCII 10)
\v
vertical tab (ASCII 11)
\f
formfeed (ASCII 12)
\r
carriage return (ASCII 13)
\e
escape (ASCII 27)
\s
space (ASCII 32)
\d
delete (ASCII 127)
\<octal string>
the character with ASCII code <octal string> base 8. e.g. \007 is the bell character and \040 is the space character (ASCII 32).
\^<control char>
the character whose ASCII code is the <control char> mod 32. e.g. \^P is CTL-P.
\<layout char>
no character, where <layout char> is a character with ASCII code =< 32 or >= 127. This is most useful when splitting atoms over two lines where the ignored newline character is preceded by a backslash.
\c
no character, also all characters up to, but not including, the next non-layout character are ignored.
\<other>
the character <other>, where <other> is any character not defined above.
 * <p>Title: SJW Test project</p>
 * <p>Description: Test and Utility project for SJW.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Simon J. Williams
 * @version 1.0
 */

public class DelimitedHandler
    extends Handler
{
    private int type;
    private int subtype;

    public DelimitedHandler( int type, int subtype )
    {
        this.type = type;
        this.subtype = subtype;
    }


    /**
     * Next char from the stream is the delimiter.
     * @param in
     * @param token
     * @return
     */
    public void handle( TokenSource in, Token token )
        throws UnterminatedTokenException, IOException
    {
        //  Token has been reset and the line number and offset added.
        token.setType( type );
        token.setSubType( subtype );

        // Get and skip the delimiter.
        int delim = in.read();

        int ch = in.read();
        while( ch != EOF && ch != delim )
        {
            token.append( ( char ) ch );
            /** @todo: Handle escapes using '\'. */
            ch = in.read();
        }

        if( ch == EOF )
        {
            // End of stream without delimiter.
            throw new UnterminatedTokenException(
                "Reached End of input when searching for closing delimiter: " +
                delim );
        }

        token.setQuote( (char) delim );
    }

}
