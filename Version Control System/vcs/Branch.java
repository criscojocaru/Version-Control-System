package vcs;

import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public class Branch {
    private ArrayList<Commit> branch;
    private String name;

    /**
     * Branch constructor.
     *
     * @param name the name of the branch
     */
    public Branch(String name) {
        branch = new ArrayList<Commit>();
        this.name = name;
    }

    /**
     * Gets the name of the branch.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds commit to the branch.
     * @param commit the commit to be added
     */
    public void addCommit(Commit commit) {
        branch.add(commit);
    }

    /**
     * Gets the branch.
     * @return branch
     */
    public ArrayList<Commit> getBranch() {
        return branch;
    }
}
