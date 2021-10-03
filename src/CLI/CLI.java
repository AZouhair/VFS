package CLI;

import Core.Shell;

import java.util.Scanner;

public class CLI {


    public static void main(String[] args) {

        boolean endRun = false;


        while(!endRun) {
            System.out.println("\nEnter your command... \nType 'help' for a list of the commands available.");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            String[] arguments = input.split(" ");
            if (arguments[0].equals("stop")) {
                System.out.println("\nExit the CLI");
                endRun = true;
            } else {
                Shell.getInstance().ShellResponse(input);
            }

        }
    }
}
