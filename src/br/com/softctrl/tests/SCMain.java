package br.com.softctrl.tests;

import br.com.softctrl.bff.SCBrainFkInterpreter;

/*
GNU LESSER GENERAL PUBLIC LICENSE
Version 3, 29 June 2007
Copyright (c) 2015 Carlos Timoshenko Rodrigues Lopes

http://www.0x09.com.br

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, subject to the following
conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
/**
 * @author carlostimoshenkorodrigueslopes@gmail.com
 */
public class SCMain {

	public static void main(String[] args) {

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

		(new SCBrainFkInterpreter()).proccess(code.toString());
	}

}
