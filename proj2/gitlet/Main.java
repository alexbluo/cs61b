package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
import java.util.*;
import java.io.*;
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ...
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            System.exit(0);
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                if (args.length != 1) {
                    System.out.print("Incorrect operands.");
                    System.exit(0);
                }
                Repository.init();
                // doc...
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                if (args.length != 2) {
                    System.out.print("Incorrect operands.");
                    System.exit(0);
                }
                Repository.add(new File(args[1]));
                // doc...
                break;
            case "rm":
                if (args.length != 2) {
                    System.out.print("Incorrect operands.");
                    System.exit(0);
                }
                Repository.rm(new File(args[1]));
                break;
            case "commit":
                if (args.length != 2) {
                    System.out.print("Please enter a commit message");
                    System.exit(0);
                }
                Commit newCommit = new Commit(args[1], new Date());
                break;
            // doc...
            case "log":
                if (args.length != 1) {
                    System.out.print("Incorrect operands.");
                    System.exit(0);
                }
                Doc.log();
                break;
            case "global-log":
                if (args.length != 1) {
                    System.out.print("Incorrect operands.");
                    System.exit(0);
                }
                Doc.globalLog();
                break;
            case "find":
                if (args.length != 2) {
                    System.out.print("Incorrect operands.");
                    System.exit(0);
                }
                Doc.find(args[1]);
                break;
                // TODO: IN FUTURE: PLEASE BREAK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            default:
                System.out.println("No command with that name exists.");
                System.out.println(args[0]);
                break;
        }
    }
}

//test