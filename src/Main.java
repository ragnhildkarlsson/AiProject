	

    import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
     
     
    public class Main {
     
            /**
             * @param args
             */
            public static void main(String[] args) {
                    try {
                            Parser p = new Parser();
                            ArrayList<String> lines = p.getRawLines();
                            System.out.println("Number of lines " + lines.size());
                            LanguageModel lm = new LanguageModel(lines, 3);
                            //lm.printTemplates();                            //lm.printAllNgrams();
                            
                            for (int i = 0; i < 200; i++) {
                                System.out.println(lm.generateNiceVerse());
								
							}
      

                    } catch (IOException e) {
                            e.printStackTrace();
                    } catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                   
            }
           
     
    }

