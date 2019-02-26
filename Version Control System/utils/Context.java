package utils;

import vcs.Vcs;

public final class Context {
    private Vcs vcs;
    private static Context instance = null;
    private InputReader inputReader;
    private OutputWriter outputWriter;

    /**
     * Context constructor.
     */
    private Context() {
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }

        return instance;
    }

    /**
     * Initialise the vcs.
     *
     * @param input  the input file
     * @param output the output file
     */
    public void init(String input, String output) {
        inputReader = new InputReader(input);
        outputWriter = new OutputWriter(output);
        vcs = new Vcs(outputWriter);
    }

    /**
     * Runs the context.
     */
    public void run() {
        String operationString = "";
        AbstractOperation operation;
        OperationParser parser = new OperationParser();
        int exitCode;
        boolean wasError;

        this.vcs.init();

        while (true) {
            operationString = this.inputReader.readLine();
            if (operationString == null || operationString.isEmpty()) {
                continue;
            }
            if (operationString.equals("exit")) {
                return;
            }

            operation = parser.parseOperation(operationString);
            exitCode = operation.accept(vcs);
            wasError = ErrorCodeManager.getInstance().checkExitCode(outputWriter, exitCode);

            if (!wasError && this.isTrackable(operation)) {
                switch (operation.type) {
                case CHANGEDIR:
                    vcs.addStagedChange("Changed directory to "
                            + operation.getOperationArgs().get(0));
                    break;
                case MAKEDIR:
                    vcs.addStagedChange("Created directory "
                            + operation.getOperationArgs().get(1));
                    break;
                case REMOVE:
                    vcs.addStagedChange("Removed "
                            + operation.getOperationArgs().get(1));
                    break;
                case TOUCH:
                    vcs.addStagedChange("Created file "
                            + operation.getOperationArgs().get(1));
                    break;
                case WRITETOFILE:
                    vcs.addStagedChange("Added \""
                            + operation.getOperationArgs().get(1) + "\" to file "
                            + operation.getOperationArgs().get(0));
                    break;
                default:
                   break;
                }
            }
        }
    }

    /**
     * Specifies if an operation is vcs trackable or not.
     * You can use it when you implement rollback/checkout -c functionalities.
     * @param abstractOperation the operation
     * @return whether it's trackable or not
     */
    private boolean isTrackable(AbstractOperation abstractOperation) {
        boolean result;

        switch (abstractOperation.type) {
            case CHANGEDIR:
            case MAKEDIR:
            case REMOVE:
            case TOUCH:
            case WRITETOFILE:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }
}
