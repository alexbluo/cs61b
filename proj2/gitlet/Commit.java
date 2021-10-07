
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
    /** The message of this Commit. */
    private String message;
    // Date of commit.
    private Date date;
    // TreeMap of all blobs that the commit tracks (need to distinguish between different versions without getting hash at runtime)
    protected TreeMap<String, Blob> blobs = new TreeMap<>();
    // Master branch not sure if needed/how to use yet
    private String branch;
    // Parents of this commit, transient is so that the commit it points to isn't also serialized or read
    private String parent1;
    // second parent for merges
    private String parent2;

    public Commit(String m, Date d) {
        message = m;
        date = d;
        // who knows what im supposed to do here
        branch = "master";
        if (!Utils.readContentsAsString(Repository.HEAD).equals("")) {
            parent1 = Utils.readContentsAsString(Repository.HEAD);

            this.blobs.putAll(Utils.readObject(Utils.join(Utils.join(Repository.COMMIT_DIR, parent1), "info"), Commit.class).blobs);
        }
        if (!Utils.readContentsAsString(Repository.stagingFile).equals("")) {
            for (Object blob : Utils.readObject(Repository.stagingFile, TreeMap.class).values()) {
                if (blobs.containsValue((Blob) blob)) {
                    blobs.replace(((Blob) blob).getName(), ((Blob) blob));

                } else {
                    blobs.put(((Blob) blob).getName(), ((Blob) blob));
                }
            }
        } else if (!Utils.readContentsAsString(Repository.HEAD).equals("")) {
            System.out.println("No changes added to the commit");
        }
        // persist, keep at end
        try {
            File commitPersist = Utils.join(Repository.COMMIT_DIR, Utils.sha1((Object) Utils.serialize(this)));
            commitPersist.mkdir();
            for (Blob blob : this.blobs.values()) {
                File newFile = Utils.join(commitPersist, Utils.sha1(blob.getContents()));
                newFile.createNewFile();
                Utils.writeContents(newFile, blob.getContents());
            }
            File info = Utils.join(commitPersist, "info");
            Utils.writeObject(info, this);
            Utils.writeContents(Repository.BRANCH, this.branch);
            Utils.writeContents(Repository.HEAD, Utils.sha1((Object) Utils.serialize(this)));
        } catch (GitletException | IOException ex) {
            System.out.println("failure");
            System.out.println(ex.getMessage());
        }
        // clear staging area dir
        for (File file : Objects.requireNonNull(Repository.STAGING_AREA.listFiles())) {
            if (!file.getAbsolutePath().equals(Repository.stagingFile.getAbsolutePath())) {
                file.delete();
            }
        }
        // NOT THIS WTIH CURRENT PUTALL IDEA (or prob not at all)
        Repository.stagingTree.clear();
    }
}
