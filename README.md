Kajabity Prolog
===============

A Prolog interpreter written in Java just for fun!

It isn't intended to compete with any other Prolog implementations, but is rather to be used as a lerning excercise for
me and to provide code examples for my AI Explained articles on Kajabity.com.

It's still a work in progress and will likely be for some time.

Comments and contributions are welcome.

Enjoy!

Development
-----------

The application is built using Apache Maven.  Use the following command to build:

```shell
    mvn clean package
```

This will build all the packages, and create a runnable jar file in the [prolog-console/target](prolog-console/target) directory.

To run the application, use the following command (replacing the path and version as appropriate):

```shell
java -jar prolog-console/target/prolog-console-0.1.0-SNAPSHOT.jar
```

Command Line Options
--------------------

I've recently introduced [piccoli](https://picocli.info/) as a command line parser, providing following options:

```text
$ java -jar prolog-console/target/prolog-console-0.1.0-SNAPSHOT.jar --help
Usage: kajabity-prolog [-hV] [--init=<inits>...]... [--load=<loads>...]...
                       [<mainFile>]
Kajabity Prolog
      [<mainFile>]        Main Prolog file
  -h, --help              Show this help message and exit.
      --init=<inits>...   Initialization file(s) instead of default.
      --load=<loads>...   Prolog source files to load first.
  -V, --version           Print version information and exit.
```

Version information can be displayed using the `--version` option, e.g.:

```text
$ java -jar prolog-console/target/prolog-console-0.1.0-SNAPSHOT.jar --version
Kajabity Prolog 0.1.0-SNAPSHOT
Picocli 4.7.7
JVM: 25 (Eclipse Adoptium OpenJDK 64-Bit Server VM 25+36-LTS)
OS: Windows 11 10.0 amd64
```

When running the application without any arguments, the console will be opened.

```shell
java -jar prolog-console/target/prolog-console-0.1.0-SNAPSHOT.jar
```

For example:
```text
C:\OSS\Prolog\kajabity-prolog>java -jar prolog-console/target/prolog-console-0.1.0-SNAPSHOT.jar
>> yes.y Prolog.  Enter "quit." to exit.
Oct 03, 2025 3:28:39 PM org.jline.utils.Log logr
WARNING: Unable to create a system terminal, creating a dumb terminal (enable debug logging for more information)
Prolog> ?- member(A, [apple, banana, cherry]).
        A = apple
;
        A = banana
;
        A = cherry
;
>> no.
Prolog> quit.
Bye.
```

Alternatively, you can load a single Prolog file by passing it as an argument:
```text
C:\OSS\Prolog\kajabity-prolog>java -jar prolog-console/target/prolog-console-0.1.0-SNAPSHOT.jar samples\royal_family.pl
>> yes.y Prolog.  Enter "quit." to exit.
Loaded: C:\OSS\Prolog\kajabity-prolog\samples\royal_family.pl
Oct 03, 2025 3:38:55 PM org.jline.utils.Log logr
WARNING: Unable to create a system terminal, creating a dumb terminal (enable debug logging for more information)
Prolog> ?- parent(charles, Child).
        Child = william
;
        Child = harry
;
>> no.
Prolog> quit.
Bye.
```

You can also load multiple files using the `--load` option.

Initialisation
--------------

Kajabity Prolog loads a number of built-in predicates implemented in Java from the [prolog-builtin](./prolog-builtin)
package.

It also adds a number of additonal predicates and operator definitions from
[prolog-io/src/main/resources/init.pl](prolog-io/src/main/resources/init.pl).

You can override these by providing your own initialisation files using the `--init` option.

Contribution
------------

Contributions are welcome!  Please fork the repository and submit a pull request.

As you can see, this is a work in progress.

Like a garden, there is always something to prune, or new things to grow.
