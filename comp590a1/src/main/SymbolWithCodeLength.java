package main;

/* SymbolWithCodeLength
 * 
 * Class that encapsulates a symbol value along with the length of the code
 * associated with that symbol. Used to build the canonical huffman tree.
 * Implements Comparable in order to sort first by code length and then by symbol value.
 */

public class SymbolWithCodeLength implements Comparable<SymbolWithCodeLength> {

	// Instance fields should be declared here.
	private int _val;
	private int _length;

	// Constructor
	public SymbolWithCodeLength(int value, int code_length) {
		_val = value;
		_length = code_length;
	}

	// codeLength() should return the code length associated with this symbol
	public int codeLength() {
		// Needs implementation
		return this.codeLength();
	}

	// value() returns the symbol value of the symbol
	public int value() {
		// Needs implementation
		return this.value();
	}

	// compareTo implements the Comparable interface
	// First compare by code length and then by symbol value.
	public int compareTo(SymbolWithCodeLength other) {
		if (this._length > other.codeLength()) {
			return 1;
		}

		if (this._length < other.codeLength()) {
			return -1;
		}

		if (this._length == other.codeLength()) {
			if (this._val > other.value()) {
				return 1;
			}
			if (this._val < other.value()) {
				return -1;
			}

			else {
				return 0;
			}
		}
		return 1;
	}
}
