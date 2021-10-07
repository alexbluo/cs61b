package gitlet;

import edu.princeton.cs.algs4.ST;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    // The staging area: should clear after each
    public static final File STAGING_AREA = join(GITLET_DIR, "staging");
    // Directory to store commits
    public static final File COMMIT_DIR = join(GITLET_DIR, "commits");
    // Branch and head files
    public static final File HEAD = join(GITLET_DIR, "head");
    public static final File BRANCH = join(GITLET_DIR, "branch");
    // HashMap of file name as inputted and actual file path so contents can be read
    public static final File stagingFile = join(STAGING_AREA, "staging");
    protected static TreeMap<String, Blob> stagingTree = new TreeMap<>();

    public static void init() {
        if (!GITLET_DIR.exists()) {
            try {
                GITLET_DIR.mkdir();
                STAGING_AREA.mkdir();
                COMMIT_DIR.mkdir();
                HEAD.createNewFile();
                BRANCH.createNewFile();
                stagingFile.createNewFile();
                Date defaultDate = new Date();
                defaultDate.setTime(0);
                Commit firstCommit = new Commit("initial commit", defaultDate);
            } catch (GitletException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {

            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }
    public static void add(File file) {
        if (!file.getAbsoluteFile().exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        // hash of file at the time of add command
        String fileHash = Utils.sha1((Object) Utils.serialize(Utils.readContentsAsString(file)));

        // if current working version of file is identical to the one in head commit do not stage, remove if already in staging
        // (if head containsValue sha1(serialize this)
        // if already in staging then overwrite with new content
        // else just add to staging area normally

        boolean go = true;
        // not implemented - will no longer be staged for removal if it was at the time of command
        // SECOND ELSE PARAMETER SHOULD BE SAME AS RESTRDELETE PARAM, NEED WAY TO REPRESENT FILE PATHED FROM STAGING_AREA
        if (!Utils.readContentsAsString(HEAD).equals("")) {
            Commit headCommit = Utils.readObject(Utils.join(Utils.join(COMMIT_DIR, Utils.readContentsAsString(HEAD)), "info"), Commit.class);
            for (Blob blob : headCommit.blobs.values()) {
                if (Utils.sha1(blob.getContents()).equals(fileHash)) {
                    go = false;
                    if (Utils.join(STAGING_AREA, fileHash).isFile()) {
                        Utils.join(STAGING_AREA, fileHash).delete();
                    }

                }
            }
        }
        /*if (go && stagingTree.containsKey(file.toString())) {

            stagingTree.replace(file.toString(), new Blob(file.toString(), file, Utils.readContentsAsString(file)));
            Utils.join(STAGING_AREA, Utils.sha1(((TreeMap<String, Blob>) Utils.readObject(stagingFile, TreeMap.class)).get(file.toString()).getContents())).delete();
            File newFile = Utils.join(STAGING_AREA, fileHash);
            Utils.writeContents(newFile, Utils.readContentsAsString(file));
        } else */if (go) {
            File newFile = Utils.join(STAGING_AREA, fileHash);
            try {
                newFile.createNewFile();
                Utils.writeContents(newFile, Utils.readContentsAsString(file));
                stagingTree = Utils.readObject(stagingFile, TreeMap.class);
                // readobject casts to regular treemap holding objects, need to hold extends K and V
                stagingTree.putAll(((TreeMap<String, Blob>) (Utils.readObject(stagingFile, TreeMap.class))));
                stagingTree.put(file.toString(), new Blob(file.toString(), newFile, Utils.readContentsAsString(file)));
                PrintWriter writer = new PrintWriter(stagingFile);
                writer.print("");
                writer.close();
                Utils.writeObject(stagingFile, stagingTree);
            } catch (GitletException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}
