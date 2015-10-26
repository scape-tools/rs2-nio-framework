package com.runescape.core.game.utility.cryption;

public final class CryptionAlgorithm {

	/**
	 * The base ratio.
	 */
	public static final int GOLDEN_RATIO = 0x9E3779B9;

	/**
	 * The log of the size of the results and memory arrays.
	 */
	public static final int SIZE_LOG = 8;

	/**
	 * The size of the results and memory arrays.
	 */
	public static final int SIZE = 1 << SIZE_LOG;

	/**
	 * For pseudo-random lookup.
	 */
	public static final int MASK = (SIZE - 1) << 2;

	/**
	 * The count through the results.
	 */
	private int count = 0;

	/**
	 * The results.
	 */
	private int results[] = new int[SIZE];

	/**
	 * The internal memory state.
	 */
	private int memory[] = new int[SIZE];

	/**
	 * The accumulator.
	 */
	private int accumulator;

	/**
	 * The last result.
	 */
	private int result;

	/**
	 * The counter.
	 */
	private int c;

	/**
	 * The overloaded class constructor used for instantiation of
	 * this class file.
	 * 
	 * @param seed The encryption seed.
	 */
	public CryptionAlgorithm(int[] seed) {

		for(int i = 0; i < seed.length; i++) {

			results[i] = seed[i];
		}

		start(true);
	}

	/**
	 * Gets the next value.
	 * 
	 * @return The next value.
	 */
	public int getNextValue() {

		if(count-- == 0) {

			generate();

			count = SIZE - 1;
		}

		return results[count];
	}

	/**
	 * Generates 256 results.
	 */
	public void generate() {

		int i, j, x, y;

		result += ++c;

		for(i = 0, j = SIZE / 2; i < SIZE / 2;) {
			x = memory[i];
			accumulator ^= accumulator << 13;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;

			x = memory[i];
			accumulator ^= accumulator >>> 6;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;

			x = memory[i];
			accumulator ^= accumulator << 2;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;

			x = memory[i];
			accumulator ^= accumulator >>> 16;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;
		}
		for (j = 0; j < SIZE / 2;) {
			x = memory[i];
			accumulator ^= accumulator << 13;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;

			x = memory[i];
			accumulator ^= accumulator >>> 6;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;

			x = memory[i];
			accumulator ^= accumulator << 2;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;

			x = memory[i];
			accumulator ^= accumulator >>> 16;
			accumulator += memory[j++];
			memory[i] = y = memory[(x & MASK) >> 2] + accumulator + result;
			results[i++] = result = memory[((y >> SIZE_LOG) & MASK) >> 2] + x;
		}
	}

	/**
	 * Initializes the encryption algorithm.
	 * 
	 * @param pass Determines if a secondary pass should be performed.
	 */
	public void start(boolean pass) {

		int a, b, c, d, e, f, g, h, i;
		a = b = c = d = e = f = g = h = GOLDEN_RATIO;
		for(i = 0; i < 4; ++i) {
			a ^= b << 11;
			d += a;
			b += c;
			b ^= c >>> 2;
			e += b;
			c += d;
			c ^= d << 8;
			f += c;
			d += e;
			d ^= e >>> 16;
			g += d;
			e += f;
			e ^= f << 10;
			h += e;
			f += g;
			f ^= g >>> 4;
			a += f;
			g += h;
			g ^= h << 8;
			b += g;
			h += a;
			h ^= a >>> 9;
			c += h;
			a += b;
		}
		for(i = 0; i < SIZE; i += 8) {
			if(pass) {
				a += results[i];
				b += results[i + 1];
				c += results[i + 2];
				d += results[i + 3];
				e += results[i + 4];
				f += results[i + 5];
				g += results[i + 6];
				h += results[i + 7];
			}
			a ^= b << 11;
			d += a;
			b += c;
			b ^= c >>> 2;
			e += b;
			c += d;
			c ^= d << 8;
			f += c;
			d += e;
			d ^= e >>> 16;
			g += d;
			e += f;
			e ^= f << 10;
			h += e;
			f += g;
			f ^= g >>> 4;
			a += f;
			g += h;
			g ^= h << 8;
			b += g;
			h += a;
			h ^= a >>> 9;
			c += h;
			a += b;
			memory[i] = a;
			memory[i + 1] = b;
			memory[i + 2] = c;
			memory[i + 3] = d;
			memory[i + 4] = e;
			memory[i + 5] = f;
			memory[i + 6] = g;
			memory[i + 7] = h;
		}
		if(pass) {
			for(i = 0; i < SIZE; i += 8) {
				a += memory[i];
				b += memory[i + 1];
				c += memory[i + 2];
				d += memory[i + 3];
				e += memory[i + 4];
				f += memory[i + 5];
				g += memory[i + 6];
				h += memory[i + 7];
				a ^= b << 11;
				d += a;
				b += c;
				b ^= c >>> 2;
				e += b;
				c += d;
				c ^= d << 8;
				f += c;
				d += e;
				d ^= e >>> 16;
				g += d;
				e += f;
				e ^= f << 10;
				h += e;
				f += g;
				f ^= g >>> 4;
				a += f;
				g += h;
				g ^= h << 8;
				b += g;
				h += a;
				h ^= a >>> 9;
				c += h;
				a += b;
				memory[i] = a;
				memory[i + 1] = b;
				memory[i + 2] = c;
				memory[i + 3] = d;
				memory[i + 4] = e;
				memory[i + 5] = f;
				memory[i + 6] = g;
				memory[i + 7] = h;
			}
		}

		generate();

		count = SIZE;
	}
}