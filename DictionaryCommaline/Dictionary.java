
import java.util.ArrayList;

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
    }

    public Word getWord(int position) {
        return shelfWords.get(position);
    }

    public void deleteWord(Word deletedWord) {
        shelfWords.remove(deletedWord);
    }

    public int shelfSize() {
        return shelfWords.size();
    }

    public String findWord(String lookUpWord) {
        for(Word i : shelfWords) {
            if(i.compareWordTarget(lookUpWord)) {
                return i.getWordExplain();
            }
        }
        return "Cannot find " + lookUpWord;
    }

    public ArrayList<String> findSubWord(String subWord) {
        ArrayList<String> validWordContainer = new ArrayList<String>();
        for(Word i : shelfWords) {
            if(i.compareSubWord(subWord)) {
                validWordContainer.add(i.getWordTarget());
            }
        }
        return validWordContainer;
    }
}
