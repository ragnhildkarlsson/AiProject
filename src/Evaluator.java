import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Evaluator {
	
	int minOccurance;
	int sampleSize;
	ArrayList<String> uniqueRawLines;
	
	public Evaluator(int minOccurence, int sampleSize, ArrayList<String> uniqueRawLines){
		this.minOccurance = minOccurence;
		this.sampleSize = sampleSize;
		this.uniqueRawLines = uniqueRawLines;
		System.out.println("Present evaluator evaluates sample of size "+sampleSize);
		System.out.println("Minoccurence " + minOccurence);
	}
	
	
	public void evaluate(LanguageModel lm){
		ArrayList<String> sample = new ArrayList<String>();
		HashSet<String> uniqeSamples = new HashSet<>();
		HashSet<String> creativeVerses = new HashSet<>();
		HashSet<String> syntacticVerses = new HashSet<>();		
		HashSet<String> niceVerses = new HashSet<>();
		
		for (int i = 0; i < sampleSize; i++) {
			String verse =lm.generateVerse();
			sample.add(verse);
		}
		
		for (int i = 0; i < sampleSize; i++) {
			uniqeSamples.add(sample.get(i));
		}
				
		//Save creative verses in set
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < uniqueRawLines.size(); i++){
			sb.append(uniqueRawLines.get(i));
			sb.append(" ");
		}
		String giant = sb.toString();
		
		for (int i = 0; i < sampleSize; i++) {
			if(!(giant.contains(sample.get(i)))){
				creativeVerses.add(sample.get(i));
			}	
		}
		//Save syntactic correct verses
		for (int i = 0; i < sampleSize; i++) {
			if(lm.isSyntaxCorrect(sample.get(i))){
				syntacticVerses.add(sample.get(i));
				niceVerses.add(sample.get(i));
			}
		}
		//nice verses contains the intersection of syntactic and creative;
		niceVerses.retainAll(creativeVerses);
		
		//Print result
		
		System.out.println("Number of unique samples was " + uniqeSamples.size());
		System.out.println("Percentage of unique verses was " + (double) uniqeSamples.size()/ sample.size());
		
		
		System.out.println("Number of creative verses was " + creativeVerses.size());
		System.out.println("Percentage of creative verses was " + (double) creativeVerses.size()/ sample.size());
		
		System.out.println("Number of syntactic verses was " + syntacticVerses.size());
		System.out.println("Percentage of syntacicly correct verses was " + (double) syntacticVerses.size()/ sample.size());

		System.out.println("Number of nice verses was " + niceVerses.size());
		System.out.println("Percentage of nice verses was " + (double) niceVerses.size()/ sample.size());

		System.out.println("The nice verses was: ");
		
		for (String string : niceVerses) {
			System.out.println(string);
		}
		
		
	}
	
	
//	public void evaluate(ArrayList<String> rawLines, ArrayList<String> verses){
//		
//		//lineWiseComparison(rawLines, verses);
//		StringBuilder sb = new StringBuilder();
//		for (int i = 0; i < rawLines.size(); i++){
//			sb.append(rawLines.get(i));
//		}
//		String giant = sb.toString();
//		
//		int countDoubles = 0;
//		for (int i = 0; i < verses.size(); i++) {
//			if(giant.contains(verses.get(i))){
//				countDoubles++;
//			}	
//		}
//		
//		System.out.println("Number of matches " + countDoubles);
//		System.out.println("Percentage of verses that are doubles is " + (double)countDoubles/(double)verses.size());
//
//		
//	}

	private void lineWiseComparison(ArrayList<String> rawLines,
			ArrayList<String> verses) {
		System.out.println("Comparing linewise ...");
		int countDoubles = 0;
		for (int i = 0; i < verses.size(); i++) {
			if(rawLines.contains(verses.get(i))){
				countDoubles++;
			}		
			
		}
		System.out.println("Number of matches " + countDoubles);
		System.out.println("Percentage of verses that are doubles is " + (double)countDoubles/(double)verses.size());
	}
	
	
}
