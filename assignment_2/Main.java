import java.util.*;

/**
 * @author Rufina Talalaeva
 *
 * <h1>Spell Autocorrection</h1>
 */

public class Main {
    public static void main(String[] args) {
        //our input stream from console
        Scanner input = new Scanner(System.in);

        /**
         * Run the task for the first task.
         */
        //firstTask(input);

        /*
         * Tests
         * 1.
         * input:
         * 2
         * vladislove vladislav
         * ostankovich ostankovich
         * output:
         * 2
         * 0
         *
         * 2.
         * input:
         * 8
         * gavaa java
         * perinka perl
         * cplusplus c
         * dratuti dotvidaniya
         * idiod idiot
         * paskal oshibka
         * spring winter
         * kotik cat
         * output:
         * 2
         * 4
         * 8
         * 9
         * 1
         * 6
         * 6
         * 4
         */

        /**
         * Run the command for the second task.
         */
        //secondTask(input);

        /*
         * Tests
         * 1.
         * input:
         * 4
         * vlad serega genadiy oleg
         * qwtr
         * output:
         * oleg vlad
         *
         * 2.
         * input:
         * 6
         * f e d c b a
         * w
         * output:
         * a b c d e f
         *
         * 3.
         * input:
         * 6
         * few eeeew dew cew bew auye
         * ew
         * output:
         * bew cew dew few
         */

        /**
         * Run the command for the third task.
         */
        thirdTask(input);

        /*
         * Tests
         * 1.
         * input:
         * vlad top
         * ..  vl_ .. to !!
         * output:
         * ..  vlad_ .. top !!
         *
         * 2.
         * input:
         * d
         * dfs gh
         * output:
         * d d
         *
         * 3.
         * input:
         * hello world
         * .hell.war
         * output:
         * .hello.world
         *
         * 4.
         * input:
         * cats are cool
         * coty ! ar ! cuul
         * output:
         * cats ! are ! cool
         *
         * 5.
         * input:
         * cats cats dogs cool are
         * ehfs e c
         * output:
         * cats are cats
         */

    }

    /**
     * Implements Third Task:
     * Reads the first line and makes from it's content the dictionary.
     * Reads the second line and corrects it, and all punctuation marks
     * and spaces stay the same as in not corrected line.
     *
     * @param in The object that is responsible for input of type Scanner.
     */
    public static void thirdTask(Scanner in){
        // Reads the line of correct words and forms the array out of them.
        String[] firstLine = in.nextLine().split("[^a-z]");

        // Creates a dictionary(word:frequency) from array of correct words.
        Map<String, Integer> dict = makeDictionary(firstLine);

        // Reads the text for autocorrection.
        String secondLine = in.nextLine();

        // Gets the words from the text for following replacement.
        String[] wordsWithMess = secondLine.split("[^a-z]");

        // But wordsWithMess except correct words gets also strings in format "".
        // So we create another list of correct words without mess like "".
        LinkedList<String> words = new LinkedList<>();
        for (String word: wordsWithMess) {
            if(word.equals("")){
                //do nothing
            }else{
                words.addLast(word);
            }
        }

        // Gets the separators from the text.
        String[] separatorsWithMess = secondLine.split("[a-z]");

        // But separatorsWithMess except correct separators gets also strings in format "".
        // So we create another list of correct separators without mess like "".
        LinkedList<String> separators = new LinkedList<>();
        for (String sep: separatorsWithMess) {
            if(sep.equals("")){
                //do nothing
            }else{
                separators.addLast(sep);
            }
        }

        /* Printing result */

        {
            /* Case1:
             * word - separator - ... - separator - word
             * Size of words is bigger than the size of separators by one.
             */
            if (words.size() > separators.size()) {
                // Print all pairs word - separator.
                for (int i = 0; i < separators.size(); ++i) {
                    System.out.print(bestSubstitution(words.get(i), dict).getFirst() + separators.get(i));
                }
                // Print the remaining word.
                System.out.print(bestSubstitution(words.getLast(), dict).getFirst());
            }
            /* Case2:
             * separator - word - ... - word - separator
             * Size of separators is bigger than the size of words by one.
             */
            else if (words.size() < separators.size()) {
                // Print all pairs separator - size.
                for (int i = 0; i < words.size(); ++i) {
                    System.out.print(separators.get(i) + bestSubstitution(words.get(i), dict).getFirst());
                }
                // Print the remaining separator.
                System.out.print(separators.getLast());
            }
            /* Case3:
             * separator - word - ... - word  OR  word - separator - ... - separator
             * Size of separators is the same as the size of words by one.
             */
            else {
                // If the first symbol of the second line is letter,
                // Then it means we have: word - separator - ... - separator.
                if ('a' <= secondLine.charAt(0) && secondLine.charAt(0) <= 'z') {
                    // Print all pairs word - separator.
                    for (int i = 0; i < words.size(); ++i) {
                        System.out.print(bestSubstitution(words.get(i), dict).getFirst() + separators.get(i));
                    }
                }
                // If the first symbol of the second line is not letter,
                // Then it means we have: separator - word - ... - word.
                else {
                    // Print all pairs separator - word.
                    for (int i = 0; i < words.size(); ++i) {
                        System.out.print(separators.get(i) + bestSubstitution(words.get(i), dict).getFirst());
                    }
                }
            }

            //Printing \n at the end to pass tests on codeforces
            System.out.println();
        }
    }

