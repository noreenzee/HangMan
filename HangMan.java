
package hangman;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class HangMan
{
    private static final String[] WORDS = { "javascript", "declaration", "object", "class", "failing" };
    // number of wrong guesses allowed
    private static final int MAX_TRIES = 6; 
    private static char[] alphabet;
    // underscores for hidden letters
    private static char[] currentWord;  
    private static String wordToGuess;
    private static int incorrectGuesses;

    public static void main(String[] args)
    {
        initializeGame();
        playGame();
    }

    // Initializes the game with a random word and initialize variables
    private static void initializeGame() {
        wordToGuess = getRandomWord();
        alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        currentWord = new char[wordToGuess.length()];
        Arrays.fill(currentWord, '_');
        incorrectGuesses = 0;
    }

    // Retrieves a random word from the WORDS array
    private static String getRandomWord() {
        Random random = new Random();
        return WORDS[random.nextInt(WORDS.length)];
    }

    // Plays the game by asking for guesses and updating the state until the game is won or lost
    private static void playGame() {
        Scanner scanner = new Scanner(System.in);
        
        while (incorrectGuesses < MAX_TRIES && !isWordGuessed()) {
            displayCurrentState();
            System.out.print("Guess a letter: ");
            char guess = scanner.nextLine().charAt(0);
            processGuess(guess);
        }
        
        displayFinalResult();
        scanner.close();
    }

    // Display current game state including gallows, alphabet, and word with guessed letters
    private static void displayCurrentState() {
        displayGallows();
        System.out.println("Available letters: " + new String(alphabet));
        System.out.println("Current word: " + new String(currentWord));
    }

    // Updates the state based on the user's guess
    private static void processGuess(char guess) {
        if (wordToGuess.contains(String.valueOf(guess))) {
            updateCurrentWord(guess);
        } else {
            incorrectGuesses++;
            System.out.println("Incorrect guess.");
        }
        removeLetterFromAlphabet(guess);
    }

    // Removes guessed letter from alphabet array
    private static void removeLetterFromAlphabet(char letter) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == letter) {
                alphabet[i] = '_';
                break;
            }
        }
    }

    // Uncover guessed letter in the currentWord array
    private static void updateCurrentWord(char letter) {
        char[] wordChars = explode(wordToGuess);
        for (int i = 0; i < wordChars.length; i++) {
            if (wordChars[i] == letter) {
                currentWord[i] = letter;
            }
        }
    }

    // explode method - converts word to a character array
    private static char[] explode(String word) {
        return word.toCharArray();
    }

    // Check if the player has guessed the word
    private static boolean isWordGuessed() {
        return wordToGuess.equals(new String(currentWord));
    }

    // Displays the final result
    private static void displayFinalResult() {
        if (isWordGuessed()) {
            System.out.println("Congratulations! You've guessed the word: " + wordToGuess);
        } else {
            System.out.println("Game over! The correct word was: " + wordToGuess);
            displayGallows();
        }
    }

    // Display gallows based on incorrect guesses
    private static void displayGallows() {
        System.out.println("----------");
        System.out.println("|     |");
        System.out.println("|     " + (incorrectGuesses >= 1 ? "O" : ""));
        System.out.println("|    " + (incorrectGuesses >= 3 ? "/" : " ") + (incorrectGuesses >= 2 ? "|" : "") + (incorrectGuesses >= 4 ? "\\" : ""));
        System.out.println("|    " + (incorrectGuesses >= 5 ? "/" : "") + " " + (incorrectGuesses >= 6 ? "\\" : ""));
        System.out.println("|");
    }
    
}
