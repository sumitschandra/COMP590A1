package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws Exception {

		String input_file_name = "/Users/SumitChandra/Desktop/UNC/comp590a1/src/data/compressed.dat";
		String output_file_name = "/Users/SumitChandra/Desktop/UNC/comp590a1/src/data/decodedfile.txt";
		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		for (int i = 0; i < 256; i++) {

			int length = bit_source.next(8);
			symbols_with_length.add(new SymbolWithCodeLength(i, length));

		}
		Collections.sort(symbols_with_length);

		HuffmanDecodeTree huffmanTree = new HuffmanDecodeTree(symbols_with_length);

		int numberOfSym = bit_source.next(32);

		int[] countOfSym = new int[256];

		try {

			// Open up output file.

			FileOutputStream fos;
			fos = new FileOutputStream(output_file_name);

			for (int i = 0; i < 256; i++) {
				int decoded;
				decoded = huffmanTree.decode(bit_source);
				countOfSym[decoded]++;
				fos.write(decoded);
			}

			for (int i = 0; i < 256; i++) {
				Double probability;
				probability = ((double) countOfSym[i] / (double) numberOfSym);

				if (countOfSym[i] >= 0)
					System.out.println("Symbol probability at index " + i + " is " + probability.toString());

			}

			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}