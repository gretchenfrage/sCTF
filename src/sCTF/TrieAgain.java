package sCTF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TrieAgain {
	
	static final char[][] LETTERGRID = 
		{
				{'E', 'E', 'C', 'A'},
				{'A', 'L', 'E', 'P'},
				{'H', 'N', 'B', 'O'},
				{'Q', 'T', 'T', 'Y'}
		};
	static ArrayList<String> words;
	static int matches;
	
	public static void main(String[] args) {
		System.out.println("reading");
		read();
		System.out.println("trimming");
		trim();
		System.out.println('"' + words.get(7) + '"');
		System.out.println("matching");
		match();
		System.out.println("ans found! " + matches);
	}
	
	static void read() {
		words= new ArrayList<String>();
		words.add("");
		
		File file = new File("words.txt");
		try (FileInputStream in = new FileInputStream(file)) {
			while (in.available() > 0) {
				char c = (char) in.read();
				if (c == '\n') {
					words.add("");
				} else {
					words.set(words.size() - 1, words.get(words.size() - 1) + Character.toUpperCase(c));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		words.remove(words.size() - 1);
	}
	
	static void trim() {
		for (int i = words.size() - 1; i >= 0; i--) {
			if (words.get(i).length() > 4) {
				words.remove(i);
			}
		}
	}
	
	static char get(int x, int y) {
		if (x > 0 && y > 0 && x < 4 && y < 4) {
			return LETTERGRID[x][y];
		} else {
			return ' ';
		}
	}
	
	static void match() {
		matches = 0;
		for (String s : words) {
			matches += check(s.toCharArray());
		}
	}
	
	static int check(char[] word) {
		int out = 0;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (check(word, x, y, 1, 1)) {
					out++;
				}
				if (check(word, x, y, -1, -1)) {
					out++;
				}
				if (check(word, x, y, 1, -1)) {
					out++;
				}
				if (check(word, x, y, -1, 1)) {
					out++;
				}
				if (check(word, x, y, 0, 1)) {
					out++;
				}
				if (check(word, x, y, 0, -1)) {
					out++;
				}
				if (check(word, x, y, 1, 0)) {
					out++;
				}
				if (check(word, x, y, -1, 0)) {
					out++;
				}
			}
		}
		return out;
	}
	
	static boolean check(char[] word, int x, int y, int xc, int yc) {
		if (word.length == 2) {
			return word[0] == get(x, y) && word[1] == get(x + xc, y + yc);
		} else if (word.length == 3) {
			return word[0] == get(x, y) && word[1] == get(x + xc, y + yc) && word[2] == get(x + 2 * xc, y + 2 * yc);
		} else if (word.length == 4) {
			return word[0] == get(x, y) && word[1] == get(x + xc, y + yc) &&
					word[2] == get(x + 2 * xc, y + 2 * yc) && word[3] == get(x + 3 * xc, y + 3 * yc);
		} else {
			throw new RuntimeException();
		}
	}
	
}
