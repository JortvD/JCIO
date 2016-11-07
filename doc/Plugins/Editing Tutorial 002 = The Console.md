This tutorial will explain how to read from the console/write from the console. Using `System.out.println()` is not recommented! The Console class is fully static, to make it a little bit easier, :).
<br />
<br />
**WARNING:** For all the tutorials I use Eclipse, link: https://eclipse.org/downloads/. It is easy to use but has a LOT of features. It is also neatly designed, and is fully customizable. You'll also need to have some knowledge of the Java syntax.
<br />
<br />
<br />
**Writing:**
<br />
There are multiple ways to write to the console. For just printing a line of text I suggest you use `Console.println(String s)`. You can also choose the user who 'says' it, with: `Console.println(ConsoleUser user, String s)`. The users you can choose from are: `ConsoleUser.System` has the `SYS` prefix, `ConsoleUser.User` has the `YOU` prefix, `ConsoleUser.Empty` has no prefix, `ConsoleUser.Error` has the `ERR` prefix. You can also write text to the Console without a prefix by using `Console.write(String s)` or `Console.writeln(String s)`.
<br />
<br />
**Reading:**
<br />
You can read input in two ways. 1) `Console.read()` will read the line without a prefix and 2) `Console.readln()` will read the line with the 'YOU' prefix. Both will return a String with the read text.