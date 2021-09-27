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

    public static void init() {
        if (!GITLET_DIR.exists()) {
            try {
                GITLET_DIR.mkdir();
                STAGING_AREA.mkdir();
                COMMIT_DIR.mkdir();
                Date defaultDate = new Date();
                defaultDate.setTime(0);
                Commit firstCommit = new Commit("initial commit", defaultDate);
            } catch (GitletException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
        }
    }
    public static void test() {

    }
    public static void add(File file) {
        if (!file.getAbsoluteFile().exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        String stringf = Utils.sha1((Object) Utils.serialize(file));
        //Utils.readObject(Objects.requireNonNull(COMMIT_DIR.listFiles())[0], Commit.class);
        if (.blobs.containsKey(stringf)) {
            for (File stFile : Objects.requireNonNull(STAGING_AREA.listFiles())) {
                if (stringf.equals(Utils.sha1((Object) Utils.serialize(stFile)))) {
                    Utils.restrictedDelete(stFile);
                    break;
                }
            }
        } else {
            boolean go = true;
            for (File stFile : Objects.requireNonNull(STAGING_AREA.listFiles())) {
                if (file.equals(stFile)) {
                    go = false;
                    File newFile = Utils.join(STAGING_AREA, stringf);
                    Utils.writeContents(newFile, file);
                    break;
                }
            }
            if (go) {
                File newFile = Utils.join(STAGING_AREA, stringf);

                try {
                    newFile.createNewFile();
                } catch (GitletException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
                Utils.writeContents(newFile, Utils.readContentsAsString(file));
            }
        }
    }
}
