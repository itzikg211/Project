package presenter;

import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;
import algorithms.mazeGenerators.Maze;

public class Presenter implements Observer
{
	View view;
	Model model;
	
public Presenter(View view,Model model) 
{
	this.view = view;
	this.model = model;
	TestMVPCommand test = new TestMVPCommand();
	view.setCommands(test);
	
}
	
	@Override
	public void update(Observable o, Object arg) 
	{
		Command c = view.getUserCommand();
		c.doCommand("TestMVPCommand");
		Maze m = model.getMaze();
		view.displayMaze(m);

		
	}
	
}
