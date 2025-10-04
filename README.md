Kajabity Prolog
===============

A Prolog interpreter written in Java, created as a learning exercise and to provide code examples for my AI Explained articles.

It isn’t intended to compete with other Prolog implementations, but rather to help explore the fundamentals of logic programming.

It’s still a work in progress and will likely be for some time. Comments and contributions are welcome!

👉 For project background and in-depth articles, see the [Kajabity Prolog Project Page](https://www.kajabity.com/kajabity-prolog/).

Development
-----------

Build with Apache Maven:

```shell
    mvn clean package
```

This will build all the packages, and create a runnable jar file in the [prolog-console/target](prolog-console/target) directory.

To run the application, use the following command:

```shell
kajabity-prolog
```

This selects between `kajabity-prolog.bat` on windows and `kajabity-prolog.sh` on unix platforms.


Command Line Options
--------------------

I've recently introduced [picocli](https://picocli.info/) as a command line parser, providing following options:

```text
C:\OSS\Prolog\kajabity-prolog> kajabity-prolog --help
Running: java --enable-native-access=ALL-UNNAMED -jar "C:\OSS\Prolog\kajabity-prolog\prolog-console\target\prolog-console-0.1.0-SNAPSHOT-shaded.jar" --help

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
C:\OSS\Prolog\kajabity-prolog> kajabity-prolog --version
Running: java --enable-native-access=ALL-UNNAMED -jar "C:\OSS\Prolog\kajabity-prolog\prolog-console\target\prolog-console-0.1.0-SNAPSHOT-shaded.jar" --version

Kajabity Prolog 0.1.0-SNAPSHOT
JVM: 25 (Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 25+37-LTS-3491)
OS: Windows 11 10.0 amd64
```

When running the application without any arguments, the console will be opened.

For example:
```text
C:\OSS\Prolog\kajabity-prolog>kajabity-prolog
Running: java --enable-native-access=ALL-UNNAMED -jar "C:\OSS\Prolog\kajabity-prolog\prolog-console\target\prolog-console-0.1.0-SNAPSHOT-shaded.jar"

Kajabity Prolog.  Enter "quit." to exit.
>> yes.
Prolog> quit.
Bye.

```

Alternatively, you can load a single Prolog file by passing it as an argument:
```text
C:\OSS\Prolog\kajabity-prolog>kajabity-prolog samples\royal_family.pl
Running: java --enable-native-access=ALL-UNNAMED -jar "C:\OSS\Prolog\kajabity-prolog\prolog-console\target\prolog-console-0.1.0-SNAPSHOT-shaded.jar" samples\royal_family.pl

Kajabity Prolog.  Enter "quit." to exit.
>> yes.
Loaded: C:\OSS\Prolog\kajabity-prolog\samples\royal_family.pl
Prolog> ?- parent(charles, Child).
        Child = william ;
        Child = harry ;
>> no.
Prolog> quit.
Bye.
```

You can also load multiple files using the `--load` option.

Initialisation
--------------
* Built-in predicates: from [prolog-builtin](prolog-builtin) package.
* Default init: [prolog-io/src/main/resources/init.pl](prolog-io/src/main/resources/init.pl).
* Override with `--init`.

Contribution
------------

Contributions are welcome! Please fork and submit a pull request.

Like a garden, there is always something to prune, or new things to grow. 🌱
