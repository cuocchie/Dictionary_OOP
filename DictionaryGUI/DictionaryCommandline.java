package sample;

import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Scanner;


public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    // private Dictionary;
    DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public DictionaryManagement getDictMan() {
        return dictionaryManagement;
    }

    public void showAllWords() {
        Dictionary tempDictionary = new Dictionary(dictionaryManagement.getDictionary());

        System.out.println("No   | English        | Vietnamese");
        for (int i = 0; i < tempDictionary.shelfSize(); i++) {
            System.out.print(i + "    ");
            tempDictionary.getWord(i).showWord();
            System.out.println();
        }
    }

    public void insertFile() throws Exception{
        dictionaryManagement.insertFromFile();
    }

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() throws Exception {
        dictionaryManagement.insertFromFile();
        showAllWords();
        dictionaryManagement.dictionaryLookup();
    }

    public boolean dictionaryGUI(String word, boolean found) {
        found = dictionaryManagement.dictionaryLookUpGUI(word, found);
        return found;
    }

    public void testDictionary() throws Exception{
        dictionaryManagement.insertFromFile();
        dictionaryManagement.dictionaryExportToFile();
    }

    public void getWords(ListView<String> englishWords, ListView<String> vietnameseWords) {
        for(int i = 0; i < dictionaryManagement.getDictionary().shelfSize(); i++) {
            englishWords.getItems().addAll(dictionaryManagement.getDictionary().getWord(i).getWordTarget());
            //System.out.println(dictionaryManagement.getDictionary().getWord(i).getWordTarget());
            vietnameseWords.getItems().addAll(dictionaryManagement.getDictionary().getWord(i).getWordExplain());
            //System.out.println(dictionaryManagement.getDictionary().getWord(i).getWordExplain());
        }
    }

    public void getWords(ListView<String> englishWords, ListView<String> vietnameseWords, String word) {
        ArrayList<Word> validWord = dictionaryManagement.dictionarySubWord(word);

        for(Word i : validWord) {
            englishWords.getItems().addAll(i.getWordTarget());
            vietnameseWords.getItems().addAll(i.getWordExplain());
        }
    }

    public void dictionarySearcher() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Word> result = new ArrayList<Word>();
        String wordSearch = scanner.next();
        result = dictionaryManagement.dictionarySubWord(wordSearch);
        if(result.isEmpty()) {
            System.out.println("There's no such word");
        }
        for(Word i : result) {
            System.out.print(i.getWordTarget() + ", ");
        }
        scanner.close();
    } 
}
