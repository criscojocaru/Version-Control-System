package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class CommitOperation extends VcsOperation {

    /**
     * Commit operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CommitOperation(OperationType type, ArrayList<String> operationArgs) {

        super(type, operationArgs);
    }

    /**
     * Executes the commit operation.
     *
     * @param vcs the version control system
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        Commit commit = new Commit();
        commit.setActiveSnapshot(vcs.getActiveSnapshot());
        int numberOfArgs = this.operationArgs.size();
        String message = "";

        for (int i = 0; i < numberOfArgs; i++) {
            message += this.operationArgs.get(i);
            if (i != numberOfArgs - 1) {
                message += " ";
            }
        }

        commit.setMessage(message);

        vcs.getCurrentBranch().addCommit(commit);

        if (vcs.getStagedChanges().size() == 0) {
            return ErrorCodeManager.VCS_BAD_CMD_CODE;

        } else {
            vcs.getStagedChanges().clear();
        }

        return ErrorCodeManager.OK;
    }
}
