public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    // private Dictionary;
    DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    public void showAllWords() {
        Dictionary tempDictionary = new Dictionary(dictionaryManagement.getDictionary());

        System.out.println("No   | English   | Vietnamese");
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
}
