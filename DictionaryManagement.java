import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary dictionary;

    DictionaryManagement() {
        dictionary = new Dictionary();
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void insertFromCommandline() {
        Scanner in = new Scanner(System.in);
        int numberOfWords = in.nextInt();
        int i = 0;
        while (i < numberOfWords) {
            String wordTarget = in.next();
            in.nextLine();
            String wordExplain = in.nextLine();
            Word newWord = new Word(wordTarget, wordExplain);
            dictionary.setWord(newWord);
            i++;
        }
    }
}
