/*
 *******************************************************************************
 *  Copyright   :(c) 2003 Williams Technologies Limited
 *
 *  Project     :   Java Prolog
 *
 *  Created on  :   16-Jun-2003
 *
 *  $Header: /home/cvs/cvs/java-prolog/src/common/uk/co/williams_technologies/util/LinkedTree.java,v 1.4 2003/11/19 07:56:56 simon Exp $
 *******************************************************************************
 *  $Log: LinkedTree.java,v $
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
package com.kajabity.collections;

/**
 * This is a LinkedTree collection class used for holding items in a tree - any node
 * in the tree may have both siblings and children, as well as a parent.
 *
 * @todo add breadth first traversal,
 * @todo add width and depth checks (deepest child and overall width of
 *       descendants) both of which will be recursive.
 * @todo add a Trees class with equivalent methods to the Collections class
 *       (e.g. "unmodifiableTree()").
 * @todo review approach - should I use an interface or use List for children,
 *       or define a 'value' contained in each node (or none?), differentiate
 *       between leaves and branches?
 * @todo Iterators for depth/breadth searches?  Needs to be modifiable (synchronised?).
 * @author Simon J. Williams
 */
public class LinkedTree implements Tree
{
    private LinkedTree parent      = null;

    private LinkedTree nextSibling = null;

    private LinkedTree prevSibling = null;

    private Tree firstChild  = null;

    private LinkedTree lastChild   = null;

    private int  size        = 0;


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#add(com.kajabity.collections.LinkedTree)
     */
    public void add( LinkedTree child )
    {
        if( size == 0 )
        {
            firstChild = lastChild = child;
        }
        else
        {
            lastChild.nextSibling = child;
            child.prevSibling = lastChild;
            lastChild = child;
        }

        child.parent = this;
        size++;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#hasNextSibling()
     */
    public boolean hasNextSibling()
    {
        return nextSibling != null;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#hasPrevSibling()
     */
    public boolean hasPrevSibling()
    {
        return prevSibling != null;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#remove()
     */
    public void remove()
    {
        //  Remove from parent - and siblings.
        if( prevSibling != null )
        {
            prevSibling.nextSibling = nextSibling;
        }
        else
        {
            parent.firstChild = nextSibling;
        }

        if( nextSibling != null )
        {
            nextSibling.prevSibling = prevSibling;
        }
        else
        {
            parent.lastChild = prevSibling;
        }

        parent.size--;

        parent = null;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#clearChildren()
     */
    public void clearChildren()
    {
        while( firstChild != null )
        {
            firstChild.remove();
        }
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#getFirstChild()
     */
    public Tree getFirstChild()
    {
        return firstChild;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#getLastChild()
     */
    public Tree getLastChild()
    {
        return lastChild;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#getNextSibling()
     */
    public Tree getNextSibling()
    {
        return nextSibling;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#getParent()
     */
    public Tree getParent()
    {
        return parent;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#getPrevSibling()
     */
    public Tree getPrevSibling()
    {
        return prevSibling;
    }


    public Tree depthNext()
    {
        if( firstChild != null )
        {
            return firstChild;
        }

        LinkedTree next = this;
        while( next.nextSibling == null )
        {
            next = next.parent;
            if( next == null )
            {
                return null;
            }
        }

        return next.nextSibling;
    }


    /**
     * Find the previous LinkedTree element based on a depth first traversal.
     *
     * @return
     */
    public Tree depthPrev()
    {
        if( prevSibling == null )
        {
            return parent;
        }

        LinkedTree prev = prevSibling;
        while( prev.lastChild != null )
        {
            prev = prev.lastChild;
        }

        return prev;
    }


    /* (non-Javadoc)
     * @see com.kajabity.collections.Tree#getSize()
     */
    public int getSize()
    {
        return size;
    }
}
