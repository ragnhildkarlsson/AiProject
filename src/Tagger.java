import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
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
	private int minOccurence;
	
	public Tagger(int minOccurence) throws ClassNotFoundException, IOException{
		tagger = new MaxentTagger("taggers/left3words-wsj-0-18.tagger");
		templates = new HashSet<nGram>();
		this.minOccurence = minOccurence;
	}
	
	public HashSet<nGram> getTemplates() throws IOException{
		
		if(templates.size()==0){
			readTemplatesFromFile();
		}
		return this.templates;
	}
	
	private void readTemplatesFromFile() throws IOException{
		File file = new File("templates/templates"+minOccurence + ".txt");
		if(!file.exists()){
			System.err.println("template file "+ "templates/templates"+minOccurence + ".txt" + " does not exist" );
			System.exit(0);
		}		
		ArrayList<String> rawTemplates = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
                rawTemplates.add(line);
        }
        br.close(); 
        System.out.println("size of raw templates is " + rawTemplates.size());
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
		String[] taggedWords = string.split("\\s+");
		
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
	
	
	private void generateTemplates(ArrayList<String> rawLines){		
		HashMap<nGram,Integer> countTemplates = new HashMap<>();
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < rawLines.size(); i++) {
			temp.clear();
			String string = rawLines.get(i);
			string = tagger.tagString(string);
			String[] taggedWords = string.split("\\s+");
			
			for (int j = 0; j < taggedWords.length; j++) {
				String[] res = taggedWords[j].split("/");
				if(res.length>1){
					String tag = res[1];
					temp.add(tag);
				}
				
			}
			String[] a = new String[temp.size()];
			String[] templateString = temp.toArray(a);
			nGram template = new nGram(templateString);
			if(countTemplates.containsKey(template)){
				Integer count = countTemplates.get(template);
				count++;
				countTemplates.put(template, count);
			}
			else{
				countTemplates.put(template, 1);
			}
			}
		
		Set<nGram> keys = countTemplates.keySet();
		for (nGram key : keys) {
			if(countTemplates.get(key)>=minOccurence){
				templates.add(key);
			}
		}
	}
	

	public void printTemplatesToFile(ArrayList<String> rawLines) throws IOException{
		generateTemplates(rawLines);
		File file = new File("templates/templates"+minOccurence + ".txt");
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileWriter write = new FileWriter(file.getPath(), true);
		PrintWriter printer = new PrintWriter(write);

		for (nGram template : templates) {
			for (int j = 0; j < template.length(); j++) {
				printer.print(template.getWord(j) + " ");
			}
			printer.println(" ");
		}
		printer.close();
	}
}
