package gitlet;
import static gitlet.Repository.*;
import static gitlet.Utils.*;
import java.util.*;
import java.io.*;

public class Doc {
    /* For methods like log, global log, find, and status
    */

    /*
     Starting at the current head commit, display information about each commit backwards along the commit
     tree until the initial commit, following the first parent commit links, ignoring any second parents
     found in merge commits. For every node in this history, the information it should display is the
     commit id, the time the commit was made, and the commit message.
     */
    public static void log() {
        Commit pos = readObject(join(join(COMMIT_DIR, Utils.readContentsAsString(HEAD)), "info"), Commit.class);
        while (pos != null) {
            System.out.println("===");
            System.out.println(sha1((Object) serialize(pos)));
            System.out.println(pos.date);
            System.out.println(pos.message);
            try {
                pos = readObject(join(join(COMMIT_DIR, pos.parent1), "info"), Commit.class);
            } catch (NullPointerException ex) {
                pos = null;
            }
        }
    }

    public static void globalLog() {
        for (String dir : plainFilenamesIn(COMMIT_DIR)) {
            System.out.println("===");
            System.out.println(sha1((Object) serialize(join(dir, "info"))));
            System.out.println(readObject(join(dir, "info"), Commit.class).date);
            System.out.println(readObject(join(dir, "info"), Commit.class).message);
        }
    }
}
