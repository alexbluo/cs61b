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
    public static File savestg = join(STAGING_AREA, "staging");
    protected static LinkedList<Blob> staging = new LinkedList<Blob>();

    public static void init() {
        if (!GITLET_DIR.exists()) {
            try {
                GITLET_DIR.mkdir();
                STAGING_AREA.mkdir();
                COMMIT_DIR.mkdir();
                HEAD.createNewFile();
                BRANCH.createNewFile();
                savestg.createNewFile();
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
        System.out.println(Utils.readObject(savestg, LinkedList.class));
        if (!file.getAbsoluteFile().exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        // hash of file at the time
        String fileHash = Utils.sha1((Object) Utils.serialize(Utils.readContentsAsString(file)));
        // head commit as object
        Commit headCommit = Utils.readObject(Utils.join(Utils.join(COMMIT_DIR, Utils.readContentsAsString(HEAD)), "info"), Commit.class);
        // if current working version of file is identical to the one in head commit do not stage, remove if already in staging
        // (if head containsValue sha1(serialize this)
        // if already in staging then overwrite with new content
        // else just add to staging area normally
        // not implemented - will no longer be staged for removal if it was at the time of command
        // SECOND ELSE PARAMETER SHOULD BE SAME AS RESTRDELETE PARAM, NEED WAY TO REPRESENT FILE PATHED FROM STAGING_AREA
        if (false/*headCommit.blobs.containsKey(file.toString())*/) {
            //problem - cant path to staging area then file in order to check if file exists in staging area

            // head commit contains identical file then delete from staging area if already staged
            //Utils.join(STAGING_AREA, headCommit.blobs.conta).delete();
            // file already exists in staging then overwrite
        } else if (Utils.join(STAGING_AREA, file.toString()).exists()) {
            Utils.readObject(savestg, LinkedList.class).
            headCommit.blobs.containsKey(file);
            File newFile = Utils.join(STAGING_AREA, fileHash);
            Utils.writeContents(newFile, Utils.readContentsAsString(file));
        } else {
            File newFile = Utils.join(STAGING_AREA, fileHash);
            try {
                newFile.createNewFile();

                Utils.writeContents(newFile, Utils.readContentsAsString(file));
                staging.addLast(new Blob(file.toString(), newFile, Utils.readContentsAsString(file)));
                PrintWriter writer = new PrintWriter(newFile);
                writer.print("");
                writer.close();
                Utils.writeObject(savestg, staging);
            } catch (GitletException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}
