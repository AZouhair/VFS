package CLI;

public class Help extends Command{

    public Help(String commandName, String commandArguments) {
        super(commandName, commandArguments);
    }

    @Override
    public void executeCommand() {

        System.out.println("List of commands available :"
                + "\nadd_user"
                + "\ncat"
                + "\ncd"
                + "\nchange_user"
                + "\nchmod"
                + "\ndelete_user"
                + "\nhelp "
                + "\nls"
                + "\nmkdir"
                + "\nrm"
                + "\nrmdir"
                + "\ntouch"
                + "\nwrite_to_file"
        );

    }

}
