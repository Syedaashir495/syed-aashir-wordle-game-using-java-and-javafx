package com.example.wordlejava;

import java.io.File;
import java.util.Scanner;

public class WordGenerator {
    
    Scanner reader;
    
    private final String WORDS_BASE_PATH = "/Users/aashir/wordlejava/src/main/" ;
    private final String ALL_WORDS_PATH =  "words2.txt";
    private final  String SOME_WORDS_PATH = "words1.txt";

    private final int SOME_WORDS_SIZE = 5_000;
    private final int ALL_WORDS_SIZE = 15_900;

    private String wordsFile;
    private int wordsFileSize; 
    
    public WordGenerator(boolean easyMode){
       if(easyMode){
            wordsFile = WORDS_BASE_PATH + SOME_WORDS_PATH;
            wordsFileSize  = SOME_WORDS_SIZE;
       }
        else    {
            wordsFile = WORDS_BASE_PATH + ALL_WORDS_PATH;
            wordsFileSize = ALL_WORDS_SIZE;
        }
           
       
    }

    String getRandomWord() {
       
        try{
            int count = 0;
            int randIdx = (int)(Math.random() * wordsFileSize);

            reader = new Scanner(new File(wordsFile)); 
            String currentWord = "";
            
            while(reader.hasNext()){
                currentWord = reader.nextLine();
                //System.out.println("Testing: " + currentWord);

                if (count == randIdx)
                    return currentWord;

                count++;
            }

        }catch(Exception e){
            System.out.println("Could not find words file.");
            e.printStackTrace();
            System.exit(1);
        }
       
       
        return "";
        
    }

    boolean isWord(String word){
        try{
            reader = new Scanner(new File(WORDS_BASE_PATH + ALL_WORDS_PATH));
            String currentWord = "";
            
            while(reader.hasNext()){
                currentWord = reader.nextLine();
                //System.out.println("Checking: " + currentWord + "...");
                if (currentWord.equals(word))
                    return true;
            }

        }catch(Exception e){
            System.out.println("Could not find word file.");
            e.printStackTrace();
            System.exit(1);
        }

       return false;

    }
    
    
}
