/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_IO
 *
 *  Created     :   Apr 12, 2004 by Simon J. Williams
 *
 *  $Header: /home/cvs/cvs/Prolog/IO/src/uk/co/williams_technologies/prolog/parse/PrologParser.java,v 1.2 2004/07/01 05:31:53 simon Exp $
 *******************************************************************************
 *  $Log: PrologParser.java,v $
 *  Revision 1.2  2004/07/01 05:31:53  simon
 *  Major update after many changes.
 *
 *  Revision 1.1.1.1  2004/05/05 05:19:26  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.io.parse;

import java.io.IOException;
import java.util.Stack;

import com.kajabity.prolog.core.environment.PrologContext;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.Operator;
import com.kajabity.prolog.core.environment.operator.PrologOperatorException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.io.parse.action.Action;
import com.kajabity.prolog.io.parse.action.FinishAction;
import com.kajabity.prolog.io.parse.action.FinishWithIntegerAction;
import com.kajabity.prolog.io.parse.action.HoldIngeterAction;
import com.kajabity.prolog.io.parse.action.PopBracketsAction;
import com.kajabity.prolog.io.parse.action.PopListAction;
import com.kajabity.prolog.io.parse.action.PushAtomAction;
import com.kajabity.prolog.io.parse.action.PushBarAction;
import com.kajabity.prolog.io.parse.action.PushBracketAction;
import com.kajabity.prolog.io.parse.action.PushConjunctionAction;
import com.kajabity.prolog.io.parse.action.PushDisjunctionAction;
import com.kajabity.prolog.io.parse.action.PushIntegerAction;
import com.kajabity.prolog.io.parse.action.PushListAction;
import com.kajabity.prolog.io.parse.action.PushNilAction;
import com.kajabity.prolog.io.parse.action.PushOperatorAction;
import com.kajabity.prolog.io.parse.action.PushRealAction;
import com.kajabity.prolog.io.parse.action.PushStringAction;
import com.kajabity.prolog.io.parse.action.PushTupleAction;
import com.kajabity.prolog.io.parse.action.PushVariableAction;
import com.kajabity.prolog.io.parse.match.DotMatcher;
import com.kajabity.prolog.io.parse.match.InfixOperatorMatcher;
import com.kajabity.prolog.io.parse.match.Matcher;
import com.kajabity.prolog.io.parse.match.PostfixOperatorMatcher;
import com.kajabity.prolog.io.parse.match.PrefixOperatorMatcher;
import com.kajabity.prolog.io.parse.match.TokenTypeMatcher;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.TokenException;

