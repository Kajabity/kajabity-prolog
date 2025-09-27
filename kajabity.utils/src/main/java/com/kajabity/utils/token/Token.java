/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/token/Token.java,v 1.1.1.1 2004/05/05 05:19:26 simon Exp $
 *******************************************************************************
 *  $Log: Token.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *  Revision 1.3  2003/11/19 07:56:56  simon
 *  Changed source directories
 *  Added PrologInput/OutputStreams
 *  Added Write predicate (not quite right yet).
 *
 *  Revision 1.2  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */


package com.kajabity.utils.token;

/**
 * <p>Title: SJW Test project</p>
 * <p>Description: Test and Utility project for SJW.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Simon J. Williams
 * @version 1.0
 */

public class Token
{
    private int type = 0;
    private int subType = 0;

    private StringBuffer buf = new StringBuffer();

    private char quote = (char) 0;
    private int line = 0;
    private int offset = 0;

    public Token()
    {
    }


    public void reset()
    {
        type = 0;
        subType = 0;
        quote = (char) 0;
        buf.setLength( 0 );
        line = 0;
        offset = 0;
    }


    public String toString()
    {
        return buf.toString();
    }


    public int getLine()
    {
        return line;
    }


    public void setLine( int line )
    {
        this.line = line;
    }


    public int getOffset()
    {
        return offset;
    }


    public void setOffset( int offset )
    {
        this.offset = offset;
    }


    public int getType()
    {
        return type;
    }


    public void setType( int type )
    {
        this.type = type;
    }
    /**
     * Returns the quote.
     * @return char
     */
    public char getQuote()
    {
        return quote;
    }

    /**
     * Sets the quote.
     * @param quote The quote to set
     */
    public void setQuote(char quote)
    {
        this.quote = quote;
    }

    /**
     * @return
     */
    public int getSubType()
    {
        return subType;
    }

    /**
     * @param i
     */
    public void setSubType(int i)
    {
        subType = i;
    }


	/**
	 * @param c
	 */
	public void append( char c )
	{
        buf.append( c );
	}

}
