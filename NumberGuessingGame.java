package codsoftInterenship;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int roundsPlayed = 0;
        int roundsWon = 0;
        int maxAttempts = 10;  // Limit the number of attempts per round

        System.out.println("Welcome to the Number Guessing Game!");

        while (true) {
            roundsPlayed++;
            int numberToGuess = random.nextInt(100) + 1;  // Generate a random number between 1 and 100
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nRound " + roundsPlayed + ": Guess the number between 1 and 100.");
            
            // Loop for user guesses
            while (attempts < maxAttempts && !guessedCorrectly) {
                System.out.print("Attempt " + (attempts + 1) + " of " + maxAttempts + ". Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                if (userGuess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else if (userGuess > numberToGuess) {
                    System.out.println("Too high! Try again.");
                } else {
                    guessedCorrectly = true;
                    System.out.println("Congratulations! You've guessed the correct number " + numberToGuess + " in " + attempts + " attempts.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you've used all " + maxAttempts + " attempts! The correct number was " + numberToGuess + ".");
            }

            // Check if user won this round
            if (guessedCorrectly) {
                roundsWon++;
            }

            System.out.println("Rounds Played: " + roundsPlayed + ", Rounds Won: " + roundsWon);

            // Ask the user if they want to play another round
            System.out.print("\nDo you want to play another round? (yes/no): ");
            String playAgain = scanner.next();
            if (!playAgain.equalsIgnoreCase("yes")) {
                System.out.println("\nThanks for playing! Your final score:");
                System.out.println("Rounds Played: " + roundsPlayed + ", Rounds Won: " + roundsWon);
                break;
            }
        }

        scanner.close();
    }
}
