/*
 *******************************************************************************
 *  Copyright   :   (c)2004 Williams Technologies Limited
 *
 *  Project     :   Prolog_Term
 *
 *  Created     :   Mar 16, 2004 by Simon
 *
 *  $Header: /home/cvs/cvs/Prolog/Term/src/uk/co/williams_technologies/prolog/term/Expressions.java,v 1.1.1.1 2004/05/05 05:19:27 simon Exp $
 *******************************************************************************
 *  $Log: Expressions.java,v $
 *  Revision 1.1.1.1  2004/05/05 05:19:27  simon
 *  Import into CVS for sharing across computers.
 *
 *******************************************************************************
 */
package com.kajabity.prolog.core.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This is a utility class providing methods to manipulate Terms and expressions
 * with terms.
 *
 * @author Simon J. Williams
 */
public class Expressions
{
    private final static Logger logger              = Logger.getLogger( Expressions.class );

    public final static List<Variable>    EMPTY_SUBSTITUTIONS = new ArrayList<Variable>();

    /**
     * The NIL atom (also shown as '.') represents false or empty in Prolog. Do
     * not confuse with the end of clause full stop.
     */
    public final static Atom    NIL                 = Atom.find( "." );

    /**
     * Undo a List of substitutions. When substitutions are made during
     * unification, a Variable has an instance value assigned to it and is then
     * appended to the list. This method starts and the end of the list, removes
     * a variable and sets the instance to null (undoing it).
     *
     * @param substitutions
     *            a List of Variables to be uninstantiated.
     */
    public static void undoSubstitutions( List<Variable> substitutions )
    {
        logger.debug( "undoSubstitutions: " + substitutions );

        if( substitutions != null )
        {
            for( int index = substitutions.size() - 1; index >= 0; index-- )
            {
                Variable var = substitutions.remove( index );
                logger.debug( "\t" + var.getName() + " = " + var.getFinalInstantiation() );

                var.setInstance( null );
            }
        }
    }

    /**
     * Unify two prolog expressions and return a list of substitutions - or null
     * if unification fails. If successful, the result will be a list of zero or
     * more Variables which became instantiated during the unification process.
     *
     * @param expr1
     *            is a Term or List of Terms
     * @param expr2
     *            is a Term or List of Terms.
     * @return a list of substitutions or null for a failed unify.
     */
    public static List<Variable> unify( Expression expr1, Expression expr2 )
    {
        logger.debug( "unify <<" + expr1 + ">> and <<" + expr2 + ">>" );

        Variable var;

        List<Variable> substitutions = new ArrayList<Variable>();
        List<Expression> leftTerms = new ArrayList<Expression>();
        leftTerms.add( expr1 );

        List<Expression> rightTerms = new ArrayList<Expression>();
        rightTerms.add( expr2 );

        do
        {
            // Pop the two terms
            Expression left = leftTerms.remove( 0 );
            left = left.getFinalInstantiation();

            Expression right = rightTerms.remove( 0 );
            right = right.getFinalInstantiation();

            logger.debug( "\tcomparing <<" + left + ">> and <<" + right + ">>" );

            if( left.isTerm() )
            {
                if( ((Term) left).getTag() == Term.TAG_VARIABLE )
                {
                    var = (Variable) left;
                    // if( !var.isAnonymous() )
                    {
                        if( right.containsVariable( var ) )
                        {
                            // fail
                            logger.debug( "\tfailed: Left var contained in right expression." );
                            undoSubstitutions( substitutions );

                            return null;
                        }

                        // Add subs Left becomes Right
                        var.setInstance( right );
                        substitutions.add( var );
                    }
                }
                else if( right.isTerm() )
                {
                    if( ((Term) right).getTag() == Term.TAG_VARIABLE )
                    {
                        var = (Variable) right;
                        // if( !var.isAnonymous() )
                        {
                            if( left.containsVariable( var ) )
                            {
                                // fail
                                logger.debug( "\tfailed: Right var contained in left expression." );
                                undoSubstitutions( substitutions );

                                return null;
                            }
                            // Add subs Right becomes Left
                            var.setInstance( left );
                            substitutions.add( var );
                        }
                    }
                    else if( ((Term) left).getTag() == ((Term) right).getTag() )
                    {
                        if( ((Term) left).getTag() == Term.TAG_TUPLE || ((Term) left).getTag() == Term.TAG_LIST )
                        {
                            if( ((Tuple) left).getArity() == ((Tuple) right).getArity() )
                            {
                                // Two tuples - append the functor and args of
                                // each.
                                leftTerms.add( ((Tuple) left).getFunctor() );
                                leftTerms.add( ((Tuple) left).getArgs() );
                                rightTerms.add( ((Tuple) right).getFunctor() );
                                rightTerms.add( ((Tuple) right).getArgs() );
                            }
                            else
                            {
                                // fail
                                logger.debug( "\tfailed: Left and Right are different arity." );
                                undoSubstitutions( substitutions );

                                return null;
                            }
                        }
                        else if( !left.equals( right ) )
                        {
                            // fail
                            logger.debug( "\tfailed: Left and Right are different constants." );
                            undoSubstitutions( substitutions );

                            return null;
                        }
                    }
                    else
                    {
                        // fail
                        logger.debug( "\tfailed: Left and Right are different types." );
                        undoSubstitutions( substitutions );

                        return null;
                    }
                }
                else
                {
                    // fail
                    logger.debug( "\tfailed: Left is term, right isn't." );
                    undoSubstitutions( substitutions );

                    return null;
                }
            }
            else if( right.isTerm() )
            {
                if( ((Term) right).getTag() == Term.TAG_VARIABLE )
                {
                    var = (Variable) right;
                    // if( !var.isAnonymous() )
                    {
                        if( left.containsVariable( var ) )
                        {
                            // fail
                            logger.debug( "\tfailed: Right var contained in left expression." );
                            undoSubstitutions( substitutions );

                            return null;
                        }
                        // Add subs Right becomes Left
                        var.setInstance( left );
                        substitutions.add( var );
                    }
                }
                else
                {
                    // fail
                    logger.debug( "\tfailed: Right is term, Left isn't." );
                    undoSubstitutions( substitutions );
                    return null;
                }
            }
            else
            {
                // Both TermList - append.
                if( ((TermList) left).size() == ((TermList) right).size() )
                {
                    leftTerms.addAll( ((TermList) left).getTerms() );
                    rightTerms.addAll( ((TermList) right).getTerms() );
                }
                else
                {
                    // fail
                    logger.debug( "\tfailed: Right and left are different lengths." );
                    undoSubstitutions( substitutions );
                    return null;
                }
            }
        } while( !leftTerms.isEmpty() );

        if( logger.isDebugEnabled() )
        {
            logger.debug( "\tSUCCESS: with " + substitutions.size() + " substitutions." );

            Iterator<Variable> it = substitutions.iterator();

            while( it.hasNext() )
            {
                var = it.next();
                logger.debug( "\t\t<<" + var.getName() + ">> is <<" + var.getInstance() + ">>" );
            }
        }

        return substitutions;
    }
}
