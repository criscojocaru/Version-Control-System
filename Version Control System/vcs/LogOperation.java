package vcs;

import utils.ErrorCodeManager;
import utils.OperationType;
import java.util.ArrayList;

/**
 * @author Cojocaru Cristina-Gabriela
 */
public final class LogOperation extends VcsOperation {

    /**
     * Log operation constructor.
     *
     * @param type          type of the operation
     * @param operationArgs the arguments of the operation
     */
    public LogOperation(OperationType type, ArrayList<String> operationArgs) {

        super(type, operationArgs);
    }

    /**
     * Executes the log operation.
     *
     * @param vcs the version control system
     * @return return code
     */
    @Override
    public int execute(Vcs vcs) {
        int numberOfCommits = vcs.getCurrentBranch().getBranch().size();

        for (int i = 0; i < numberOfCommits; i++) {
            vcs.getOutputWriter().write("Commit id: "
                    + vcs.getCurrentBranch().getBranch().get(i).getId() + "\n");
            vcs.getOutputWriter().write("Message: "
                    + vcs.getCurrentBranch().getBranch().get(i).getMessage() + "\n");
            if (i != numberOfCommits - 1) {
                vcs.getOutputWriter().write("\n");
            }
        }
        return ErrorCodeManager.OK;
    }
}
