package gitlet;

import edu.princeton.cs.algs4.ST;

import java.io.*;
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

    // big mistake - made stagingTree track the file name and blob instead of hash of contents and blob
    // too lazy to fix but makes some methods O(logn) instead of O(1) lol
    // nvm fine ill fix it
    @SuppressWarnings("unchecked")
    public static void add(File file) {
        if (!file.getAbsoluteFile().exists()) {
            System.out.println("File does not exist");
            System.exit(0);
        }
        // hash of file at the time of add command
        String fileHash = sha1(readContentsAsString(file));

        boolean go = true;
        if (!readContentsAsString(HEAD).equals("") /*&& sha1(((Blob) readObject(stagingFile, TreeMap.class).get(file.toString())).getContents()).eauals(fileHash)*/) {
            for (Blob blob : readObject(join(join(COMMIT_DIR, readContentsAsString(HEAD)), "info"), Commit.class).blobs.values()) {
                if (sha1(blob.getContents()).equals(fileHash)) {
                    go = false;
                    if (!readContentsAsString(stagingFile).equals("")) {
                        for (Object blob2 : readObject(stagingFile, TreeMap.class).values()) {
                            if (((Blob) blob2).getName().equals(blob.getName())) {
                                join(STAGING_AREA, sha1(((Blob) blob2).getContents())).delete();
                            }
                        }
                    }
                }
            }
        }
        //could def cut this down but im too lazy lol
        if ((go && !readContentsAsString(stagingFile).equals("")) && readObject(stagingFile, TreeMap.class).containsKey(file.toString())) {
            stagingTree.replace(file.toString(), new Blob(file.toString(), file, readContentsAsString(file)));
            join(STAGING_AREA, sha1(((TreeMap<String, Blob>) readObject(stagingFile, TreeMap.class)).get(file.toString()).getContents())).delete();
            File newFile = join(STAGING_AREA, fileHash);
            writeContents(newFile, readContentsAsString(file));
        } else if (go) {
            //MIGHT HAVE TO MAKE NEWFILE PATH THE ACTUAL ABSOLUTE PATH INSTEAD OF STAGING/HASH LATER FOR CHECKOUT IDK
            File newFile = join(STAGING_AREA, fileHash);
            try {
                newFile.createNewFile();
                writeContents(newFile, readContentsAsString(file));
                    if (!readContentsAsString(stagingFile).equals("")) {
                        stagingTree.putAll(readObject(stagingFile, TreeMap.class));
                    }
                    stagingTree.put(file.toString(), new Blob(file.toString(), newFile, readContentsAsString(file)));
                    PrintWriter writer = new PrintWriter(stagingFile);
                    writer.print("");
                    writer.close();
                    writeObject(stagingFile, stagingTree);
            } catch (GitletException | IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    //fix first lol
    //actually not sure if needed, check first
    @SuppressWarnings("unchecked")
    public static void rm(File file) {
        try {
            if (readObject(stagingFile, TreeMap.class).containsKey(file.toString())) {
                if (!readContentsAsString(stagingFile).equals("")) {
                    stagingTree.putAll(readObject(stagingFile, TreeMap.class));
                }

                // VERY IMPORTANT FOR OPTIMIZATION - REUSE IN OTHER PLACES
                // path to actual file
                join(STAGING_AREA, sha1(((Blob) readObject(stagingFile, TreeMap.class).get(file.toString())).getContents())).delete();

                stagingTree.remove(file.toString());
                PrintWriter writer = new PrintWriter(stagingFile);
                writer.print("");
                writer.close();
                writeObject(stagingFile, stagingTree);
            }
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            System.out.println("No reason to remove the file.");
        }
    }
}
