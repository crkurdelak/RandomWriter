import java.util.HashMap;

public class RandomWriter {
    public static void main(String[] args) {
        // TODO implement program
        // TODO update README

        // takes command line arguments int k and int n

        // feed two .txt files
        // prefix length = k
        // build language model
        // each length-k prefix corresponds to an arraylist of all characters that appear
        // immediately after it

        // use language model to generate n characters of text
        // choose initial prefix at random
        // look up entry in map
        // choose at random one element from the array and print it, for up to n characters
        // update prefix by dropping 0th character and appending generated output character
        // repeat until n characters have been generated

        // print to System.Out
    }
}

