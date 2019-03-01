package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import io.OutputStreamBitSink;

public class HuffmanEncoder {

	private Map<Integer, String> _code_map;
	private Map<Integer, String> cmap;

	public HuffmanEncoder(int[] symbols, int[] symbol_counts) throws Exception {
		assert symbols.length == symbol_counts.length;

		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes

		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();

		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.

		for (int i = 0; i < 256; i++) {
			node_list.add(new LeafHuffmanNode(symbols[i], symbol_counts[i]));
		}

		// Sort the leaf nodes
		Collections.sort(node_list);

		// While you still have more than one node in your list...
		while (node_list.size() > 1) {
			// Remove the two nodes associated with the smallest counts

			// Create a new internal node with those two nodes as children.

			// Add the new internal node back into the list

			// Resort

			HuffmanNode smallest = node_list.remove(0);
			HuffmanNode smallest2 = node_list.remove(0);

			InternalHuffmanNode node = new InternalHuffmanNode(smallest, smallest2);
			node_list.add(node);

			Collections.sort(node_list);
		}
		assert node_list.size() == 1;
		cmap = new HashMap<Integer, String>();

		// Create a temporary empty mapping between symbol values and their code
		// strings
		HuffmanNode root = node_list.get(0);

		// Start at root and walk down to each leaf, forming code string along
		// the
		// way (0 means left, 1 means right). Insert mapping between symbol
		// value and
		// code string into cmap when each leaf is reached.
		stringOfLeaf("", true, root);

		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> lengths = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new
		// SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you
		// found in cmap).

		// Sort sym_with_lenght
		Collections.sort(lengths);

		// Now construct the canonical tree as you did in HuffmanDecodeTree
		// constructor

		InternalHuffmanNode croot = new InternalHuffmanNode();

		for (int i = 0; i < 256; i++) {
			croot.insertSymbol(lengths.get(i).codeLength(), lengths.get(i).value());
		}

		// If all went well, tree should be full.
		assert croot.isFull();

		// Create code map that encoder will use for encoding

		cmap = new HashMap<Integer, String>();

		// Walk down canonical tree forming code strings as you did before and
		// insert into map.

		stringOfLeaf("", false, croot);

	}

	public void stringOfLeaf(String s, boolean leaf, HuffmanNode root) throws Exception {

		if (root.isLeaf()) {
			if (!leaf) {
				_code_map.put(root.symbol(), s);
			}
			if (leaf) {
				cmap.put(root.symbol(), s);

			}
		}
		if (!root.isLeaf()) {

			if (root.left() != null) {
				stringOfLeaf(s + "0", leaf, root.left());
			}
			if (root.right() != null) {
				stringOfLeaf(s + "1", leaf, root.right());
			}
		}

	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}

}
