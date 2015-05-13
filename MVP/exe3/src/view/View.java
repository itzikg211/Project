package view;

import presenter.Command;
import presenter.TestMVPCommand;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public interface View 
{
	void start();
	void setCommands(TestMVPCommand c);
	Command getUserCommand();
	void displayMaze(Maze m);
	void displaySolution(Solution s);
}
