package gitlet;

// TODO: any imports you need here
import jdk.jshell.execution.Util;

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
    // HashMap of all blobs that the commit tracks
    private HashMap<String, File> blobs = new HashMap<>();
    // Head pointer
    private static Commit head = null;
    // Parents of this commit, transient is so that the commit it points to isn't also serialized or read
    private transient Commit parent1 = null;
    // second parent for merges
    private transient Commit parent2 = null;

    public void Commit(String m, Date d, HashMap<String, File> files) {
        message = m;
        date = d;
        for (File file : blobs.values()) {
            this.blobs.put(Utils.sha1(file), file);
        }
    }

    public static void commit() {

    }

    /* TODO: fill in the rest of this class. */

}
