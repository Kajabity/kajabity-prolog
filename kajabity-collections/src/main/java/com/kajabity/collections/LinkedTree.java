/*
 * Copyright (c) 2003-2025 Simon J. Williams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
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
