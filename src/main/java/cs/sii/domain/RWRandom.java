package cs.sii.domain;

import java.util.Random;
import org.springframework.stereotype.Component;


/**
 * Reimplementazione della classe random utilizzando dei metodi leggermente modificati.
 * Rimane similirare alla classe Random
 * Fonte: http://www.macs.hw.ac.uk/~dwcorne/cceafi/DECC-DML-aFI/benchmark/javarandom/src/Randomizer.java
 * 		  https://docs.oracle.com/javase/7/docs/api/java/util/Random.html
 *
*/

@Component
public class RWRandom extends Random {

	public RWRandom() {
	}

	public RWRandom(Long seed) {
		super(seed);
	}

	public long nextPosLong(long n) {
		long bits, val;
		do {
			bits = (this.nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

	public int nextPosInt(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive");

		if ((n & -n) == n) // i.e., n is a power of 2
			return (int) ((n * (long) next(31)) >> 31);

		int bits, val;
		do {
			bits = next(31);
			val = bits % n;
		} while (bits - val + (n - 1) < 0);
		return val;
	}

}
