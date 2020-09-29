
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    // private Dictionary;
    DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
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

    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() throws Exception {
        dictionaryManagement.insertFromFile();
        showAllWords();
        dictionaryManagement.dictionaryLookup();
    }

    public void testDictionary() throws Exception{
        dictionaryManagement.insertFromFile();
        dictionaryManagement.dictionaryExportToFile();
    }

    public void dictionarySearcher() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> result = new ArrayList<String>();
        String wordSearch = scanner.next();
        result = dictionaryManagement.dictionarySubWord(wordSearch);
        if(result.isEmpty()) {
            System.out.println("There's no such word");
        }
        for(String i : result) {
            System.out.print(i + ", ");
        }
        scanner.close();
    } 
}
