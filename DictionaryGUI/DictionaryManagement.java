package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;


public class DictionaryManagement {
    private Dictionary dictionary;

    DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void addWord(String englishWord, String vietnameseWord) {
        Word newWord = new Word(englishWord, vietnameseWord);
        dictionary.setWord(newWord);
    }

    public void updateWord(String updateWordTarget, String targetWord, String explainWord) {
        for(Word i : dictionary.getShelfWords()) {
            if(i.compareWordTarget(updateWordTarget)) { 
                i.setWordExplain(explainWord);
                i.setWordTarget(targetWord);
            }
        }
        Collections.sort(dictionary.getShelfWords());
    }

    public boolean deleteWord(String wordToDelete) {
        boolean found = false;

        Word deletedWord = new Word(" ", " ");
        for(Word i : dictionary.getShelfWords()) {
            if(i.compareWordTarget(wordToDelete)) {
                deletedWord = i;
                found = true;
            }
        }
        dictionary.deleteWord(deletedWord);
        Collections.sort(dictionary.getShelfWords());
        return found;
    }

    public void insertFromCommandline() {
        Scanner in = new Scanner(System.in);
        System.out.println("So tu ban muon nhap: ");
        int numberOfWords = in.nextInt();
        int i = 0;
        in.nextLine();
        while (i < numberOfWords) {
            System.out.print("Nhap tu tieng anh: ");
            String wordTarget = in.nextLine();
            System.out.print("Nhap tu tieng viet: ");
            String wordExplain = in.nextLine();
            Word newWord = new Word(wordTarget, wordExplain);
            dictionary.setWord(newWord);
            i++;
        }
        in.close();
    }

    public void insertFromFile() throws Exception{
        File inputFile = new File("src/sample/dictionaries.txt");
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNextLine()) {
            String wordLine = scanner.nextLine();
            int tabIndex = wordLine.indexOf(" ");
            String wordTarget = wordLine.substring(0, tabIndex);
            String wordExplain = wordLine.substring(tabIndex + 1, wordLine.length());
            wordExplain = wordExplain.trim();
            Word newWord = new Word(wordTarget, wordExplain);
            dictionary.setWord(newWord);
        }
        scanner.close();
    }

    public void dictionaryLookup() {
        System.out.print("Nhap tu ban muon tim: ");
        Scanner scanner = new Scanner(System.in);
        String lookUpWord = scanner.next();
        boolean found = false;
        System.out.println(dictionary.findWord(lookUpWord, true));
        scanner.close();
    }

    public boolean dictionaryLookUpGUI(String word, boolean found) {
        String result = dictionary.findWord(word, found);
        if(result.contains("Cannot find")) found = false;
        else found = true;
        return found;
    }

    public ArrayList<Word> dictionarySubWord(String subWord) {
        return dictionary.findSubWord(subWord);
    }

    public void dictionaryExportToFile() throws Exception {
        FileWriter output = new FileWriter("src/sample/dictionaries.txt");
        ArrayList<Word> temp = new ArrayList<Word>();
        temp = dictionary.getShelfWords();
        //Collections.sort(temp);
        for(Word i : temp) {
            output.write(i.getWordTarget() + " " + i.getWordExplain() + "\n");
        }
        output.close();
    }
}
