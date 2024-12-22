import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    // Enum for difficulty levels
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    // Method to generate the random number based on difficulty
    public static int generateTargetNumber(Difficulty difficulty) {
        Random random = new Random();
        int targetNumber = 0;

        switch (difficulty) {
            case EASY:
                targetNumber = random.nextInt(50) + 1; // 1-50 range
                break;
            case MEDIUM:
                targetNumber = random.nextInt(100) + 1; // 1-100 range
                break;
            case HARD:
                targetNumber = random.nextInt(200) + 1; // 1-200 range
                break;
        }

        return targetNumber;
    }

    // Method to display the main menu
    public static void displayMenu() {
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("1. Start Game");
        System.out.println("2. View Instructions");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to display the game instructions
    public static void displayInstructions() {
        System.out.println("\n--- Game Instructions ---");
        System.out.println("The game generates a random number.");
        System.out.println("Your goal is to guess the number.");
        System.out.println("After each guess, the game will tell you if your guess is too low, too high, or correct.");
        System.out.println("You can select a difficulty level that changes the range of numbers.");
        System.out.println("You can keep guessing until you find the correct number.");
        System.out.println("Good luck and have fun!");
        System.out.println("-------------------------\n");
    }

    // Method to start the game
    public static void startGame(Difficulty difficulty) {
        Scanner scanner = new Scanner(System.in);
        int targetNumber = generateTargetNumber(difficulty);
        int guess = 0;
        int attempts = 0;

        System.out.println("Game started with difficulty: " + difficulty);
        System.out.println("I have selected a number. Try to guess it!");

        // Game loop
        while (guess != targetNumber) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
            attempts++;

            if (guess < targetNumber) {
                System.out.println("Too low! Try again.");
            } else if (guess > targetNumber) {
                System.out.println("Too high! Try again.");
            } else {
                System.out.println("Congratulations! You've guessed the correct number.");
                System.out.println("It took you " + attempts + " attempts to guess the number.");
            }
        }

        // Ask if the player wants to play again
        System.out.print("Do you want to play again? (yes/no): ");
        String playAgain = scanner.next();
        if (playAgain.equalsIgnoreCase("yes")) {
            startGame(difficulty);
        } else {
            System.out.println("Thank you for playing! Goodbye.");
        }
    }

    // Method to choose difficulty
    public static Difficulty chooseDifficulty() {
        Scanner scanner = new Scanner(System.in);
        Difficulty chosenDifficulty = Difficulty.EASY; // Default difficulty

        System.out.println("\nChoose Difficulty:");
        System.out.println("1. Easy (1-50)");
        System.out.println("2. Medium (1-100)");
        System.out.println("3. Hard (1-200)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                chosenDifficulty = Difficulty.EASY;
                break;
            case 2:
                chosenDifficulty = Difficulty.MEDIUM;
                break;
            case 3:
                chosenDifficulty = Difficulty.HARD;
                break;
            default:
                System.out.println("Invalid choice, defaulting to Easy difficulty.");
                chosenDifficulty = Difficulty.EASY;
                break;
        }

        return chosenDifficulty;
    }

    // Main game loop with menu
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean exitGame = false;

        while (!exitGame) {
            displayMenu();

            choice = scanner.nextInt();

            switch (choice) {
                case 1: // Start the game
                    Difficulty difficulty = chooseDifficulty();
                    startGame(difficulty);
                    break;
                case 2: // View instructions
                    displayInstructions();
                    break;
                case 3: // Exit the game
                    System.out.println("Exiting the game...");
                    exitGame = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
