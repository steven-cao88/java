import java.util.*;

public class MazeRunner {
    private Scanner scanner;
    private Maze myMap;
    private int moves; // number of moves taken so far

    // direction symbols
    private static final String RIGHT = "R";
    private static final String LEFT = "L";
    private static final String UP = "U";
    private static final String DOWN = "D";

    // threshold of warning levels (in steps)
    private static final int WARNING_LEVEL = 50;
    private static final int ALERT_LEVEL = 75;
    private static final int DANGER_LEVEL = 90;
    private static final int LIMIT_LEVEL = 100;

    public MazeRunner() {
        myMap = new Maze();
        scanner = new Scanner(System.in);
        moves = 0;
    }

    public static void main(String[] args) {
        MazeRunner mazeRunner = new MazeRunner();
        mazeRunner.intro();
        boolean winFlag = mazeRunner.play();
        if (winFlag) {
            System.out.println("Congratulations, you made it out alive!");
            System.out.println("You did it in " + mazeRunner.moves + " moves");
        } else {
            System.out.println("Sorry, but you didn't escape in time- you lose!");
        }

    }

    public void intro() {
        System.out.println("Welcome to Maze Runner!");
        System.out.println("Here is your current positions:");
        this.myMap.printMap();
    }

    public String userMove() {
        String answer = "";
        do {
            System.out.println("Where would you like to move? (" + RIGHT + ", " + LEFT + ", " + UP + ", " + DOWN + ") ");
            answer = this.scanner.next();
        } while (!(answer.equalsIgnoreCase(RIGHT) || answer.equalsIgnoreCase(LEFT)
                || answer.equalsIgnoreCase(UP) || answer.equalsIgnoreCase(DOWN)));
        return answer.toUpperCase();
    }

    public void move(String direction) {
        if (!navigatePit(direction)) {
            if (direction.equalsIgnoreCase(RIGHT) && this.myMap.canIMoveRight()) {
                this.myMap.moveRight();
            } else if (direction.equalsIgnoreCase(LEFT) && this.myMap.canIMoveLeft()) {
                this.myMap.moveLeft();
            } else if (direction.equalsIgnoreCase(UP) && this.myMap.canIMoveUp()) {
                this.myMap.moveUp();
            } else if (direction.equalsIgnoreCase(DOWN) && this.myMap.canIMoveDown()) {
                this.myMap.moveDown();
            } else {
                System.out.println("Sorry, you’ve hit a wall.");
            }
            updateStatus();
        }
    }

    // return true if jump over pit
    public boolean navigatePit(String direction) {
        String jumpAnswer = "";
        if (this.myMap.isThereAPit(direction)) {
            System.out.println("Watch out! There's a pit ahead, jump it?");
            jumpAnswer = scanner.next();

            if (jumpAnswer.equalsIgnoreCase("Y")) {
                this.myMap.jumpOverPit(direction);
                updateStatus();
                return true;
            }
        }
        return false; // there is no pit or user does not want to jump over pit
    }

    // keep playing until limit of steps reaches
    // return win or not
    public boolean play() {
        String direction = "";
        String movesMessage = "";
        while (!this.myMap.didIWin()) {
            direction = userMove();
            move(direction);
            movesMessage = this.movesMessage();
            if (movesMessage.length() > 0) {
                System.out.println(movesMessage);
            }
            if (this.moves >= LIMIT_LEVEL) {
                return false;
            }
        }
        return true;

    }

    // return warning message when each warning level reaches
    public String movesMessage() {
        switch (this.moves) {
            case WARNING_LEVEL:
                return "Warning: You have made " + WARNING_LEVEL + " moves, you have " + (LIMIT_LEVEL - WARNING_LEVEL)
                        + " remaining before the maze exit closes";
            case ALERT_LEVEL:
                return "Alert! You have made " + ALERT_LEVEL + " moves, you only have " + (LIMIT_LEVEL - ALERT_LEVEL)
                        + " moves left to escape.";
            case DANGER_LEVEL:
                return "DANGER! You have made " + DANGER_LEVEL + "  moves, you only have " + (LIMIT_LEVEL - DANGER_LEVEL)
                        + " moves left to escape!!";
            case LIMIT_LEVEL:
                return "Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[";
            default:
                return "";
        }
    }

    // update steps and display maze
    public void updateStatus() {
        this.moves++;
        this.myMap.printMap();
        System.out.println("You've used " + this.moves + " out of " + LIMIT_LEVEL + " steps");
    }
}
