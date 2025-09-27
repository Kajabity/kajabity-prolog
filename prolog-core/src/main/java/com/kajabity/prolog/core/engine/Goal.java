/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 * Created on 16-Mar-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/engine/uk/co/williams_technologies/prolog/solver/Goal.java,v 1.7 2004/05/05 05:28:38 simon Exp $
 *******************************************************************************
 *  $Log: Goal.java,v $
 *  Revision 1.7  2004/05/05 05:28:38  simon
 *  Lots of changes...
 *
 *  Revision 1.6  2003/11/20 05:14:29  simon
 *  Changes to implement write( Term ) built-in - added PrologOutputStream,
 *  also refactored to src/* folders.
 *
 *  Revision 1.5  2003/07/07 03:39:21  simon
 *  List handling improved.  First built-ins working.  Cut works!
 *
 *  Revision 1.4  2003/06/28 05:38:35  simon
 *  UPdate during adding of 'op' builtin.
 *
 *******************************************************************************
 */

package com.kajabity.prolog.core.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.kajabity.collections.LinkedTree;
import com.kajabity.prolog.core.environment.IConsortIterator;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Expressions;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.core.expression.TermList;
import com.kajabity.prolog.core.expression.Variable;

/**
 * This class represents a single term in a problem search tree -i.e. during
 * solving, each individual term is managed by an instance of this Goal class.
 *
 * @author Simon J. Williams
 */
public class Goal extends LinkedTree
{
    /**
     * A Goal contains a single predicate (Term) whose truth is to be tested.
     */
    private Term                  term            = null;

    /**
     * During solving, a goal term is matched against a set of Horn clauses with
     * the same tuple at the head (the Consorts) and we iterate through them to
     * search for a solution.
     */
    private IConsortIterator      consortIterator = null;

    /**
     * Each goal may contain some variables - we note them so we can show their
     * values later.
     */
    private Map<String, Variable> variables       = null;

    /**
     * When a goal is unified with one of it's consorts, a list of substitutions
     * is produced showing where a variable is instantiated with a term from the
     * matched expression.
     */
    private List<Variable>        substitutions   = null;

    /**
     * Mark a goal as being 'Cut' - no further consorts should be tried.
     */
    private boolean               cut             = false;

    /**
     * Construct a Goal for solving a given Term.
     *
     * @param term
     *            the term to be tested for a solution.
     */
    public Goal( Term term )
    {
        this.term = term;
    }

    /**
     * Accessor for the variables defined in the Term.
     *
     * @return an unmodifiable {@link Map} of the {@link Variable}s in the Term keyed by their {@link String} name.
     */
    public Map<String, Variable> getVariables()
    {
        return Collections.unmodifiableMap( variables );
    }

    /**
     * Set the consortIterator.
     *
     * @param consortIterator
     */
    public void setConsortIterator( IConsortIterator consortIterator )
    {
        this.consortIterator = consortIterator;
    }

    /**
     * Accessor for the Term for this goal.
     *
     * @return
     */
    public Term getTerm()
    {
        return term;
    }

    /**
     * Accessor for the current substitutions for the goal.
     *
     * @return
     */
    public List<Variable> getSubstitutions()
    {
        return substitutions;
    }

    /**
     * DOCUMENT ME!
     *
     * @param list
     */
    public void setSubstitutions( List<Variable> list )
    {
        substitutions = list;
    }

    /**
     * *
     */
    public void reset()
    {
        Expressions.undoSubstitutions( substitutions );
        substitutions = null;
        variables = null;

        // erase children (recursive) and their variables
        for( Goal child = (Goal) getFirstChild(); child != null; child = (Goal) getFirstChild() )
        {
            child.reset();
            child.remove();
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return
     */
    public boolean isCut()
    {
        return cut;
    }

    /**
     * mark this goal - and all previous siblings - as cut.
     */
    public void cut()
    {
        // Stop this and it's previous siblings from backtracking.
        Goal sibling = this;
        while( sibling != null )
        {
            sibling.cut = true;
            sibling = (Goal) sibling.getPrevSibling();
        }

        // Prevent the parent from backtracking.
        if( getParent() != null )
        {
            ((Goal) getParent()).cut = true;
        }
    }

    /**
     * Create a set of child goals (from the tail of a clause) to be the
     * children of this goal. The expression passed is recursively expanded to
     * go through TermLists and inistantiated variables to get a list of
     * children which are all Terms.
     *
     * @param expr
     *            A term, variable or list of terms which form the right hand
     *            side of the clause we have matched for the current goal.
     */
    public void setChildren( Expression expr )
    {
        // We are going to iteratively expand all of the children until they are
        // just terms - no term lists.
        List<Expression> terms = new ArrayList<Expression>();
        terms.add( expr );

        do
        {
            Expression child = terms.remove( 0 );
            child = child.getFinalInstantiation();

            if( child.isTerm() )
            {
                // Add this to the list of goals.
                this.add( new Goal( (Term) child ) );
            }
            else
            {
                // Add these to the list of expressions still to be handled.
                terms.addAll( 0, ((TermList) child).getTerms() );
            }
        } while( !terms.isEmpty() );
    }

    /**
     * @return Returns the consortIterator.
     */
    public IConsortIterator getConsortIterator()
    {
        return consortIterator;
    }

    public void setVariables( Map<String, Variable> variables )
    {
        this.variables = variables;
    }
}
