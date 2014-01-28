package org.training.vamel;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
	private File inputFile;
	private File outputFile;
	private Pattern pattern;
	private Matcher matcher;
	private String currentLine;

	public App(String inputFileName, String outputFileName, String regexp) {
		this.inputFile = new File(inputFileName);
		this.outputFile = new File(outputFileName);
		this.pattern = Pattern.compile(regexp);
	}

	public void match() {
		  BufferedReader inputStream = null;
	      PrintWriter outputStream = null;
	      try {
	    	  inputStream = new BufferedReader(
	    			  new FileReader(inputFile));
	    	  outputStream = new PrintWriter(
	    			  new FileWriter(outputFile));
	    	  while ((currentLine = inputStream.readLine()) != null) {
	    		  matcher = pattern.matcher(currentLine);
	    		  boolean hasGroups = matcher.groupCount() > 0;
	    		  while (matcher.find()) {
	    			  if (hasGroups){
	    				  for (int i = 1; i <= matcher.groupCount(); i++) {
	    					  if (matcher.group(i) != null)
	    					  System.out.println("Capture group: " + matcher.group(i));
	    				  }
	    			  }
	    			  outputStream.println(matcher.group());
	    		  }
	    	  }
	      } catch (IOException e) {
	    	  System.err.println(e);
	      } finally {
	    	  this.close(inputStream);
	    	  this.close(outputStream);
	      }
	    	 

	}

	private void close(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			System.err.println("Unable to close stream " + e);
		}
	}

	public static void main(String[] args) {
		String inputFileName = args[0];
		String outputFileName = args[1];
		String regExp = args[2];
		App regExpApp = new App(inputFileName, outputFileName, regExp);
		regExpApp.match();

	}
}
