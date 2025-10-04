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

package com.kajabity.prolog.console;

import com.kajabity.prolog.core.environment.Database;
import com.kajabity.prolog.core.environment.PrologException;
import com.kajabity.prolog.core.expression.Expression;
import com.kajabity.prolog.core.expression.Term;
import com.kajabity.prolog.io.Prolog;
import com.kajabity.prolog.io.parse.PrologParser;
import com.kajabity.prolog.io.token.Tokeniser;
import com.kajabity.utils.token.StringTokenSource;
import com.kajabity.utils.token.TokenException;
import com.kajabity.utils.token.TokenSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.net.URL;

@Command(name = "kajabity-prolog",
        mixinStandardHelpOptions = true, // adds --help and --version
        description = "Kajabity Prolog",
        versionProvider = PropertiesVersionProvider.class)
class PrologConsole implements Runnable {
    private final static Logger logger = LogManager.getLogger(PrologConsole.class);

    PrologParser parser = null;

    @Option(names = "--init", description = "Initialization file(s) instead of default.", arity = "1..*")
    private java.util.List<String> inits;

    @Option(names = "--load", description = "Prolog source files to load first.", arity = "1..*")
    private java.util.List<String> loads;

    @Parameters(index = "0", description = "Main Prolog file", arity = "0..1")
    private String mainFile;

    @Override
    public void run() {
        // Here you’d launch Prolog with the collected arguments
        logger.info("Init files: " + inits);
        logger.info("Main file: " + mainFile);
        logger.info("Load files: " + loads);

        Database database;
        try {
            database = Prolog.createDatabase(System.in, System.out, System.err);

            // Load initialisation file(s)
            if (inits != null) {
                logger.info("Loading initialisation files.");
                for (String filename : inits) {
                    Prolog.consult(database, filename);
                }
            } else {
                URL initialiseUrl = getClass().getResource("/init.pl");
                if (initialiseUrl == null) {
                    logger.warn("Failed to find initialisation file.");
                } else {
                    logger.info("Prolog initialising from " + initialiseUrl);
                    Prolog.consult(database, initialiseUrl);
                }
            }

            // Load source file(s)
            if (loads != null) {
                logger.info("Loading source files.");
                for (String filename : loads) {
                    Prolog.consult(database, filename);
                }
            }

            // Load source file(s)
            if (mainFile != null) {
                logger.info("Loading source files.");
                Prolog.consult(database, mainFile);
            }

        } catch (TokenException | IOException | PrologException e) {
            throw new RuntimeException(e);
        }

        try {
            terminal(database);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void terminal(Database database) throws IOException {
        Terminal terminal = TerminalBuilder.builder()
                .system(true)
                .dumb(true) // fallback if system terminal fails
                .build();

        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .build();

        String prompt = "Prolog> ";
        String continuedPrompt = "     |> ";

        parser = new PrologParser(database);

        boolean partial = false;
        do {
            String line;
            try {
                line = reader.readLine(partial ? continuedPrompt : prompt);
                partial = false;
            } catch (UserInterruptException | EndOfFileException e) {
                break;
            }

            logger.debug("Read: [{}]", line);

            TokenSource source = new StringTokenSource(line);
            Tokeniser t = new Tokeniser(source);

            try {
                parser.parse(t);

                if (parser.isEmpty()) {
                    logger.debug("==> Empty");
                } else if (parser.isFinished()) {
                    Expression expr = parser.getExpression();
                    parser.reset();

                    logger.debug("==> Parsed {}.", expr);

                    //  Handle the parsed statement.
                    Prolog.processTerm(database, (Term) expr);
                } else {
                    logger.debug("==> Partial");
                    partial = true;
                }
            } catch (Exception ex) {
                logger.error("Parse error", ex);
                database.getCurrentOutputStream().println(ex.getMessage());
                parser.reset();
            }
        } while (!database.isTerminated());

        database.getCurrentOutputStream().println("Bye.");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new PrologConsole()).execute(args);
        System.exit(exitCode);
    }
}
