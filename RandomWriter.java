import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Procedurally generates text based on one or more text files.
 *
 * Exits with code 0 on success, 1 if insufficient or invalid arguments are provided, or 2 if the
 * input text file(s) do not contain sufficient characters to satisfy k or n.
 *
 * @author ckurdelak20@georgefox.edu
 */
public class RandomWriter {
    private static int INVALID_ARGS = 1;
    private static int INSUFFICIENT_CHARACTERS = 2;

    public static void main(String[] args) {
        // TODO implement program
        // TODO update README
        // TODO exit codes

        // takes command line arguments int k and int n
        int k = 0;
        int n = 0;

        try {
            k = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.exit(INVALID_ARGS);
        }

        String[] filenames = Arrays.copyOfRange(args, 2, args.length);
        if (filenames.length == 0) {
            System.exit(INVALID_ARGS);
        }

        HashMap<String, ArrayList<Character>> languageModel = buildLanguageModel(filenames, k);

        System.out.println(generateText(languageModel, n));
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
                    char newChar = (char) reader.read();
                    possibleNextChars.add(newChar);

                    currentPrefix = currentPrefix.append(newChar);
                    currentPrefix = currentPrefix.deleteCharAt(0);
                }
            } catch (FileNotFoundException e) {
                System.exit(INVALID_ARGS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return languageModel;
    }


    /**
     * Procedurally generates text based on a language model.
     *
     * @param languageModel the language model
     * @param n the number of characters to generate
     * @return the generated text
     */
    public static String generateText(HashMap<String, ArrayList<Character>> languageModel, int n) {
        StringBuilder output = new StringBuilder();
        StringBuilder currentPrefix = new StringBuilder();

        // get list of all keys
        List<String> keyList = new ArrayList<String>(languageModel.keySet());
        if (keyList.size() < n) {
            System.exit(INSUFFICIENT_CHARACTERS);
        }
        // random number generator
        Random rng = new Random();
        // select random key
        String firstKey = keyList.get(rng.nextInt(keyList.size()));
        currentPrefix.append(firstKey);
        output.append(currentPrefix.toString());

        // while output.size() < n
        while (output.length() < n) {
            // output currentKey
            String key = currentPrefix.toString();
            // output a random char from its corresponding values list
            if (languageModel.containsKey(key)) {
                ArrayList<Character> valueList = languageModel.get(key);
                char newChar = valueList.get(rng.nextInt(valueList.size()));
                output.append(newChar);
                // append that char to currentKey and remove the first character of currentKey
                currentPrefix.append(newChar);
                currentPrefix.deleteCharAt(0);
            }
            else {
                System.exit(INSUFFICIENT_CHARACTERS); // TODO find out correct thing to do here
            }
        }

        return output.toString();
    }
}