    /**
     * Implements Second Task:
     * Reads the integer N(number of words in dictionary).
     * Reads N following words and putting it in the dictionary.
     * Reads the word to spell-check.
     * Outputs a sequence of possible best substitutions.
     *
     * @param in The object that is responsible for input of type Scanner.
     */
    public static void secondTask(Scanner in){
        // Result string, which will contain the sequence of possible best substitutions.
        String result = "";

        // Reads the number of words in dictionary.
        // And moving the pointer of reading to next line.
        int N = in.nextInt();
        in.nextLine();

        // Reads the line of correct words and forms the array out of them.
        String[] correctWords = in.nextLine().split("[^a-z]");

        // Creates a dictionary(word:frequency) from array of correct words.
        Map<String, Integer> dict = makeDictionary(correctWords);

        // Reads the word which should be corrected.
        String mainWord = in.next();

        // Getting the list of best suggestions of replacing mainWord.
        LinkedList<String> best = bestSubstitution(mainWord, dict);

        // Adding all words from list to the result string.
        for (String current: best) {
            result += current + " ";
        }

        // Deleting last space element.
        result = result.substring(0, result.length() - 1);

        // Printing the result - best suggestions to replace.
        System.out.println(result);
    }

    /**
     * Implements First Task:
     * Reads the integer N(number of pairs of words).
     * Reads N following pairs(misspelled word and correct word).
     * For each pair prints the number of misspellings.
     *
     * @param in The object that is responsible for input of type Scanner.
     */
    public static void firstTask(Scanner in){
        //reads the number of pairs of words
        int N = in.nextInt();


        String s1, s2;
        for (int i = 0; i < N; ++i){
            //input the pair of words
            s1 = in.next();
            s2 = in.next();

            //output how many misspellings in the pair
            System.out.println(estimate(s1,s2));
        }
    }

    /**
     * Creates a dictionary mapping String with Integer. String:word(unique item from list of words) &
     * Integer:frequency(number of appearing th word in list of words) from array of words.
     *
     * @param words Array of words to be added to dictionary.
     * @return Dictionary of words with their frequencies as values.
     */
    public static Map<String, Integer> makeDictionary(String[] words){
        /* Creating a map, which will play role of dictionary.
         * Mapping word with it's frequency in words.
         */
        Map<String, Integer> dict = new LinkedHashMap<>();

        /* Adding words to dictionary.
         * If this word has already appeared in dictionary, then increase it's frequency by one.
         * If has not, then adds this word to dictionary with frequency one(because it appeared once).
         */
        for (String current: words) {
            if(current.equals("")){
                //Do not add 'empty' word to dictionary.
                //So, do nothing.
            }else if(dict.containsKey(current)){
                //If this word is already in the dict, increase the frequency of it.
                dict.put(current, dict.get(current) + 1);
            }else{
                //If this word is not in the dict, then add it to dict with frequency 1.
                dict.put(current, 1);
            }
        }

        return dict;
    }

    /**
     * Finds the best suggestions of replacing the word by another words(correct) from dictionary.
     * If word is already correct, the function will return the same word.
     *
     * @param wordToReplace Word to check.
     * @param dict Dictionary of correct words.
     * @return List of best suggestions to replace the word.
     */
    public static LinkedList<String> bestSubstitution(String wordToReplace, Map<String, Integer> dict){
        /* Creates a priority queue of pairs.
         * The reason is when we insert the pair, it is already put to the right sorted position.
         * The idea of having the priority queue is in taking first best corresponding words from it.
         */
        PriorityQueue<Pair> best = new PriorityQueue<>();

        // Creates the empty list of strings for storing best suggestions as a result.
        LinkedList<String> result = new LinkedList<>();

        // Adding all words from dictionary to our priority queue.
        dict.forEach((word, frequency) -> {
            // Calculates how apart two words are in terms of possible misspellings.
            int error = estimate(word, wordToReplace);

            /* Creates the pair of error and corresponding word from dictionary,
             * And adds the frequency of this word from dictionary.
             */
            Pair temp = new Pair(error, word, frequency);

            /* Adds this pair to our priority queue. Pair is automatically inserted to it's sorted position.
             * Words with least error with wordToReplace are put on the top.
             * If two words have equal error values, then they are sorted by highest frequency of appearing in dict.
             * If two words have equal error values and frequencies, then they are sorted in lexicographic order.
             */
            best.add(temp);
        });

        /* Takes the element with least error(gets the key of the first element,
         * because the first element by our logic has least error value with wordToReplace.
         */
        int minErrorBest = (int) best.peek().getKey();

        /* Takes the element with least error and highest frequency(gets the key of the first element,
         * because the first element by our logic has least error value with wordToReplace and
         * highest frequency out of words with the same error value.
         */
        int maxFreqBest = best.peek().getFrequency();

        // Adds to our list words with the least error with wordToReplace and highest frequency.
        while((!best.isEmpty()) && ((int) best.peek().getKey() == minErrorBest) && (best.peek().getFrequency() == maxFreqBest)){
            Pair t = best.remove();
            result.addLast((String) t.getValue());
        }

        return result;
    }

