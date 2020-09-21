import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> shelfWords;

    Dictionary() {
        shelfWords = new ArrayList<Word>();
    }

    Dictionary(Dictionary dictionary) {
        this.shelfWords = dictionary.shelfWords;
    }

    public void setWord(Word word) {
        shelfWords.add(word);
    }

    public Word getWord(int position) {
        return shelfWords.get(position);
    }

    public int shelfSize() {
        return shelfWords.size();
    }
}
