# MyRegex

[![CI](https://github.com/rocketnia/myregex/actions/workflows/ci.yml/badge.svg)](https://github.com/rocketnia/myregex/actions/workflows/ci.yml)

MyRegex is a project I (Rocketnia) made in March 2008. I had recently learned about regular expressions, and their suite of operations for composing pattern-matching operations was inspiring to me. I didn't know the term "combinator parser library" at the time, but that's what this is.

Traditional regex systems require every lookbehind expression to have a maximum length known in advance. This library lifts that limitation. Every combinator has an interpretation that goes backwards, applying its match from the end of the string to the beginning.

Traditional regex systems have a backreference operator, to assert that a certain matched substring appears again at another location in the overall match. The mechanism I implemented here is somewhat more expressive: The parser combinators can collect multiple substring matches under a single name, and another combinator can take a look at this set and force a backtrack if not all the substrings are identical.


## Installation and use

To build MyRegex, first install a distribution of the JDK. We've used Eclipse Temurin 17.0.1+12 from Adoptium, but MyRegex should work even on much older JVM versions. (At the time of development in 2008, I was developing in Java 6 on the Sun Microsystems JDK.)

Then, run a build with the Gradle wrapper.

```bash
./gradlew build
```

This runs unit tests and creates a JAR file in lib/build/lisbs/lib.jar.
