# MyRegex

MyRegex is a project I (Rocketnia) made in March 2008. I had recently learned about regular expressions, and their suite of operations for composing pattern-matching operations was inspiring to me. I didn't know the term "combinator parser library" at the time, but that's what this is.

Traditional regex systems require every lookbehind expression to have a maximum length known in advance. This library lifts that limitation. Every combinator has an interpretation that goes backwards, applying its match from the end of the string to the beginning.

Traditional regex systems have a backreference operator, to assert that a certain matched substring appears again at another location in the overall match. The mechanism I implemented here is somewhat more expressive: The parser combinators can collect multiple substring matches under a single name, and another combinator can take a look at this set and force a backtrack if not all the substrings are identical.

A rudimentary unit test is provided in `com.rocketnia.hacks.myregex.MyRegexTest`. You can run it if you have a compiled version of the Proj/src/ directory in your Java classpath.
