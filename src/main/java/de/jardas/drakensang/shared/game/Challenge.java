package de.jardas.drakensang.shared.game;

public class Challenge {
	public static ChallengeProbability calculateChances(int at1, int at2,
			int at3, int talentOriginal, int handicap) {
		final int talent = talentOriginal > handicap ? talentOriginal
				- handicap : 0;
		final int wx = talentOriginal < handicap ? handicap - talentOriginal
				: 0;

		int count = 0;
		int success = 0;
		int remain = 0;

		for (int w1 = 1; w1 <= 20; w1++) {
			for (int w2 = 1; w2 <= 20; w2++) {
				for (int w3 = 1; w3 <= 20; w3++) {
					count++;

					if (w1 == 1 && w2 == 1 || w1 == 1 && w3 == 1 || w2 == 1
							&& w3 == 1) {
						success++;
						remain += talent;
						continue;
					}

					if (w1 == 20 && w2 == 20 || w1 == 20 && w3 == 20
							|| w2 == 20 && w3 == 20) {
						continue;
					}

					int remainder = talent - Math.max(0, w1 + wx - at1)
							- Math.max(0, w2 + wx - at2)
							- Math.max(0, w3 + wx - at3);

					if (remainder >= 0) {
						remain += remainder;
						success++;
					}
				}
			}
		}

		return new ChallengeProbability((float) success / count, (float) remain
				/ success);
	}

	public static class ChallengeProbability {
		private final float probabilty;
		private final float remainder;

		private ChallengeProbability(float probabilty, float remainder) {
			super();
			this.probabilty = probabilty;
			this.remainder = remainder;
		}

		public float getProbabilty() {
			return probabilty;
		}

		public float getRemainder() {
			return remainder;
		}

		@Override
		public String toString() {
			return probabilty + " + " + remainder;
		}
	}
}
