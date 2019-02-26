package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class CheckoutOperation extends VcsOperation {

    /**
     * Checkout operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public CheckoutOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the checkout operation.
     *
     * @param vcs the version control system
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        int numberOfBranches = vcs.getBranches().size();
        int currentCommitID = 0;

        if (vcs.getStagedChanges().size() != 0) {
            return ErrorCodeManager.VCS_STAGED_OP_CODE;
        }

        boolean found = false;
        if (operationArgs.size() == 1) {

            for (int i = 0; i < numberOfBranches; i++) {
                if (vcs.getBranches().get(i).getName().equals(this.operationArgs.get(0))) {
                    found = true;
                    vcs.setCurrentBranch(vcs.getBranches().get(i));
                    return ErrorCodeManager.OK;
                }
            }
            if (!found) {
                return ErrorCodeManager.VCS_BAD_CMD_CODE;
            }
        } else if (operationArgs.size() == 2) {
            for (int i = 0; i < numberOfBranches; i++) {
                int numberOfCommits = vcs.getBranches().get(i).getBranch().size();

                for (int j = 0; j < numberOfCommits; j++) {
                    if (vcs.getBranches().get(i).getBranch().get(j).getId()
                            == Integer.parseInt(this.operationArgs.get(1))) {
                        found = true;
                        currentCommitID = Integer.parseInt(this.operationArgs.get(1));
                        vcs.setCurrentBranch(vcs.getBranches().get(i));
                        vcs.setActiveSnapshot(vcs.getBranches().get(i).
                                getBranch().get(j).getActiveSnapshot());
                    }
                }
            }

            if (!found) {
                return ErrorCodeManager.VCS_BAD_PATH_CODE;
            } else {
                for (int i = 0; i < numberOfBranches; i++) {
                    int numberOfCommits = vcs.getBranches().get(i).getBranch().size();
                    for (int j = 0; j < numberOfCommits; j++) {
                        if (vcs.getBranches().get(i).getBranch().get(j).getId() > currentCommitID) {
                            vcs.getBranches().get(i).getBranch().remove(j);
                        }
                    }
                }
            }
        }
        return ErrorCodeManager.OK;
    }
}
