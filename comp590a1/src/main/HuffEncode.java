package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws Exception {
		String input_file_name = "/Users/SumitChandra/Desktop/UNC/comp590a1/src/data/compressed.dat";
		String output_file_name = "/Users/SumitChandra/Desktop/UNC/comp590a1/src/data/encodedfile.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] countOfSymbols = new int[256];
		int numOfSymbols = 0;

		// Read in each symbol (i.e. byte) of input file and
		// update appropriate count value in countOfSymbols
		// Should end up with total number of symbols
		// (i.e., length of file) as numOfSymbols

		int[] list = new int[256];

		int nextBits = fis.read();
		while (nextBits != -1) {
			countOfSymbols[nextBits]++;
			numOfSymbols++;
			nextBits = fis.read();
		}

		fis.close();

		double entropy = 0, newval = 0;

		int[] symbols = new int[256];
		for (int i = 0; i < 256; i++) {
			symbols[i] = i;

			Double probability = new Double((double) countOfSymbols[i] / (double) numOfSymbols);
			if (probability > 0)
				entropy += (probability * (-1) * (Math.log((double) probability)) / Math.log(2));
		}
		System.out.println("Theoretical entropy:  " + entropy);

		HuffmanEncoder e = new HuffmanEncoder(symbols, countOfSymbols);

		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		for (int i = 0; i < 256; i++) {
			Double probability;
			probability = ((double) countOfSymbols[i] / (double) numOfSymbols);

			if (probability > 0) {
				newval = newval + probability * (double) e.getCode(i).length();
			}
			bit_sink.write(e.getCode(i).length(), 8);
		}

		System.out.println("Entropy new Value:" + newval);

		bit_sink.write(numOfSymbols, 32);

		fis = new FileInputStream(input_file_name);

		nextBits = fis.read();
		while (nextBits != -1) {
			e.encode(nextBits, bit_sink);
			nextBits = fis.read();
		}

		bit_sink.padToWord();
		fis.close();
		fos.close();
	}
}