	

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.FileReader;
    import java.io.IOException;
    import java.util.ArrayList;
     
     
    public class Parser {
     
           
            ArrayList<String> rawLines;
           
           
            public Parser() throws IOException{
           
                    rawLines = new ArrayList<String>();
                    readLyrics();
                    //TODO TAG Lyrics
            }
           
           
            public ArrayList<String> getRawLines(){
                    return rawLines;
            }
           
            public void printRawLines(){
     
            }
           
            public void readLyrics() throws IOException{
                    File folder = new File("lyrics");
                    //System.err.println(folder.exists());
                    File[] listOfFiles = folder.listFiles();
                   
                    for (int i = 0; i < listOfFiles.length; i++) {
                    File file = listOfFiles[i];
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                            rawLines.add(line);
                    }
                    br.close();    
                    }
            }
     
    }

