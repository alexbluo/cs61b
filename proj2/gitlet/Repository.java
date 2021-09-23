package gitlet;

import java.io.File;
import java.io.IOException;

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
    // The staging area: contains only one file at a time
    public static final File STAGING_AREA = join(GITLET_DIR, "staging");
    /* TODO: fill in the rest of this class. */

    public static void init() {
        if (!GITLET_DIR.exists()) {
            try {
                GITLET_DIR.mkdir();
                STAGING_AREA.createNewFile();
            } catch (GitletException | IOException ex) {
                ex.getMessage();
            }
        }
        // make first commit
        // Commit firstCommit = new Commit();
        // put in commitTree from commit class: Utils.sha1(firstCommit);


    }


}
