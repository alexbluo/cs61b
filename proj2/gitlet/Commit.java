package gitlet;

// TODO: any imports you need here
import java.io.*;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.*;


/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    // Date of commit.
    private Date date;
    // Tree of commits mapping sha1 to commits
    // figure out how to store commits + blobs, prob have to use date instead or maybe not even a tree
    private static TreeMap<String, Commit> commitMap;

    public void Commit(String m, Date d, ) {

    }
    public static void add(File file) {
        if (!file.exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        Utils.writeContents(Repository.STAGING_AREA, Utils.sha1(file));

    }
    /* TODO: fill in the rest of this class. */
}
