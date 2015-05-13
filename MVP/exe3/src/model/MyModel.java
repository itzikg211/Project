package model;

import java.util.Observable;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model
{

	@Override
	public void generateMaze(int rows, int cols) 
	{
		// TODO Auto-generated method stub
		System.out.println("generated a maze !");
		setChanged();
		notifyObservers();
		
	}

	@Override
	public Maze getMaze() 
	{
		// TODO Auto-generated method stub
		System.out.println("get maze");
		return null;
	}

	@Override
	public void solveMaze(Maze m) 
	{
		// TODO Auto-generated method stub
		System.out.println("solve maze");
		
	}

	@Override
	public Solution getSolution() 
	{
		// TODO Auto-generated method stub
		System.out.println("get solution of the maze");
		return null;
	}

	@Override
	public void stop() 
	{
		// TODO Auto-generated method stub
		System.out.println("stop");
		
	}
	
}
