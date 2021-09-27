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
    // Persistence of commit info NOT NEEDED OR EVEN EFFECTIVE GLOBALLY IG???
    // protected File commitPersist = Utils.join(Repository.COMMIT_DIR, Utils.sha1((Object) Utils.serialize(this)));
    /** The message of this Commit. */
    private final String message;
    // Date of commit.
    private final Date date;
    // HashMap of all blobs that the commit tracks
    protected TreeMap<String, File> blobs = new TreeMap<>();
    // Master branch
    private static Commit branch; // need singletons??
    // Head pointer MIGHT NOT NEED STATIC**
    protected static Commit head;
    // Parents of this commit, transient is so that the commit it points to isn't also serialized or read
    private transient Commit parent1;
    // second parent for merges
    private transient Commit parent2;

    public Commit(String m, Date d) {
        message = m;
        date = d;
        //if (/*can be problematic*/ head != null) {
            for (File file : head.blobs.values()) {
                this.blobs.put(Utils.sha1((Object) Utils.serialize(file)), file);
            }
        //}
        for (File file : Objects.requireNonNull(Repository.STAGING_AREA.listFiles())) {
            if (!blobs.containsKey(Utils.sha1((Object) Utils.serialize(file)))) {
                //abomination here
                blobs.remove(Utils.sha1((Object) Utils.serialize(file)), file);
                //????
            } else {
                this.blobs.put(Utils.sha1((Object) Utils.serialize(file)), file);
            }
        }
        // doesnt work
        parent1 = head;
        head = this;
        // clear staging area dir
        for (File file : Objects.requireNonNull(Repository.STAGING_AREA.listFiles())) {
            file.delete();
        }
        // persist, keep at end
        try {
            File commitPersist = Utils.join(Repository.COMMIT_DIR, Utils.sha1((Object) Utils.serialize(this)));
            System.out.println(commitPersist);
            System.out.println(commitPersist.createNewFile());
            Utils.writeObject(commitPersist, this);

        } catch (GitletException | IOException ex) {
            System.out.println("failure");
            System.out.println(ex.getMessage());
        }
    }


}
