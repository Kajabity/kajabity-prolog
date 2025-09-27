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

public interface Tree
{

    /**
     * Add a child node to the end of the list of children in this tree node.
     *
     * @param child a LinkedTree node to add to the end of the children list for this
     *            tree node.
     */
    void add( LinkedTree child );

    /**
     * True if this is not the last sibling within the children of it's parent.
     *
     * @return
     */
    boolean hasNextSibling();

    /**
     * True if this is not the first sibling within the children of it's parent.
     *
     * @return
     */
    boolean hasPrevSibling();

    /**
     * Remove this tree node from the tree - detach it from it's parent and it's
     * siblings (if any). The children of the node and any "descendants" remain
     * intact within this node - so this node could be reattached to another
     * point in this - or another - tree.
     */
    void remove();

    /**
     * Remove all of this tree node's children.
     */
    void clearChildren();

    /**
     * Return the first child of this tree node.
     *
     * @return the first child of this tree node - or null if no children.
     */
    Tree getFirstChild();

    /**
     * Return the last child of this tree node.
     *
     * @return the last child of this tree node - or null if no children.
     */
    Tree getLastChild();

    /**
     * @return
     */
    Tree getNextSibling();

    /**
     * @return
     */
    Tree getParent();

    /**
     * @return
     */
    Tree getPrevSibling();

    /**
     * @return Returns the size of this tree node - the number of immediate
     *         children.
     */
    int getSize();

}
