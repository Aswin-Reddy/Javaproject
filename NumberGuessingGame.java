import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

class GameRound {
    private int roundNumber;
    private int targetNumber;
    private int attempts;
    private long timeTaken;
    private Date startTime;
    private Date endTime;

    public GameRound(int roundNumber, int targetNumber) {
        this.roundNumber = roundNumber;
        this.targetNumber = targetNumber;
        this.attempts = 0;
        this.timeTaken = 0;
        this.startTime = null;
        this.endTime = null;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public int getTargetNumber() {
        return targetNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        this.attempts++;
    }

    public long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(long timeTaken) {
        this.timeTaken = timeTaken;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void calculateTimeTaken() {
        if (startTime != null && endTime != null) {
            this.timeTaken = endTime.getTime() - startTime.getTime();
        }
    }
}

class PlayerProfile {
    private String username;
    private int totalScore;
    private int totalRounds;

    public PlayerProfile(String username) {
        this.username = username;
        this.totalScore = 0;
        this.totalRounds = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void updateScore(int score) {
        this.totalScore += score;
    }

    public void incrementRounds() {
        this.totalRounds++;
    }

    public int getTotalRounds() {
        return totalRounds;
    }
}

public class NumberGuessingGame {

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    private static PlayerProfile currentPlayer;
    private static ArrayList<GameRound> gameHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        displayMenu();

        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    startGame();
                    break;
                case 2:
                    viewInstructions();
                    break;
                case 3:
                    viewStats();
                    break;
                case 4:
                    System.out.println("Thank you for playing! Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("1. Start Game");
        System.out.println("2. View Instructions");
        System.out.println("3. View Stats");
        System.out.println("4. Exit");
    }

    public static void viewInstructions() {
        System.out.println("\n--- Game Instructions ---");
        System.out.println("The game generates a random number.");
        System.out.println("Your goal is to guess the number.");
        System.out.println("After each guess, the game will tell you if your guess is too low, too high, or correct.");
        System.out.println("You can select a difficulty level that changes the range of numbers.");
        System.out.println("You can keep guessing until you find the correct number.");
        System.out.println("Good luck and have fun!");
        System.out.println("-------------------------\n");
    }

    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        currentPlayer = new PlayerProfile(username);
        Difficulty difficulty = chooseDifficulty();

        System.out.println("Game started with difficulty: " + difficulty);

        int rounds = 0;
        int totalScore = 0;

        while (true) {
            rounds++;
            GameRound round = playRound(difficulty, rounds);
            gameHistory.add(round);
            totalScore += calculateRoundScore(round);
            currentPlayer.updateScore(totalScore);
            currentPlayer.incrementRounds();

            System.out.print("Do you want to play another round? (yes/no): ");
            String playAgain = scanner.nextLine();

            if (!playAgain.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("Total score: " + totalScore);
        System.out.println("Total rounds played: " + rounds);
    }

    public static Difficulty chooseDifficulty() {
        Scanner scanner = new Scanner(System.in);
        Difficulty chosenDifficulty = Difficulty.EASY;

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
                break;
        }

        return chosenDifficulty;
    }

    public static GameRound playRound(Difficulty difficulty, int roundNumber) {
        Scanner scanner = new Scanner(System.in);
        int targetNumber = generateTargetNumber(difficulty);
        GameRound round = new GameRound(roundNumber, targetNumber);
        int guess = 0;
        Date startTime = new Date();
        round.setStartTime(startTime);

        while (guess != targetNumber) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
            round.incrementAttempts();

            if (guess < targetNumber) {
                System.out.println("Too low! Try again.");
            } else if (guess > targetNumber) {
                System.out.println("Too high! Try again.");
            } else {
                Date endTime = new Date();
                round.setEndTime(endTime);
                round.calculateTimeTaken();
                System.out.println("Congratulations! You've guessed the correct number in " + round.getAttempts() + " attempts.");
                System.out.println("Time taken: " + round.getTimeTaken() + "ms.");
            }
        }

        return round;
    }

    public static int calculateRoundScore(GameRound round) {
        int score = 1000;
        score -= round.getAttempts() * 10;
        score -= (round.getTimeTaken() / 1000) * 5;

        if (score < 0) {
            score = 0;
        }

        return score;
    }

    public static int generateTargetNumber(Difficulty difficulty) {
        Random random = new Random();
        int targetNumber = 0;

        switch (difficulty) {
            case EASY:
                targetNumber = random.nextInt(50) + 1; 
                break;
            case MEDIUM:
                targetNumber = random.nextInt(100) + 1; 
                break;
            case HARD:
                targetNumber = random.nextInt(200) + 1; 
                break;
        }

        return targetNumber;
    }

    public static void viewStats() {
        if (currentPlayer == null) {
            System.out.println("No player data found. Please start a game first.");
            return;
        }

        System.out.println("Player: " + currentPlayer.getUsername());
        System.out.println("Total Score: " + currentPlayer.getTotalScore());
        System.out.println("Total Rounds Played: " + currentPlayer.getTotalRounds());
        System.out.println("\n--- Game History ---");

        for (GameRound round : gameHistory) {
            System.out.println("Round " + round.getRoundNumber() + " - Target Number: " + round.getTargetNumber() +
                    ", Attempts: " + round.getAttempts() + ", Time Taken: " + round.getTimeTaken() + "ms");
        }
    }
}
