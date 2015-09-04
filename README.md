# jBff
A little BrainF**k interpreter in Java.

```java
StringBuilder code = new StringBuilder();
		code.append("+++++ +++++             initialize counter (cell #0) to 10\n");
		code.append("[                       use loop to set 70/100/30/10\n");
		code.append("    > +++++ ++          add  7 to cell #1\n");
		code.append("    > +++++ +++++       add 10 to cell #2\n");
		code.append("    > +++               add  3 to cell #3\n");
		code.append("    > +                 add  1 to cell #4\n");
		code.append("<<<< -                  decrement counter (cell #0)\n");
		code.append("]\n");
		code.append("> ++ .                  print 'H'\n");
		code.append("> + .                   print 'e'\n");
		code.append("+++++ ++ .              print 'l'\n");
		code.append(".                       print 'l'\n");
		code.append("+++ .                   print 'o'\n");
		code.append("> ++ .                  print ' '\n");
		code.append("<< +++++ +++++ +++++ .  print 'W'\n");
		code.append("> .                     print 'o'\n");
		code.append("+++ .                   print 'r'\n");
		code.append("----- - .               print 'l'\n");
		code.append("----- --- .             print 'd'\n");
		code.append("> + .                   print '!'\n");
		code.append("> .                     print '\n'\n");
```

Now you only need to call:

```java
(new SCBrainFkInterpreter()).proccess(code.toString());
```
