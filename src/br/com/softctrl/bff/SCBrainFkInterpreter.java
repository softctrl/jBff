package br.com.softctrl.bff;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
public class SCBrainFkInterpreter {

	private static final String B_1 = "[";
	private static final String B_2 = "]";

	private interface IDebug {
		public void debug(char action);

		public void dumpMemory();

		public void printValue();

		public void log(StringBuilder message);
	}

	private final IDebug DEBUG_ON = new IDebug() {
		@Override
		public void debug(char action) {
			System.out.println(
					String.format("Action[%c] = %s", action, SCBrainFkInterpreter.toString(memory.toArray(), address)));
		}

		@Override
		public void dumpMemory() {
			System.out.println(
					String.format("\nMemory dump %s", SCBrainFkInterpreter.toString(memory.toArray(), address)));
		}

		@Override
		public void printValue() {
			System.out.print("\nPrinted Value: [" + new String(new byte[] { memory.get(address) }) + "]\n\n");
		}

		@Override
		public void log(StringBuilder message) {
			System.out.println(message);
		};
	};

	private final IDebug DEBUG_OFF = new IDebug() {
		@Override
		public void dumpMemory() {
		}

		@Override
		public void debug(char action) {
		}

		@Override
		public void printValue() {
			System.out.print(new String(new byte[] { memory.get(address) }));
		}

		@Override
		public void log(StringBuilder message) {
		};
	};

	private static final byte ZERO = 0;
	// private static final String TOKENS = "[\\<\\>+-\\.,\\[\\]]";
	private static final String NON_TOKENS = "[^\\<\\>+-\\.,\\[\\]]";

	private List<Byte> memory = new ArrayList<Byte>();
	private int address = -1;
	private int length = 0;
	// private ILog log = null;
	private IDebug dbg = DEBUG_OFF;

	public SCBrainFkInterpreter() {
		this.moveNext();
	}

	public SCBrainFkInterpreter setDebugMode(boolean active) {
		if (active) {
			this.dbg = DEBUG_ON;
		} else {
			this.dbg = DEBUG_OFF;
		}
		return this;
	}

	private void movePrior() {
		if (address > 0)
			address--;
	}

	private void moveNext() {
		address++;
		if (this.length <= address) {
			this.length++;
			this.memory.add(ZERO);
		}
	}

	private int incValue() {
		byte result = ZERO;
		if (memory.size() > address) {
			result = getValue();
		} else {
			memory.add(ZERO);
		}
		setValue(++result);
		return result;
	}

	private int decValue() {
		byte result = ZERO;
		if (memory.size() > address) {
			result = getValue();
		} else {
			memory.add(ZERO);
		}
		setValue(--result);
		return result;
	}

	private byte getValue() {
		return memory.get(address);
	}

	private void setValue(byte value) {
		this.memory.set(address, value);
	}

	private void printValue() {
		this.dbg.printValue();
	}

	private static final Scanner SCANNER = new Scanner(System.in);

	private void readValue() {
		this.setValue(SCANNER.nextByte());
	}

	public final void execute(char action) {
		switch (action) {
		case '<':
			this.movePrior();
			break;
		case '>':
			this.moveNext();
			break;
		case '+':
			this.incValue();
			break;
		case '-':
			this.decValue();
			break;
		case '.':
			this.printValue();
			break;
		case ',':
			this.readValue();
			break;
		}
		this.dbg.debug(action);
	}

	public void dumpMemory() {
		this.dbg.dumpMemory();
	}

	public static String toString(Object[] array, int address) {

		if (array == null)
			return "null";
		if (array.length == 0)
			return "[]";

		StringBuilder result = new StringBuilder();
		result.append('[');
		int count = array.length;
		result.append(address == 0 ? "*" : "");
		result.append(String.valueOf(array[0]));// String.format("%c%s",
												// (address == 0?'*':'\0'),
												// String.valueOf(array[0])));
		for (int idx = 1; idx < count; idx++) {
			result.append(',').append(' ');
			result.append(address == idx ? "*" : "");
			result.append(String.valueOf(array[idx]));
		}
		return result.append(']').toString();

	}

	private int getIndexLastBracket(final StringBuilder code, int idx, int count) {

		int idx2 = code.indexOf(B_2, idx);
		if (idx2 == -1)
			return -1;
		if (code.substring(idx + 1, idx2).contains(B_1)) {
			return getIndexLastBracket(code, idx2 + 1, ++count);
		} else if (count > 0) {
			if (code.indexOf(B_2, idx2 + 1) == -1)
				return idx2;
			else
				return getIndexLastBracket(code, idx2 + 1, --count);
		}
		return idx2;

	}

	public void validateSintax(final StringBuilder code) {

		final char b_1 = '[';
		final char b_2 = ']';
		int length = code.length();

		int b1Count = 0;
		int b2Count = 0;
		for (int idx = 0; idx < length; idx++) {
			if (code.charAt(idx) == b_1)
				b1Count++;
			if (code.charAt(idx) == b_2)
				b2Count++;
			if (b1Count < b2Count)
				throw new RuntimeException("Illegal sintax");
		}
		if (b1Count != b2Count)
			throw new RuntimeException("Illegal sintax.");

	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	private static final StringBuilder getOnlyCode(final String code) {
		return new StringBuilder(code.replaceAll(NON_TOKENS, ""));
	}

	/**
	 * 
	 * @return
	 */
	public final SCBrainFkInterpreter reset() {
		this.memory.clear();
		this.address = -1;
		this.length = 0;
		this.dbg = DEBUG_OFF;
		return this;
	}

	public SCBrainFkInterpreter proccess(String code) {

		StringBuilder _code = getOnlyCode(code);
		char[] actions = _code.toString().toCharArray();
		this.dbg.log(_code);
		int length = actions.length;
		boolean b = false;
		for (int idx = 0; idx < length;) {
			char act = actions[idx];
			switch (act) {
			case '[':
				b = true;

				int close = getIndexLastBracket(_code, idx, 0);
				if (close == -1)
					throw new RuntimeException("you missed ]");
				String loop = _code.substring(idx + 1, close);
				while (getValue() > 0)
					this.proccess(loop);
				idx += loop.length();
				break;
			case ']':
				if (!b)
					throw new RuntimeException("you missed [");
				break;

			default:
				this.execute(act);
				break;
			}
			idx++;
		}
		return this;

	}

}
