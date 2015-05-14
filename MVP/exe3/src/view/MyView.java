package view;

import java.util.Observable;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;


public class MyView extends Observable implements View{

	@Override
	public void start() 
	{
		// TODO Auto-generated method stub
		System.out.println("start");
		setChanged();
		notifyObservers(1);
	}


	@Override
	public void displayMaze(Maze m) 
	{
		m.print();
		setChanged();
		notifyObservers(2);
	}

	@Override
	public void displaySolution(Solution s) 
	{
		// TODO Auto-generated method stub
		System.out.println("display Solution");
		setChanged();
		notifyObservers(111);
		
	}

}
