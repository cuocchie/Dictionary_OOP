package sample;

import java.util.ArrayList;
import java.util.Collections;

public class Dictionary {
    private ArrayList<Word> shelfWords;

    Dictionary() {
        shelfWords = new ArrayList<Word>();
    }

    Dictionary(Dictionary dictionary) {
        this.shelfWords = dictionary.shelfWords;
    }

    public ArrayList<Word> getShelfWords() {
        return shelfWords;
    }

    public void setWord(Word word) {
        shelfWords.add(word);
        Collections.sort(shelfWords);
    }

    public Word getWord(int position) {
        return shelfWords.get(position);
    }

    public void deleteWord(Word deletedWord) {
        shelfWords.remove(deletedWord);
        Collections.sort(shelfWords);
    }

    public int shelfSize() {
        return shelfWords.size();
    }

    public String findWord(String lookUpWord, boolean found) {
        for(Word i : shelfWords) {
            if(i.compareWordTarget(lookUpWord)) {
                found = true;
                return i.getWordExplain();
            }
        }
        return "Cannot find " + lookUpWord;
    }

    public ArrayList<Word> findSubWord(String subWord) {
        ArrayList<Word> validWordContainer = new ArrayList<Word>();
        for(Word i : shelfWords) {
            if(i.compareSubWord(subWord)) {
                validWordContainer.add(i);
            }
        }
        return validWordContainer;
    }
}
