	

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Random;
import java.util.Set;
     
     
    public class LanguageModel {
           
            private HashMap<nGram, Integer> nGrams;
            private final String STARTSYMB = "STARTSYM";
            private final String STOPSYMB = "STOPSYM";
            private ArrayList<String> rawLines;
            final int N;
            private Random rand = new Random();
            private ArrayList<nGram> startGrams;
           
            public LanguageModel(ArrayList<String> rawLines, int n){
                    nGrams = new HashMap<nGram, Integer>();
                    startGrams = new ArrayList<nGram>();
                    this.rawLines = rawLines;
                    N = n;
                    buildModel();
                   
            }
            public void printAllNgrams(){
                    Set<nGram> keys = nGrams.keySet();
                    for (nGram ngram : keys) {
                            for (int i = 0; i < ngram.length(); i++) {
                                    System.out.print(ngram.getWord(i)+" ");
                            }
                            System.out.print("Count " + nGrams.get(ngram));
                            System.out.println(" ");
                    }
            }
            public String generateVerse(){
                    String[] start = getStartGram().getWords();
//                    for (int i = 0; i < start.length; i++) {
//                            System.err.println(start[i]);
//                    }
                    ArrayList<String> verse = new ArrayList<String>();
                    addNgramToList(verse, start);
                    ArrayList<DistributionElement> distribution= new ArrayList<DistributionElement>();
                    
                    int iter = 0;
                    int maxIter = 8;
                    while (!verse.get(verse.size()-1).equals(STOPSYMB)){
                            distribution.clear();
                            int sum = 0;
                            String[] lastMiniGram = new String[N-1];
                            for (int i = 0; i < lastMiniGram.length; i++) {                        
                                    lastMiniGram[i] = verse.get(verse.size()-(N-1-i));
                            }
                           
                            Set<nGram> keys = nGrams.keySet();
                           
                            for (nGram key : keys) {
                                    boolean match =true;
                                    //System.err.println("key " + key[0]);
                                    for (int i = 0; i < lastMiniGram.length; i++) {
                                            //System.err.println("key i is "+key[i]+" last mingram i "+ lastMiniGram[i]);
                                            if(!(key.getWord(i).equals(lastMiniGram[i]))){                                         
                                                    match =false;
                                                    break;
                                            }
                                    }                      
                                    if(match){
                                            int cumFreq = sum+(nGrams.get(key));
                                            //System.out.println("Match with next word "+ key.getWord(N-1) +" "+ cumFreq );
                                            distribution.add(new DistributionElement(key.getWord(N-1), cumFreq));
                                    }
                            }
                            String nextWord="";
                           
                            //System.out.println("distribution size is " + distribution.size());
                            int lastFreq = distribution.get(distribution.size()-1).cumulativFrequence;
                            int r = rand.nextInt(lastFreq);
                            for (int i = 0; i < distribution.size(); i++){
                                    if(r< distribution.get(i).cumulativFrequence){
                                            nextWord = distribution.get(i).word;
                                            break;
                                    }
                            }
                            if(iter > maxIter){
                            	for (int i = 0; i < distribution.size(); i++){
                            		if(distribution.get(i).word.equals(STOPSYMB)){
                            			nextWord = STOPSYMB;
                            		}
                            	
                            }
                            }
                            //System.out.println("Next word was "  + nextWord);
                            verse.add(nextWord);
                            iter++;
                           
                           
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < verse.size(); i++) {
                            sb.append(verse.get(i));
                            sb.append(" ");
                    }
                   
                    return sb.toString();
            }
           
            private void addNgramToList(ArrayList<String> list, String[] nGram) {
                    for (int i = 0; i < nGram.length; i++) {
                            list.add(nGram[i]);
                    }
            }
           
            private nGram getStartGram(){
                    int index = rand.nextInt(startGrams.size());
                    return startGrams.get(index);
            }
           
            private void buildModel(){
                   
                    for (int i = 0; i < rawLines.size(); i++) {
                            String line = rawLines.get(i);
                            line = STARTSYMB +" "+ line + " "+ STOPSYMB;
                            String[] words = line.split("\\s+");
                            for (int j = 0; j < words.length; j++) {
                                    String[] nGramStrings = new String[N];
                                    for (int k = 0; k < nGramStrings.length; k++) {
                                            if(words.length-j>=N){
                                                    nGramStrings[k]=words[j+k];
                                            }
                                    }
                                    if(nGramStrings[0]==null){
                                        break;
                                }
                                     nGram ngram = new nGram(nGramStrings);
                                    if(j==0){
                                            startGrams.add(ngram);
                                    }
                                   
                                    
                                   
                                    if(!nGrams.containsKey(ngram)){
                                            nGrams.put(ngram, 1);
                                    }
                                    else{
                                            int value = nGrams.get(ngram);
                                            value++;
                                            nGrams.put(ngram, value);
                                    }
                                   
                            }
                           
                           
                    }
                   
                   
            }
           
           
           
    }

