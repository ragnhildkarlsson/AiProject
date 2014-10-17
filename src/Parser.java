	

    import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
     
     
    public class Parser {
     
           
            ArrayList<String> rawLines;
            HashSet<String> uniqueLines;
           
           
            public Parser() throws IOException{
            		uniqueLines = new HashSet<>();
                    rawLines = new ArrayList<String>();
                    readLyrics();
                    System.out.println("Number of lines in indata "+ rawLines.size());
                    System.out.println("Number of unique lines in indata "+ uniqueLines.size());
                       
                    //TODO TAG Lyrics
            }
           
           
            public ArrayList<String> getRawLines(boolean unique){

            	ArrayList<String> result = new ArrayList<>();
            	if(unique){
            		for (String string : uniqueLines) {
						result.add(string);
					}
            		                }
            	else{
            		result = rawLines;
            	}
            	return result;
            }
           
            public void printRawLines(){
     
            }
           
            public void readLyrics() throws IOException{
                    File folder = new File("lyrics");
                    System.err.println(folder.exists());
                    File[] listOfFiles = folder.listFiles();
                   
                    for (int i = 0; i < listOfFiles.length; i++) {
                    File file = listOfFiles[i];
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                            rawLines.add(line);
                            uniqueLines.add(line);
                    }
                    br.close();    
                    }
            }
     
    }

