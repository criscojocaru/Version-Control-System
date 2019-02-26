package vcs;

import filesystem.FileSystemSnapshot;
import utils.IDGenerator;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public class Commit {
    private int id;
    private FileSystemSnapshot activeSnapshot;
    private String message;

    /**
     * Commit constructor.
     *
     */
    public Commit() {
        id = IDGenerator.generateCommitID();
    }

    /**
     * Gets the id of the commit.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the active file system snapshot of the commit.
     * @param activeSnapshot the active snapshot to be set
     */
    public void setActiveSnapshot(FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot.cloneFileSystem();
    }

    /**
     * Gets the active file system snapshot.
     * @return activeSnapshot
     */
    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }

    /**
     * Gets the message of the commit.
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message of the commit.
     * @param message the message to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
