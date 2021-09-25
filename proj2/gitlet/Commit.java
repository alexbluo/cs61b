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
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    // Persistence of commit info
    protected File commitPersist = Utils.join(Repository.COMMIT_DIR, Utils.sha1((Object) Utils.serialize(this)));
    /** The message of this Commit. */
    private String message;
    // Date of commit.
    private Date date;
    // HashMap of all blobs that the commit tracks
    protected HashMap<String, File> blobs = new HashMap<>();
    // Master branch
    private Commit branch = this;
    // Head pointer MIGHT NOT NEED STATIC**
    protected static Commit head = null;
    // Parents of this commit, transient is so that the commit it points to isn't also serialized or read
    private transient Commit parent1;
    // second parent for merges
    private transient Commit parent2;

    public Commit(String m, Date d, HashMap<String, File> files) {
        message = m;
        date = d;
        for (File file : files.values()) {
            this.blobs.put(Utils.sha1((Object) Utils.serialize(file)), file);
        }
        parent1 = head;
        head = this;

        // clear staging area file as well as HashMap
        try {
            for (File file : Repository.STAGING_AREA.listFiles()) {
                file.delete();
            }
        } catch (NullPointerException ex){

        }
        Repository.stagingArea.clear();

        // persist, keep at end
        try {
            commitPersist.createNewFile();
            Utils.writeObject(commitPersist, this);

        } catch (GitletException | IOException ex) {
            System.out.println("failure");
            System.out.println(ex.getMessage());
        }
    }


}
