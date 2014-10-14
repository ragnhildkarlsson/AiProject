public class nGram {

	private String[] words;

	public nGram(String[] words) {
		this.words = words;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof nGram) {
			nGram comparingnGram = (nGram) o;
			if (comparingnGram.length() == this.length()) {
				boolean equal = true;
				for (int i = 0; i < this.length(); i++) {
					if (!this.getWord(i).equals(comparingnGram.getWord(i)))
						equal = false;
				}
				return equal;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = 0;
		for (int i = 0; i < this.length(); i++) {
			hashCode += this.getWord(i).hashCode();
		}
		return hashCode;
	}

	public String[] getWords() {
		return this.words;
	}

	public String getWord(int index) {
		if (index < words.length) {
			return this.words[index];
		} else {
			return null;
		}
	}

	public int length() {
		return words.length;
	}

}
