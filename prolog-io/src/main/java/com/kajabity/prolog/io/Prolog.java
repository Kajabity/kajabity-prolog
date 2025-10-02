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
package com.kajabity.prolog.io;

import com.kajabity.prolog.builtin.InitialiseBuiltins;
import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.Processor;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.io.parse.ParseException;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.InputStreamTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class description here....
 *
 * @author Simon
 */
public class Prolog {
    private final static Logger logger = Logger.getLogger(Prolog.class);

    /**
     * Create a new database with some Core build-ins.
     *
     * @param in  The default input stream - e.g. System.in.
     * @param out The default output stream - e.g. System.out.
     * @param err The default error output stream - e.g. System.err.
     * @return Returns an initialised database.
     */
    public static Database createDatabase(InputStream in, PrintStream out, PrintStream err) throws PrologException {
        Database database = new Database(System.in, System.out, System.err);

        InitialiseParserBuiltins.initialise(database);
        InitialiseBuiltins.initialise(database);

        return database;
    }

    /**
     * Consult Prolog sourced from the current input stream.
     *
     * @param db the prolog database used to parse the file and add definitions to.
     * @throws PrologException if the parser encounters an error.
     * @throws TokenException  if the parser encounters a token error.
     * @throws IOException     if the parser encounters an IO error.
     */
    public static void consult(Database db) throws TokenException, IOException, PrologException {
        logger.debug("### Consulting...");

        consult(db, db.getCurrentInputStream());
    }

    /**
     * Consult Prolog sourced from a named file.
     *
     * @param db       the prolog database used to parse the file and add definitions to.
     * @param filename the name of the file to consult.
     * @throws PrologException if the parser encounters an error.
     * @throws TokenException  if the parser encounters a token error.
     * @throws IOException     if the parser encounters an IO error.
     */
    public static void consult(Database db, String filename) throws TokenException, IOException, PrologException {
        Path workingDir = Paths.get("").toAbsolutePath();

        Path filePath = workingDir.resolve(filename).normalize();
        logger.info("Loading file: " + filePath);
        if (Files.exists(filePath) && Files.isRegularFile(filePath)) {
            try (InputStream inputStream = Files.newInputStream(filePath)) {
                Prolog.consult(db, inputStream);
                System.out.println("Loaded: " + filePath);
            } catch (IOException e) {
                System.err.println("Failed to load file: " + filePath + " - " + e.getMessage());
            }
        } else {
            System.err.println("File not found or not a regular file: " + filePath);
        }

    }

    /**
     * Consult the contents of a URL (e.g. a file, etc.)
     *
     * @param db the prolog database used to parse the file and add definitions to.
     * @throws PrologException if the parser encounters an error.
     * @throws TokenException  if the parser encounters a token error.
     * @throws IOException     if the parser encounters an IO error.
     */
    public static void consult(Database db, URL url) throws TokenException, IOException, PrologException {
        InputStream is = url.openStream();

        logger.debug("### Consulting... " + url);

        consult(db, is);
    }

    /**
     * Consult the contents of an input stream until no more tokens can be read.
     *
     * @param db the prolog database used to parse the file and add definitions to.
     * @param is the input stream to read from.
     * @throws PrologException if the parser encounters an error.
     * @throws TokenException  if the parser encounters a token error.
     * @throws IOException     if the parser encounters an IO error.
     */
    public static void consult(Database db, InputStream is) throws PrologException, TokenException, IOException {
        PrologParser parser = new PrologParser(db);
        TokenSource source = new InputStreamTokenSource(is);
        Tokeniser tokeniser = new Tokeniser(source);

        while (parser.parse(tokeniser)) {
            if (parser.isFinished()) {
                Expression expr = parser.getExpression();

                if (expr.isTerm()) {
                    processTerm(db, (Term) expr);
                } else {
                    throw new PrologException("Cannot process - expression is not a Term.");
                }
            } else {
                throw new PrologException("Incomplete term in file.");
            }
        }
    }

    /**
     * Extract the specified processor from the Term and execute it.
     *
     * @param db   the prolog database on which to execute the processor.
     * @param term the Processor term.
     * @throws PrologException
     * @throws IOException
     * @throws TokenException
     * @throws ParseException
     */
    public static void processTerm(Database db, Term term) throws PrologException, IOException, TokenException {
        logger.debug("\tProcess Term: " + term + ".");

        // Find a processor to handle this expression.
        Processor processor = db.findProcessor(term.getName(), term.getArity());

        // Execute the process.
        processor.execute(db, term);
    }
}
