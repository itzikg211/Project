package presenter;

public interface Command
{
	 /**
	   * This method does the command by the name of the class.
	   * @param arg <b>(String) </b>This is the parameter to the doCommand method.
	   * @return Nothing.
	   */
	void doCommand(String arg);

}
