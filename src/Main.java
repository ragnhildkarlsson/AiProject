	

    import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
     
     
    public class Main {
    	private static boolean preprocess =true;
    	private static boolean generate = false;
    	private static boolean evaluate = false;
    	private static int MINOCURENCE = 2;
    	private static int SAMPLESIZE = 1000;
            /**
             * @param args
             */
            public static void main(String[] args) {
                    try {
                			Parser p = new Parser();
                			ArrayList<String> rawLinesUnique = p.getRawLines(true);
                			ArrayList<String> rawLines = p.getRawLines(false);
                    		
                    		if(preprocess){
                    		Tagger t = new Tagger(MINOCURENCE);
                    		t.printTemplatesToFile(rawLinesUnique);
                    			Preprocessor pre = new Preprocessor(false, 3,MINOCURENCE);
                    		}
                    		LanguageModel lm = new LanguageModel(rawLines, 3,MINOCURENCE);
                    		if(generate){
                    		
                            System.out.println("Number of lines " + rawLines.size());
                            //lm.printTemplates();                            //lm.printAllNgrams();
                            
                            ArrayList<String> versesGenerated = new ArrayList<String>();
                            for (int i = 0; i < 100; i++) {
                            	String verse = lm.generateNiceVerse();
                                //System.out.println(verse);
                                versesGenerated.add(verse);
								
								}
                    		}
                    		if(evaluate){
                            Evaluator eval = new Evaluator(MINOCURENCE, SAMPLESIZE,rawLinesUnique);
                            eval.evaluate(lm);
                    		}
                            
    
                    } catch (IOException e) {
                            e.printStackTrace();
                    } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                   
            }
           
     
    }

