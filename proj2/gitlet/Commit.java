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
    private String message;
    // Date of commit.
    private Date date;
    // TreeMap of all blobs that the commit tracks (need to distinguish between different versions without getting hash at runtime)
    protected TreeMap<File, String> blobs = new TreeMap<>();
    // Master branch not sure if needed/how to use yet
    private String branch;
    // Parents of this commit, transient is so that the commit it points to isn't also serialized or read
    private transient Commit parent1;
    // second parent for merges
    private transient Commit parent2;

    public Commit(String m, Date d) {
        message = m;
        date = d;
        if (Utils.join(Repository.COMMIT_DIR, Utils.readContentsAsString(Repository.HEAD)).isFile()) {
            parent1 = Utils.readObject(Utils.join(Repository.COMMIT_DIR, Utils.readContentsAsString(Repository.HEAD)), Commit.class);
        }
        //idek (yet)
        branch = "master";
        // put all file hashes and files from head blobs into this blobs
        if (Utils.join(Repository.COMMIT_DIR, Utils.readContentsAsString(Repository.HEAD)).isFile()) {
            for (File file : parent1.blobs.keySet()) {
                blobs.put(file, parent1.blobs.get(file));
            }
        }
        // for each file in staging area, if this blobs does contain the file and does not contain that file's key then ???
        for (File file : Objects.requireNonNull(Repository.STAGING_AREA.listFiles())) {
            if (blobs.containsKey(file) && !blobs.containsValue(Utils.sha1((Object) Utils.serialize(Utils.readContentsAsString(file))))) {
                blobs.replace(file, Utils.sha1((Object) Utils.serialize(Utils.readContentsAsString(file))));

            } else {
                blobs.put(file, Utils.sha1((Object) Utils.serialize(Utils.readContentsAsString(file))));
            }
        }
        // clear staging area dir
        for (File file : Objects.requireNonNull(Repository.STAGING_AREA.listFiles())) {
            file.delete();
        }
        // persist, keep at end
        try {
            File commitPersist = Utils.join(Repository.COMMIT_DIR, Utils.sha1((Object) Utils.serialize(this)));
            commitPersist.mkdir();
            for (File file : blobs.keySet()) {
                File newFile = Utils.join(commitPersist, file);
                newFile.createNewFile();
                Utils.writeContents(newFile, Utils.readContentsAsString(file));
            }
            Utils.writeObject(commitPersist, this);
            Utils.writeContents(Repository.BRANCH, this.branch);
            Utils.writeContents(Repository.HEAD, Utils.sha1((Object) Utils.serialize(this)));
        } catch (GitletException | IOException ex) {
            System.out.println("failure");
            System.out.println(ex.getMessage());
        }
    }


}
