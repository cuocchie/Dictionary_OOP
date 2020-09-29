
import java.util.ArrayList;
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

    public void addWord() {
        Scanner scanner = new Scanner(System.in);
        String addingWordTarget = scanner.next();
        scanner.nextLine();
        String addingWordExplain = scanner.nextLine();
        scanner.close();
        Word newWord = new Word(addingWordTarget, addingWordExplain);
        dictionary.setWord(newWord);
    }

    public void updateWord() {
        Scanner scanner = new Scanner(System.in);
        String updateWordTarget = scanner.next();
        for(Word i : dictionary.getShelfWords()) {
            if(i.compareWordTarget(updateWordTarget)) { 
                while(true) {
                    System.out.println("Press 1 to update English Word\nPress 2 to update Vietnamese meaning\nPress 3 to update both");
                    int command = scanner.nextInt();
                    if(command == 1) {
                        String updateEnglish = scanner.next();
                        i.setWordTarget(updateEnglish);
                        break;
                    } else if(command == 2) {
                        scanner.nextLine();
                        String updateMeaning = scanner.nextLine();
                        i.setWordExplain(updateMeaning);
                        break;
                    } else if(command == 3) {
                        String updateEnglish = scanner.next();
                        i.setWordTarget(updateEnglish);
                        scanner.nextLine();
                        String updateMeaning = scanner.nextLine();
                        i.setWordExplain(updateMeaning);
                        break;
                    } else {
                        System.out.println("Invalid Command");
                    }
                }
            }
        }
        scanner.close();
    }

    public void deleteWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhap tu can xoa: ");
        String deleteWordTarget = scanner.next();
        Word deletedWord = new Word(" ", " ");
        for(Word i : dictionary.getShelfWords()) {
            if(i.compareWordTarget(deleteWordTarget)) {
                deletedWord = i;
            }
        }
        dictionary.deleteWord(deletedWord);
        scanner.close();
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
        File inputFile = new File("dictionaries.txt");
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String wordLine = scanner.nextLine();
            int tabIndex = wordLine.indexOf("  ");
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
        System.out.println(dictionary.findWord(lookUpWord));
        scanner.close();
    }

    public ArrayList<String> dictionarySubWord(String subWord) {
        return dictionary.findSubWord(subWord);
    }

    public void dictionaryExportToFile() throws Exception {
        FileWriter output = new FileWriter("writtenDictionary.txt");
        ArrayList<Word> temp = new ArrayList<Word>();
        temp = dictionary.getShelfWords();
        for(Word i : temp) {
            output.write(i.getWordTarget() + "\t" + i.getWordExplain() + "\n");
        }
        output.close();
    }
}
