/*
 * Copyright (c) 2004-2025 Simon J. Williams
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
package com.kajabity.prolog.core.expression;


/**
 * This class represents a numeric value in a prolog expression - it can be
 * either a Long or a Double (INT or REAL) value
 *
 * @author Simon J. Williams
 */
public final class NumericConstant
    extends Term
{
    /**
     * Holds the value of this object as either an integer or real number.  It
     * is expected to contain either a Double or a Long object.
     */
    private Number value;

    /**
     * Constructor for an INT numeric constant.
     *
     * @param value a long value which will be used to construct a Long numeric
     *        constant.
     */
    public NumericConstant( long value )
    {
        this( TAG_INTEGER, new Long( value ) );
    }

    /**
     * Constructor for a REAL numeric constant.
     *
     * @param value a double value which will be used to construct a Double
     *        numeric constant.
     */
    public NumericConstant( double value )
    {
        this( TAG_REAL, new Double( value ) );
    }

    /**
     * A constructor allowing either INT or REAL to be given in the tag.
     *
     * @param tag either INT or REAL for integer or real values.
     * @param value the value - must be either Long or Double.
     */
    private NumericConstant( int tag, Number value )
    {
        super( tag, value.toString(  ), 0 );

        this.value = value;
    }

    /**
     * Compare by comparing the value.
     *
     * @see Object#equals(Object)
     */
    public boolean equals( Object obj )
    {
        if( obj instanceof NumericConstant )
        {
            return value.equals( ( (NumericConstant) obj ).value );
        }
        else
        {
            return value.equals( obj );
        }
    }

    /**
     * HashCode from value.
     *
     * @see Object#hashCode()
     */
    public int hashCode(  )
    {
        return value.hashCode(  );
    }

    /**
     * String value is the one provided by the numeric value.
     *
     * @see Object#toString()
     */
    public String toString(  )
    {
        return value.toString(  );
    }

    public boolean isReal()
    {
        return getTag() == TAG_REAL;
    }

    /**
     * @return
     */
    public long longValue()
    {
        return value.longValue();
    }

    public boolean isInteger()
    {
        return getTag() == TAG_INTEGER;
    }

    /**
     * @return
     */
    public double doubleValue()
    {
        return value.doubleValue();
    }
}
