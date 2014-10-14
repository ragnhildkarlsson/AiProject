	

    import java.io.IOException;
    import java.util.ArrayList;
     
     
    public class Main {
     
            /**
             * @param args
             */
            public static void main(String[] args) {
                    try {
                            Parser p = new Parser();
                            ArrayList<String> lines = p.getRawLines();
                            LanguageModel lm = new LanguageModel(lines, 3);
                            //lm.printAllNgrams();
                            System.out.println(lm.generateVerse());
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                   
            }
           
     
    }

