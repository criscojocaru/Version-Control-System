package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class RollbackOperation extends VcsOperation {

    /**
     * Log operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public RollbackOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the rollback operation.
     *
     * @param vcs the version control system
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        vcs.getStagedChanges().clear();
        int numberOfBranches = vcs.getBranches().size();
        int numberOfCommits;
        int currentId = 0;

        for (int i = 0; i < numberOfBranches; i++) {
            numberOfCommits = vcs.getBranches().get(i).getBranch().size();
            for (int j = 0; j < numberOfCommits; j++) {
                if (vcs.getBranches().get(i).getBranch().get(j).getId() > currentId) {
                    currentId = vcs.getBranches().get(i).getBranch().get(j).getId();
                }
            }

        }

        for (int i = 0; i < numberOfBranches; i++) {
            numberOfCommits = vcs.getBranches().get(i).getBranch().size();
            for (int j = 0; j < numberOfCommits; j++) {
                if (vcs.getBranches().get(i).getBranch().get(j).getId() == currentId) {
                    vcs.setCurrentBranch(vcs.getBranches().get(i));
                    vcs.setActiveSnapshot(vcs.getBranches().get(i).
                            getBranch().get(j).getActiveSnapshot());
                }
            }
        }
        return ErrorCodeManager.OK;
    }
}
