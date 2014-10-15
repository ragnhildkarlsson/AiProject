import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Preprocessor {

	private Parser parser;
	private Tagger tagger;
	private HashMap<nGram, ArrayList<String>> speechParts;
	private ArrayList<String> rawLines;
	private HashSet<String> uniqueWords;
	private HashSet<nGram> templates;
	private int N;
	private HashMap<nGram, Integer> tagGrams;
	private HashSet<nGram> newGrams;
	private Random rand = new Random();

	public Preprocessor(boolean createTemplates, int n) throws IOException,
			ClassNotFoundException {

		parser = new Parser();
		tagger = new Tagger();
		speechParts = new HashMap<>();
		uniqueWords = new HashSet<>();
		rawLines = parser.getRawLines();
		templates = tagger.getTemplates();
		tagGrams = new HashMap<nGram, Integer>();
		newGrams = new HashSet<>();
		N = n;

		if (createTemplates) {
			tagger.printTemplatesToFile(rawLines);
		}

		generateSpeechParts();
		buildTemplateGrams();
		genNewNGrams();
		printNewNGrams();

	}

	private void printNewNGrams() throws IOException {

		File file = new File("newNGrams/newGrams.txt");
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		
		FileWriter write = new FileWriter(file.getPath(), true);
		PrintWriter printer = new PrintWriter(write);

		for (nGram newGram : newGrams) {
			for (int j = 0; j < newGram.length(); j++) {
				printer.print(newGram.getWord(j) + " ");
			}
			printer.println(" ");
		}
		printer.close();

	}

	private void genNewNGrams() {
		Set<nGram> tagGramSet = tagGrams.keySet();
		for (nGram t : tagGramSet) {
			int freq = tagGrams.get(t);
			boolean foundHopelessGram = false;

			for (int i = 0; i < freq; i++) {
				String[] tags = t.getWords();
				String[] newWords = new String[N];

				for (int j = 0; j < tags.length; j++) {
					// Try matching with one tag
					String[] tag = { tags[j] };
					nGram gram = new nGram(tag);
					
					if(speechParts.containsKey(gram)){
						int r = rand.nextInt(speechParts.get(gram).size());
						String newWord = speechParts.get(gram).get(r);
						newWords[j] = newWord; 	
					} else {
						// Try with two tags at the same time as key
						if (j == tags.length - 1){
							// we cannot try with two tags
							foundHopelessGram = true;
							break;
						}
						String[] tag2 = { tags[j], tags[j + 1] };
						gram = new nGram(tag2);
						if(speechParts.containsKey(gram)){
							int r = rand.nextInt(speechParts.get(gram).size());
							String newWord = speechParts.get(gram).get(r);
							newWords[j] = newWord; 	
							
						} else {
							// no solution found, break!
							foundHopelessGram = true;
							break;
						}
					}	
				}
				if (foundHopelessGram) {
					break;
				}

				nGram newGram = new nGram(newWords);
				System.out.println(newGram.toString());
				newGrams.add(newGram);

			}

		}

	}

	private void buildTemplateGrams() {

		for (nGram templateGram : templates) {
			String template = templateGram.toString();
			String[] tags = template.split("\\s+");
			for (int i = 0; i < tags.length; i++) {

				String[] nGramTags = new String[N];

				for (int k = 0; k < nGramTags.length; k++) {
					if (tags.length - i >= N) {
						nGramTags[k] = tags[i + k];
					}
				}
				if (nGramTags[0] == null) {
					break;
				}
				nGram ngram = new nGram(nGramTags);
				if (!tagGrams.containsKey(ngram)) {
					tagGrams.put(ngram, 1);
				} else {
					int value = tagGrams.get(ngram);
					value++;
					tagGrams.put(ngram, value);
				}
			}

		}

	}

	private void generateSpeechParts() {

		for (int i = 0; i < rawLines.size(); i++) {
			String[] words = rawLines.get(i).split("\\s+");
			for (int j = 0; j < words.length; j++) {
				String word = words[j];
				if (!(uniqueWords.contains(word))) {
					uniqueWords.add(word);
					nGram tagGram = tagger.getTags(word);
					if (!(speechParts.containsKey(tagGram))) {
						ArrayList<String> wordList = new ArrayList<String>();
						wordList.add(word);
						speechParts.put(tagGram, wordList);
					} else {
						speechParts.get(tagGram).add(word);
					}

				} else {
					break;
				}

			}

		}
	}

}
