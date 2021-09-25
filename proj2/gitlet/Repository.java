package gitlet;

import java.io.File;
import java.io.IOException;
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
    // HashMap of everything in staging area currently
    protected static HashMap<String, File> stagingArea = new HashMap<>();

    public static void init() {
        if (!GITLET_DIR.exists()) {
            try {
                GITLET_DIR.mkdir();
                STAGING_AREA.mkdir();
                COMMIT_DIR.mkdir();
                Date defaultDate = new Date();
                defaultDate.setTime(0);
                Commit firstCommit = new Commit("initial commit", defaultDate, stagingArea);
            } catch (GitletException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }
    public static void test() {
        File testFile = join(STAGING_AREA, "lol");
        System.out.println(Utils.sha1((Object) Utils.serialize(testFile)));
    }
    public static void add(File file) {
        if (!file.getAbsoluteFile().exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        String stringf = Utils.sha1((Object) Utils.serialize(file));
        // here not reading Commit from a file, get rid of local HashMaps entirely and use Utils.plainFileNamesIn instead bruh
        if (/*Utils.readObject()*/Commit.head.blobs.containsKey(stringf)) {
            if (stagingArea.containsKey(stringf)) {
                stagingArea.remove(stringf);
            }
        } else if (stagingArea.containsValue(file)) {
            File newFile = Utils.join(STAGING_AREA, stringf);
            Utils.writeContents(newFile, stringf);
        } else {
            File newFile = Utils.join(STAGING_AREA, stringf);
            try {
                newFile.createNewFile();
            } catch (GitletException | IOException ex) {
                System.out.println(ex.getMessage());
            }
            stagingArea.put(stringf, file);
            Utils.writeContents(newFile, stringf);
        }
    }


}
