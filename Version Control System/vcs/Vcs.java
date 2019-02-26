package vcs;

import filesystem.FileSystemOperation;
import filesystem.FileSystemSnapshot;
import utils.OutputWriter;
import utils.Visitor;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class Vcs implements Visitor {
    private final OutputWriter outputWriter;
    private FileSystemSnapshot activeSnapshot;
    private ArrayList<String> stagedChanges;
    private ArrayList<Branch> branches;
    private Branch currentBranch;
    private Commit firstCommit;

    /**
     * Vcs constructor.
     *
     * @param outputWriter the output writer
     */
    public Vcs(OutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Does initialisations.
     */
    public void init() {
        this.activeSnapshot = new FileSystemSnapshot(outputWriter);
        stagedChanges = new ArrayList<String>();
        currentBranch = new Branch("master");
        firstCommit = new Commit();
        firstCommit.setMessage("First commit");
        firstCommit.setActiveSnapshot(this.activeSnapshot.cloneFileSystem());
        currentBranch.addCommit(firstCommit);
        branches = new ArrayList<>();
        branches.add(currentBranch);
    }

    /**
     * Visits a file system operation.
     *
     * @param fileSystemOperation the file system operation
     * @return the return code
     */
    public int visit(FileSystemOperation fileSystemOperation) {
        return fileSystemOperation.execute(this.activeSnapshot);
    }

    /**
     * Visits a vcs operation.
     *
     * @param vcsOperation the vcs operation
     * @return return code
     */
    @Override
    public int visit(VcsOperation vcsOperation) {
        return vcsOperation.execute(this);
    }

    /**
     * Gets the staged changes.
     *
     * @return stagedChanges
     */
    public ArrayList<String> getStagedChanges() {
        return stagedChanges;
    }

    /**
     * Adds a staged change.
     *
     * @param stagedChange the change to be staged
     */
    public void addStagedChange(String stagedChange) {
        this.stagedChanges.add(stagedChange);
    }

    /**
     * Gets the output writer.
     *
     * @return outputWriter
     */
    public OutputWriter getOutputWriter() {
        return outputWriter;
    }

    /**
     * Gets the active file system snapshot.
     *
     * @return activeSnapshot
     */
    public FileSystemSnapshot getActiveSnapshot() {
        return activeSnapshot;
    }

    /**
     * Sets the active file system snapshot.
     *
     * @param activeSnapshot the active file system snapshot to be set
     */
    public void setActiveSnapshot(FileSystemSnapshot activeSnapshot) {
        this.activeSnapshot = activeSnapshot;
    }

    /**
     * Gets current branch.
     *
     * @return currentBranch
     */
    public Branch getCurrentBranch() {
        return currentBranch;
    }

    /**
     * Sets current branch.
     *
     * @param currentBranch the branch to be set as current
     */
    public void setCurrentBranch(Branch currentBranch) {
        this.currentBranch = currentBranch;
    }

    /**
     * Gets the branches of the vcs.
     *
     * @return branches
     */
    public ArrayList<Branch> getBranches() {
        return branches;
    }

    /**
     * Adds a branch to the vcs.
     *
     * @param branch the branch to be added
     */
    public void addBranch(Branch branch) {
        this.branches.add(branch);
    }
}
