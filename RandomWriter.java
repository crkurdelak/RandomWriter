import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * TODO write summary
 *
 * @author ckurdelak20@georgefox.edu
 */
public class RandomWriter {
    public static void main(String[] args) {
        // TODO implement program
        // TODO update README
        // TODO exit codes

        // takes command line arguments int k and int n
        int k = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        String[] filenames = Arrays.copyOfRange(args, 2, args.length);
        // TODO exit code 1 if missing parameters


        HashMap<String, ArrayList<Character>> languageModel = buildLanguageModel(filenames, k);

        System.out.println("hello.");
        // feed two .txt files
        // prefix length = k
        // build language model
        // each length-k prefix corresponds to an arraylist of all characters that appear
        // immediately after it

        // use language model to generate n characters of text:
        // randomly choose key from language model
        // output key
        // given current key:
        //      prefix = prefix[1:] + value
        //      look up key --> value in language model
        //      given value (possible next chars for key)
        //          randomly choose a value
        //          output value

        // choose initial prefix at random
        // look up entry in map
        // choose at random one element from the array and print it, for up to n characters
        // update prefix by dropping 0th character and appending generated output character
        // repeat until n characters have been generated

        // print to System.Out
    }


    /**
     * Populates a language model using 1 or more files.
     *
     * @param filenames an array containing one or more filenames
     * @param k the prefix length
     * @return the populated language model
     */
    public static HashMap<String, ArrayList<Character>> buildLanguageModel(String[] filenames,
                                                                         int k) {
        HashMap<String, ArrayList<Character>> languageModel = new HashMap();

        for (String filename : filenames) {
            try {
                File inputFile = new File(filename);
                FileReader reader = new FileReader(inputFile);
                StringBuilder currentPrefix = new StringBuilder();

                for (int i = 0; i < k; i++) {
                    currentPrefix.append((char)reader.read());
                }
                while (reader.ready()) {
                    String key = currentPrefix.toString();
                    if (! languageModel.containsKey(key)) {
                        languageModel.put(key, new ArrayList<Character>());
                    }
                    ArrayList<Character> possibleNextChars =
                            (ArrayList<Character>) languageModel.get(key);
                    possibleNextChars.add((char)reader.read());

                    currentPrefix = currentPrefix.append((char)reader.read());
                    currentPrefix = currentPrefix.deleteCharAt(0);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return languageModel;
    }
}

