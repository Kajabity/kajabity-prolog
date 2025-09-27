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

package com.kajabity.prolog.io.parse;

import java.io.IOException;

import junit.framework.TestCase;

import com.kajabity.prolog.core.environment.Associativity;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.environment.operator.SimpleOperator;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.io.format.PrologFormat;
import com.kajabity.prolog.io.format.SimplePrologFormat;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.StringTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;

/**
 * Test the Prolog Parser classes.
 *
 * @author <a href="simon@willaims-technologies.co.uk">Simon J. Williams</a>
 */
public class PrologParserTest extends TestCase
{
    private final static String lines[][] =
                     {
                     { "", "YNx", "" },
                     { "% comment", "YNx", "" },
                     { "  /* comment */ ", "YNx", "" },
                     { "atom.", "YYx", "atom." },
                     { "functor( arg1, arg2 ).", "YYx", "functor( arg1, arg2 )." },
                     { "a :- b.", "YYx", ":-( a, b )." },
                     { "a :- b,c.", "YYx", ":-( a, (b, c) )." },
                     { "a :- b,c(d,e).", "YYx", ":-( a, (b, c( d, e )) )." },
                     { "a( x ) :- b( y ), c, d(z,m).", "YYx", ":-( a( x ), (b( y ), c, d( z, m )) )." },
                     { "a + b + c.", "YYx", "+( +( a, b ), c )." },
                     { "a + b * c + d.", "YYx", "+( +( a, *( b, c ) ), d )." },
                     { "3.", "YYx", "3." },
                     { "3.2.", "YYx", "3.2." },
                     { "12-6-3.", "YYx", "-( -( 12, 6 ), 3 )." },
                     { "12^6^3.", "YYx", "^( 12, ^( 6, 3 ) )." },
                     { "-a * b", "YNx", "*( -( a ), b )." },
                     { "*( -( a ), b )", "YNx", "*( -( a ), b )." },
                     { "[].", "YYx", ".." },
                     { "[ a, b, c ].", "YYx", ".( a, .( b, .( c, . ) ) )." },
                     { "[ a, b | X ].", "YYx", ".( a, .( b, X ) )." },
                     { ".( a, [] ).", "YYx", ".( a, . )." }, };

    public void testParse() throws TokenException, IOException, ParseException, PrologException
    {
        Database database = new Database( System.in, System.out, System.err );
        database.add( new SimpleOperator( ":-", Associativity.xfx, 1200 ) );
        database.add( new SimpleOperator( "is", Associativity.xfx, 700 ) );
        database.add( new SimpleOperator( "+", Associativity.yfx, 500 ) );
        database.add( new SimpleOperator( "-", Associativity.yfx, 500 ) );
        database.add( new SimpleOperator( "-", Associativity.fy, 200 ) );
        database.add( new SimpleOperator( "^", Associativity.xfy, 200 ) );
        database.add( new SimpleOperator( "*", Associativity.yfx, 400 ) );

        PrologParser parser = new PrologParser( database );
        PrologFormat formatter = new SimplePrologFormat();

        TokenSource source;
        for( int i = 0; i < lines.length; i++ )
        {
            System.out.println( "Parse \"" + lines[i][0] + "\" ==> " );

            source = new StringTokenSource( lines[i][0] );
            Tokeniser tokeniser = new Tokeniser( source );

            parser.parse( tokeniser );
            Expression expr = parser.getExpression();

            /*
             * System.out.println( " { \"" + lines[ i ][ 0 ] + "\", \"" +
             * (parser.isEmpty() ? "Y" : "N") + (parser.isFinished() ? "Y" :
             * "N") + "x\", \""
             */

            System.out.println( "\t(empty " + parser.isEmpty() + ", finished " + parser.isFinished() + ") ==> "
                    + formatter.format( expr ) + "\n\n" );

        }
    }

    public void testSkip()
    {
        // fail( "Not tested." );
    }
}
