package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class BranchOperation extends VcsOperation {

    /**
     * Branch operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public BranchOperation(OperationType type, ArrayList<String> operationArgs) {

        super(type, operationArgs);
    }

    /**
     * Executes the branch operation.
     *
     * @param vcs the version control system
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        int numberOfBranches = vcs.getBranches().size();

        for (int i = 0; i < numberOfBranches; i++) {
            if (vcs.getBranches().get(i).getName().equals(this.operationArgs.get(0))) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        }

        Branch branch = new Branch(this.operationArgs.get(0));
        Commit newCommit = new Commit();
        newCommit.setActiveSnapshot(vcs.getActiveSnapshot());
        branch.addCommit(newCommit);
        vcs.addBranch(branch);

        return ErrorCodeManager.OK;
    }
}
