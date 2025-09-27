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
package com.kajabity.prolog.core.environment;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is a read-only representation of the set of possible direction
 * values for a prolog operator.<br/
 * >  An operator can be prefix (it comes immediately before a single operand -
 * e.g. "-a"), infix (it is placed between two operands - e.g. "a + b")  or
 * postfix (placed after the operand  - e.g. "a++").<br/
 * >    They can also bind tighter with the left or right operand - left being
 * left or right associative.  Assignments are right associative to ensure a
 * sequence of them is performed right to left - a plus is left associative to
 * work left to right.<br/
 * >  It is implemented as a two valued enumeration - there is a finite set of
 * possible values.
 *
 * @author Simon J. Williams
 */
public final class Associativity
{
    /** Non-associative postfix. */
    public static final Associativity fx =
        new Associativity( "fx", Position.PREFIX, Direction.NONE );

    /** Right-associative postfix. */
    public static final Associativity fy =
        new Associativity( "fy", Position.PREFIX, Direction.RIGHT );

    /** Non-associative infix. */
    public static final Associativity xfx =
        new Associativity( "xfx", Position.INFIX, Direction.NONE );

    /** Right-associative infix. */
    public static final Associativity xfy =
        new Associativity( "xfy", Position.INFIX, Direction.RIGHT );

    /** Left-associative infix. */
    public static final Associativity yfx =
        new Associativity( "yfx", Position.INFIX, Direction.LEFT );

    /** Non-associative prefix. */
    public static final Associativity xf =
        new Associativity( "xf", Position.POSTFIX, Direction.NONE );

    /** Left-associative prefix. */
    public static final Associativity yf =
        new Associativity( "yf", Position.POSTFIX, Direction.LEFT );


    private final static Map<String, Associativity>          _instances = new HashMap<>(  );

    static
    {
        _instances.put( fx.name, fx );
        _instances.put( fy.name, fy );
        _instances.put( xfx.name, xfx );
        _instances.put( xfy.name, xfy );
        _instances.put( yfx.name, yfx );
        _instances.put( xf.name, xf );
        _instances.put( yf.name, yf );
    }

    /** The name of the associativity - used to find it and for printing. */
    private String name;

    /** Position of the operator is either prefix, infix or postfix. */
    private final Position position;

    /** Operators may be none, left or right associative . */
    private final Direction direction;

    /**
     * The constructor is private because we provide all of the valid values as
     * public constants!  This means we don't have to check the parameters.
     *
     * @param name DOCUMENT ME!
     * @param position is the position of the operator - PREFIX, INFIX or
     *        POSTFIX.
     * @param associativity is the direction of the operator - LEFT, RIGHT or
     *        NONE.
     */
    private Associativity( String name, Position position, Direction direction )
    {
        this.name          = name;
        this.position      = position;
        this.direction     = direction;
    }

    /**
     * Returns the direction.
     *
     * @return int
     */
    public Direction getDirection(  )
    {
        return direction;
    }

    /**
     * Returns the position.
     *
     * @return int
     */
    public Position getPosition(  )
    {
        return position;
    }

    /**
     * Method isInfix.
     *
     * @return boolean
     */
    public boolean isPrefix(  )
    {
        return position == Position.PREFIX;
    }

    /**
     * Method isInfix.
     *
     * @return boolean
     */
    public boolean isInfix(  )
    {
        return position == Position.INFIX;
    }

    /**
     * Method isInfix.
     *
     * @return boolean
     */
    public boolean isPostfix(  )
    {
        return position == Position.POSTFIX;
    }

    public final static Associativity find( String name )
    {
        return _instances.get( name );
    }

    /**
     * return the name as the String value.
     * @return the name as the String representation.
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return name;
    }

    //  ========================================================================

    public final static class Position
    {
        public static final Position PREFIX  = new Position( "PREFIX" );
        public static final Position INFIX   = new Position( "INFIX" );
        public static final Position POSTFIX = new Position( "POSTFIX" );

        private final String               name;

        private Position( String name )
        {
            this.name = name;
        }


		/**
         * return the name as the String value.
         * @return the name as the String representation.
		 * @see Object#toString()
		 */
        @Override
		public String toString()
		{
			return name;
		}
    }

    //  ========================================================================

    /**
     * Compare directions so they can be sorted.
     * @author Simon J. Williams
     *
     */
    public final static class Direction
        implements Comparable<Direction>
    {
        public static final Direction LEFT  = new Direction( "LEFT", -1 );
        public static final Direction NONE   = new Direction( "NONE", 0 );
        public static final Direction RIGHT = new Direction( "RIGHT", 1 );

        private final String name;
        private final int value;

        private Direction( String name, int value )
        {
            this.name = name;
            this.value = value;
        }

        /**
         * return the name as the String value.
         * @return the name as the String representation.
         * @see Object#toString()
         */
        @Override
        public String toString()
        {
            return name;
        }

		@Override
		public int compareTo(Direction otherDirection ) {
			return this.value - otherDirection.value;
		}
    }
}
