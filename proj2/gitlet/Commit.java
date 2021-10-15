
package gitlet;

// TODO: any imports you need here
import jdk.jshell.execution.Util;

import java.io.*;
import static gitlet.Utils.*;
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
    protected String message;
    // Date of commit.
    protected Date date;
    // TreeMap of all blobs that the commit tracks (need to distinguish between different versions without getting hash at runtime)
    protected TreeMap<String, Blob> blobs = new TreeMap<>();
    // Branch not sure if needed/how to use yet, prob just write the branch to every commit but more complicated
    private String branch;
    // Parents of this commit
    protected String parent1;
    // second parent for merges
    protected String parent2;

    public Commit(String m, Date d) {
        message = m;
        date = d;
        // who knows what im supposed to do here
        branch = "master";
        if (!readContentsAsString(Repository.HEAD).equals("")) {
            parent1 = readContentsAsString(Repository.HEAD);
            this.blobs.putAll(readObject(join(join(Repository.COMMIT_DIR, parent1), "info"), Commit.class).blobs);
        }
        if (!readContentsAsString(Repository.stagingFile).equals("")) {
            for (Object blob : readObject(Repository.stagingFile, TreeMap.class).values()) {
                blobs.put(((Blob) blob).getName(), ((Blob) blob));
            }
        } else if (!readContentsAsString(Repository.HEAD).equals("")) {
            System.out.println("No changes added to the commit");
        }
        // persist, keep at end
        try {
            File commitPersist = join(Repository.COMMIT_DIR, sha1((Object) serialize(this)));
            commitPersist.mkdir();
            for (Blob blob : this.blobs.values()) {
                File newFile = join(commitPersist, sha1(blob.getContents()));
                newFile.createNewFile();
                writeContents(newFile, blob.getContents());
            }
            File info = join(commitPersist, "info");
            writeObject(info, this);
            writeContents(Repository.BRANCH, this.branch);
            writeContents(Repository.HEAD, sha1((Object) serialize(this)));

            // clear staging area dir
            for (File file : Objects.requireNonNull(Repository.STAGING_AREA.listFiles())) {
                if (!file.getAbsolutePath().equals(Repository.stagingFile.getAbsolutePath())) {
                    file.delete();
                }
            }
            PrintWriter writer = new PrintWriter(Repository.stagingFile);
            writer.print("");
            writer.close();
        } catch (GitletException | IOException ex) {
            System.out.println("failure");
            System.out.println(ex.getMessage());
        }
    }
}
