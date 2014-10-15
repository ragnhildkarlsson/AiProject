import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;


public class Tagger {
	private MaxentTagger tagger;
	private HashSet<nGram> templates;
	
	public Tagger() throws ClassNotFoundException, IOException{
		tagger = new MaxentTagger("taggers/left3words-wsj-0-18.tagger");
		templates = new HashSet<nGram>();
		readTemplates();
	}
	
	public HashSet<nGram> getTemplates(){
		return this.templates;
	}
	
	private void readTemplates() throws IOException{
		ArrayList<String> rawTemplates = new ArrayList<String>();
		File folder = new File("templates");
        System.err.println(folder.exists());
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
        	File file = listOfFiles[i];
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
                rawTemplates.add(line);
        }
        br.close(); 
        System.out.println("size of raw templates is " + rawTemplates.size());
        }

        ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < rawTemplates.size(); i++) {
			temp.clear();
			Scanner scan = new Scanner(rawTemplates.get(i));
			while(scan.hasNext()){
				String word = scan.next();
				temp.add(word);
				}
			scan.close();
			String[] a = new String[temp.size()];
			String[] template = temp.toArray(a);			
			templates.add(new nGram(template));	
		}
			

	}
	public nGram getTags(String input){
		ArrayList<String> temp = new ArrayList<String>();
		temp.clear();
		String string = input;
		string = tagger.tagString(string);
		String[] taggedWords = string.split("\\s");
		
		for (int j = 0; j < taggedWords.length; j++) {
			String[] res = taggedWords[j].split("/");
			if(res.length>1){
				String tag = res[1];
				temp.add(tag);
			}
			
		}
		String[] a = new String[temp.size()];
		String[] template = temp.toArray(a);					
	return new nGram(template);
	}
	
	public HashSet<nGram> generateTemplates(ArrayList<String> rawLines){		
		HashSet<nGram> templates = new HashSet<>();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < rawLines.size(); i++) {
			temp.clear();
			String string = rawLines.get(i);
			string = tagger.tagString(string);
			String[] taggedWords = string.split("\\s");
			
			for (int j = 0; j < taggedWords.length; j++) {
				String[] res = taggedWords[j].split("/");
				if(res.length>1){
					String tag = res[1];
					temp.add(tag);
				}
				
			}
			String[] a = new String[temp.size()];
			String[] template = temp.toArray(a);			
			templates.add(new nGram(template));
			}
		
		return templates;
	}
	
//	private void printTemplatesToFile(ArrayList<String> rawLines) throws IOException{
//	HashSet<nGram> templates = generateTemplates(rawLines);
//    File folder = new File("templates");
//    System.err.println(folder.exists());
//    File[] listOfFiles = folder.listFiles();
//	File file = listOfFiles[0];
//	FileWriter write =new FileWriter(file.getPath(),true);
//	PrintWriter printer = new PrintWriter(write);
//
//    for (nGram template : templates) {
//        for (int j = 0; j < template.length(); j++) {
//            printer.print(template.getWord(j)+" ");   
//        }
//        printer.println(" ");
//    }		
//
//}	
}
