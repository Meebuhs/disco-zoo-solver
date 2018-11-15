package ui;

/**
 * The java launcher checks whether the main class extends javafx.application.Application and if this is true, requires
 * javafx dependencies to be present as modules. This would require the javafx sdk to be installed on each machine
 * wanting to run this solver. By wrapping the main method in this separate class which does not extend application,
 * this restriction is circumvented and a single jar can be distributed.
 */
public class Main {
    public static void main(String[] args) {
        SolverApp.main(args);
    }
}
