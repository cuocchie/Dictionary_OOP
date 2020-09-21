
public class Word {
    private String word_target;
    private String word_explain;

    Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public String getWordTarget() {
        return word_target;
    }

    public String getWordExplain() {
        return word_explain;
    }

    public void setWordTarget(String word_target) {
        this.word_target = word_target;
    }

    public void setWordExplain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String calculateSpaces(String word_target) {
        String spaces = "";
        int MAX_SPACES = 10;
        for (int i = 0; i < MAX_SPACES - word_target.length(); i++) {
            spaces += " ";
        }
        return spaces;
    }

    public void showWord() {
        System.out.print("| " + word_target);
        System.out.print(calculateSpaces(word_target) + "| " + word_explain);
    }
}