/**
 * DOCUMENT ME!
 *
 * @author Simon To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class PrologParser
{
    /** Match if token is an atom and value is ".". */
    private static Matcher      matchDot             = new DotMatcher();

    /** Match where token is an atom and a matching operator can be found. */
    private static Matcher      matchPrefixOperator  = new PrefixOperatorMatcher();

    /** Match where token is an atom and a matching operator can be found. */
    private static Matcher      matchInfixOperator   = new InfixOperatorMatcher();

    /** Match where token is an atom and a matching operator can be found. */
    private static Matcher      matchPostifxOperator = new PostfixOperatorMatcher();

    /** Match the token type to the param. */
    private static Matcher      matchType            = new TokenTypeMatcher();

    // ========================================================================
    private static Action       finish               = new FinishAction();

    private static Action       finishWithInteger    = new FinishWithIntegerAction();

    private static Action       holdInteger          = new HoldIngeterAction();

    private static Action       popBrackets          = new PopBracketsAction();

    private static Action       popList              = new PopListAction();

    private static Action       pushAtom             = new PushAtomAction();

    private static Action       pushTuple            = new PushTupleAction();

    private static Action       pushBar              = new PushBarAction();

    private static Action       pushBracket          = new PushBracketAction();

    private static Action       pushConjunction      = new PushConjunctionAction();

    private static Action       pushDisjunction      = new PushDisjunctionAction();

    private static Action       pushInteger          = new PushIntegerAction();

    private static Action       pushList             = new PushListAction();

    private static Action       pushNIL              = new PushNilAction();

    private static Action       pushOperator         = new PushOperatorAction();

    private static Action       pushReal             = new PushRealAction();

    private static Action       pushString           = new PushStringAction();

    /** Push the token onto the stack as a Variable. */
    private static Action       pushVariable         = new PushVariableAction();

    // ========================================================================
    private static final int    STATE_X              = 0;

    private static final int    STATE_A              = 1;

    private static final int    STATE_B              = 2;

    private static final int    STATE_C              = 3;

    private static final int    STATE_D              = 4;

    private static final int    STATE_E              = 5;

    private static final int    STATE_F              = 6;

    // ========================================================================
    private static final Tx[]   stateA               =
                                                     { new Tx( matchPrefixOperator, 0, pushOperator, STATE_A ),
            new Tx( matchType, Tokeniser.ATOM, pushAtom, STATE_B ), new Tx( matchType, Tokeniser.COMMENT, null, STATE_A ),
            new Tx( matchType, Tokeniser.LEFT_BRACKET, pushBracket, STATE_A ),
            new Tx( matchType, Tokeniser.LEFT_LIST, null, STATE_F ), new Tx( matchType, Tokeniser.WS, null, STATE_A ),
            new Tx( matchType, Tokeniser.VARIABLE, pushVariable, STATE_C ), new Tx( matchType, Tokeniser.CUT, pushAtom, STATE_C ),
            new Tx( matchType, Tokeniser.STRING, pushString, STATE_C ),
            new Tx( matchType, Tokeniser.INTEGER, holdInteger, STATE_D ), new Tx( matchType, Tokeniser.END, null, STATE_X ) };

    private static final Tx[]   stateB               =
                                                     { new Tx( matchType, Tokeniser.LEFT_BRACKET, pushTuple, STATE_A ),
            new Tx( null, 0, null, STATE_C )        };

    private static final Tx[]   stateC               =
                                                     { new Tx( matchInfixOperator, 0, pushOperator, STATE_A ),
            new Tx( matchType, Tokeniser.BAR, pushBar, STATE_A ),
            new Tx( matchType, Tokeniser.CONJUNCTION, pushConjunction, STATE_A ),
            new Tx( matchType, Tokeniser.DISJUNCTION, pushDisjunction, STATE_A ),
            new Tx( matchPostifxOperator, 0, pushOperator, STATE_C ), new Tx( matchType, Tokeniser.COMMENT, null, STATE_C ),
            new Tx( matchType, Tokeniser.RIGHT_LIST, popList, STATE_C ),
            new Tx( matchType, Tokeniser.RIGHT_BRACKET, popBrackets, STATE_C ), new Tx( matchType, Tokeniser.WS, null, STATE_C ),
            new Tx( matchDot, 0, finish, STATE_X ), new Tx( matchType, Tokeniser.END, null, STATE_X ) };

    private static final Tx[]   stateD               =
                                                     { new Tx( matchDot, 0, null, STATE_E ), new Tx( null, 0, pushInteger, STATE_C ) };

    private static final Tx[]   stateE               =
                                                     { new Tx( matchType, Tokeniser.INTEGER, pushReal, STATE_C ),
            new Tx( matchType, Tokeniser.WS, finishWithInteger, STATE_X ),
            new Tx( matchType, Tokeniser.COMMENT, finishWithInteger, STATE_X ),
            new Tx( matchType, Tokeniser.END, finishWithInteger, STATE_X ) };

    private static final Tx[]   stateF               =
                                                     { new Tx( matchType, Tokeniser.RIGHT_LIST, pushNIL, STATE_C ),
            new Tx( null, 0, pushList, STATE_A )    };

    // ========================================================================
    /** array of states matching the state int with the actual state tx's */
    private static final Tx[][] states               =
                                                     { null, stateA, stateB, stateC, stateD, stateE, stateF };

    // ========================================================================
    private Stack<Expression>               values               = new Stack<Expression>();

    private Stack<Operator>               operators            = new Stack<Operator>();

    private boolean             finished             = false;

    private Tokeniser           tokeniser            = null;

    private PrologContext       database             = null;

    private Operator            operator             = null;

    private String              heldToken            = null;

    private int                 state                = STATE_A;

    private int                 lastState            = STATE_X;

    /**
     * Construct a PrologParser with a database to use for identifying
     * operators.
     *
     * @param database
     *            A Prolog database of terms, operators, etc.
     */
    public PrologParser( PrologContext database )
    {
        this.database = database;
    }

    /**
     * Parse prolog tokens into a statement.
     *
     * @param tokeniser
     *            converts an input stream into tokens for the Parser.
     * @return true if a complete statement has been parsed.
     * @throws TokenException
     * @throws IOException
     * @throws ParseException
     * @throws PrologException
     */
    public boolean parse( Tokeniser tokeniser ) throws TokenException, IOException, ParseException, PrologException
    {
        this.tokeniser = tokeniser;

        if( finished || isEmpty() )
        {
            reset();
        }
        else
        {
            // Continue where we left off...
            state = lastState;
        }

        while( (state != STATE_X) )
        {
            tokeniser.readToken();

            // System.out.println( "\tState " + (char) ('A' + state - 1) + " >>
            // " + tokeniser.getType() + " \"" + tokeniser + "\"" );

            boolean matched = false;

            for( int tx = 0; tx < states[state].length; tx++ )
            {
                Tx trans = states[state][tx];
                if( trans.matches( tokeniser, this ) )
                {
                    trans.doAction( tokeniser, this );

                    lastState = state;
                    state = trans.getNextState();

                    if( trans.isLamda() )
                    {
                        // System.out.println( "\t\tMatched Tx " + tx + ": LAMDA
                        // to state " + (char) ('A' + state - 1) );
                        tx = -1;
                    }
                    else
                    {
                        // System.out.println( "\t\tMatched Tx " + tx );
                        matched = true;
                        break;
                    }
                }
            }

            if( !matched )
            {
                throw new ParseException( "Failed to match token: \"" + tokeniser + "\"" );
            }
        }

        return !isEmpty();
    }

    public void pushOperator( Operator operator, boolean force ) throws PrologOperatorException
    {
        if( !force )
        {
            while( !operators.isEmpty() && (operator.compareTo( operators.peek() ) >= 0) )
            {
                pop();
            }
        }
        operators.push( operator );
    }

    public void pushValue( Term value )
    {
        values.push( value );
    }

    private void pop() throws PrologOperatorException
    {
        Operator op = operators.pop();
        Expression expr = op.apply( values );
        values.push( expr );
        // System.out.println( "popped " + op );
    }

    public void popTill( String name ) throws ParseException, PrologOperatorException
    {
        while( !operators.isEmpty() )
        {
            Operator op = operators.pop();
            if( op.getName().equals( name ) )
            {
                Expression expr = op.apply( values );
                values.push( expr );
                // System.out.println( "Popped (found) " + op );
                return;
            }
            else if( op.getName().equals( "(" ) || op.getName().equals( "[" ) )
            {
                throw new ParseException( "Found un-terminated " + op.getName() + " in expression." );
            }

            Expression expr = op.apply( values );
            values.push( expr );

            // System.out.println( "Popped " + op );
        }

        // If we are here - we failed to find the matching bracket.
        throw new ParseException( "No matching " + name + " for terminator." );
    }

    public Expression getExpression() throws ParseException, PrologOperatorException
    {
        while( !operators.isEmpty() )
        {
            Operator op = operators.pop();
            if( op.getName().equals( "(" ) || op.getName().equals( "[" ) )
            {
                throw new ParseException( "Found un-terminated " + op.getName() + " in expression." );
            }
            Expression expr = op.apply( values );
            values.push( expr );
        }

        if( values.size() > 1 )
        {
            throw new ParseException( "Invalid number of values left in expression stack." );
        }

        if( !values.isEmpty() )
        {
            return values.pop();
        }

        return null;
    }

    /**
     * A term is considered finished when the finial dot is read.
     *
     * @return
     */
    public boolean isFinished()
    {
        return finished;
    }

    public void reset()
    {
        state = STATE_A;
        finished = false;
        values.clear();
        operators.clear();
        operator = null;
        heldToken = null;
    }

    public void skip( Tokeniser tokeniser )
    {
        // TODO: implement PrologParser.skip()
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isEmpty()
    {
        return values.isEmpty() && operators.isEmpty();
    }

    /**
     * @return Returns the tokeniser.
     */
    public Tokeniser getTokeniser()
    {
        return tokeniser;
    }

    /**
     * @param finished
     *            The finished to set.
     */
    public void setFinished( boolean finished )
    {
        this.finished = finished;
    }

    /**
     * @return Returns the heldToken.
     */
    public String getHeldToken()
    {
        return heldToken;
    }

    /**
     * @param heldToken
     *            The heldToken to set.
     */
    public void setHeldToken( String heldToken )
    {
        this.heldToken = heldToken;
    }

    /**
     * @return Returns the operator.
     */
    public Operator getOperator()
    {
        return operator;
    }

    /**
     * @param operator
     *            The operator to set.
     */
    public void setOperator( Operator operator )
    {
        this.operator = operator;
    }

    /**
     * @return Returns the database.
     */
    public PrologContext getDatabase()
    {
        return database;
    }
}