    /**
     * Specifies how far apart two words are in terms of possible misspellings.
     *
     * <u>Misspellings:</u>
     * • missed letter (e.g. asignment has a missing letter s);
     * • extra letter (e.g. problemo has an extra letter o at the end);
     * • different letter (e.g. vutter has letter v instead of b);
     * • swapping adjacent letters (e.g. presetn has n and t swapped).
     *
     * @param word1 First word.
     * @param word2 Second word.
     * @return Number of misspellings.
     */
    public static int estimate(String word1, String word2){
        int n = word1.length();
        int m = word2.length();
        int[][] mis = new int[n+1][m+1];
        for(int i = 0; i <= n; ++i){
            mis[i][0] = i;
        }
        for(int i = 0; i <= m; ++i){
            mis[0][i] = i;
        }
        for(int i = 1; i <= n; ++i){
            for(int j = 1; j <= m; ++j){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    mis[i][j] = mis[i-1][j-1];
                }else{
                    mis[i][j] = 1 + min(mis[i][j-1], min(mis[i-1][j-1], mis[i-1][j]));
                    if( i > 1 && j > 1){
                        if((word1.charAt(i-1) == word2.charAt(j-2)) && (word1.charAt(i-2) == word2.charAt(j-1))){
                            mis[i][j] = min(mis[i][j], mis[i-2][j-2] + 1);
                        }
                    }
                }
            }
        }
        return mis[n][m];
    }

    /**
     * Finds the minimum out of two integers.
     * If numbers are equal, returns the same number.
     *
     * @param a First integer.
     * @param b Second integer.
     * @return The smallest of a and b.
     */
    private static int min(int a, int b){
        if(a < b) {
            return a;
        } else {
            return b;
        }
    }
}

/**
 * Class that holds mapping Key->Value as pair.
 *
 * <b>Note_1: </b> Frequency is additional field for holding how many such pairs.
 * <b>Note_2: </b> Elements of pair should be Comparable.
 *
 * @param <K> The type of Key - first value for pair.
 * @param <V> The type of Value - second value for pair.
 */
class Pair<K extends Comparable, V extends Comparable> implements Comparable<Pair> {
    private K key;
    private V value;
    private int frequency;

    /* Constructor */

    /**
     * Constructs a Pair by given arguments.
     *
     * @param key First value for pair.
     * @param value Second value for pair.
     * @param frequency Field for holding how many such pairs.
     */
    public Pair(K key, V value, int frequency) {
        this.key = key;
        this.value = value;
        this.frequency = frequency;
    }

    /* Getters */

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public int getFrequency(){
        return frequency;
    }

    /**
     * Compares <i>this</i> pair with other pair according to the precedence:
     * <ol>
     *     <li>Pair with less key comes earlier.</li>
     *     <li>Pair with more frequency comes earlier.</li>
     *     <li>Pair with less value comes earlier.</li>
     * </ol>
     *
     * @param other Pair to compare with.
     * @return a negative integer, zero, or a positive integer as <i>this</i> is less than, equal to, or greater than <i>other</i>.
     */
    @Override
    public int compareTo(Pair other) {
        if(this.getKey().compareTo(other.getKey()) > 0) {
            return 1;
        }else if(this.getKey().compareTo(other.getKey()) < 0){
            return -1;
        }else if(this.getFrequency() - other.getFrequency() > 0){
            return -1;
        }else if(this.getFrequency() - other.getFrequency() < 0){
            return 1;
        }else if(this.getValue().compareTo(other.getValue()) > 0){
            return 1;
        }else if(this.getValue().compareTo(other.getValue()) < 0){
            return -1;

        }else{
            return 0;
        }
    }
}
