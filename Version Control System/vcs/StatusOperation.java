package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class StatusOperation extends VcsOperation {

    /**
     * Status operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public StatusOperation(OperationType type, ArrayList<String> operationArgs) {
        super(type, operationArgs);
    }

    /**
     * Executes the status operation.
     *
     * @param vcs the version control system
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        vcs.getOutputWriter().write("On branch: " + vcs.getCurrentBranch().getName() + "\n");
        vcs.getOutputWriter().write("Staged changes:");

        if (vcs.getStagedChanges() != null) {
            vcs.getOutputWriter().write("\n");
            for (int i = 0; i < vcs.getStagedChanges().size(); i++) {
                vcs.getOutputWriter().write("\t" + vcs.getStagedChanges().get(i) + "\n");
            }
        }
        return ErrorCodeManager.OK;
    }
}
