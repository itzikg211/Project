package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.MazeSearch;
import algorithms.mazeGenerators.DFSMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MazeGenerator;
import algorithms.search.AStar;
import algorithms.search.MazeManhattanDistance;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model
{
	ExecutorService executor;
	Maze maze;
	Solution sol;
	public HashMap<Maze, Solution> msols;
	/*{
        ///////need to do this function
		@Override
		public boolean equals(Object arg0) 
		{
			return true;
		};
	};*/
	
	 /**
	   * This method converts the data in an InputStream to a String.
	   * @param in <b>(InputStream) </b>This is the parameter to the fromStream method
	   * @return s <b>(String) </b>
	   */
	
	public String fromStream(InputStream in) throws IOException
	{
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    StringBuilder out = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) 
	    {
	        out.append(line);
	    }
	    return out.toString();
	}
	
	
	public MyModel() 
	{
		this.executor = Executors.newCachedThreadPool();
		msols = new HashMap<Maze,Solution>();
		
		try 
		{
			InputStream in=new GZIPInputStream(new ObjectInputStream(new FileInputStream("file.txt.zip")));
			String s = fromStream(in);
			in.close();
			OutputStream out= new FileOutputStream("temp.txt");
			out.write(s.getBytes());
			out.flush();
			out.close();
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream("file.txt"));
			/*this.msols = (HashMap<Maze, Solution>) oin.readObject();
			oin.close();*/

		} 
		catch (FileNotFoundException e) 
		{
		
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
		
			e.printStackTrace();
		}
		/*catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		
		
	}
	@Override
	public void generateMaze(int rows, int cols) 
	{
		// TODO Auto-generated method stub
		System.out.println("generated a maze !");
		
		Future<Maze> f  =  executor.submit(new CallableMaze(rows,cols));
		while(!f.isDone())
		{
		}
		setChanged();
		notifyObservers(3);
	}

	@Override
	public Maze getMaze() 
	{
		// TODO Auto-generated method stub
		System.out.println("get maze");
		if(maze==null)
		{
			System.out.println("No maze yet");
			return null;
		}
		return maze;
	}

	@Override
	public void solveMaze(Maze m) 
	{
		// TODO Auto-generated method stub
		System.out.println("solve maze");
		
		if(msols.containsKey(maze))
		{
			sol = msols.get(maze);
			setChanged();
			notifyObservers(4);
		}
		else
		{
			Future<Solution> f  =  executor.submit (new CallableSolution(m));
			while(!f.isDone())
			{
			}
			msols.put(maze, sol);
			setChanged();
			notifyObservers(4);
		}
		
	}

	@Override
	public Solution getSolution() 
	{
		// TODO Auto-generated method stub
		System.out.println("get solution of the maze");
		if(sol==null)
		{
			System.out.println("No solution yet");
			return null;
		}
		return sol;
	}

	@Override
	public void stop() 
	{
		
		GZIPOutputStream zipOut=null;
		ObjectOutputStream in;
		ObjectInputStream out;
		try 
		{
			zipOut=new GZIPOutputStream(new ObjectOutputStream(new FileOutputStream("file.txt"+".zip")));
			in = new ObjectOutputStream(new FileOutputStream("file.txt"));
			in.writeObject(this.msols);///the problem occurs here
			/*out = new ObjectInputStream(new FileInputStream("file.txt"));
			zipOut.write(fromStream(out).getBytes());
			zipOut.flush();
			zipOut.close();
			in.flush();*/
			in.close();
		} 
		catch (FileNotFoundException e1) 
		{
			e1.printStackTrace();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
		
		System.out.println("stop");
		if(executor.isShutdown())
			return;
		executor.shutdown();
		
	}
	
	public class CallableMaze implements Callable<Maze>
	{
		int rows;
		int cols;
		@Override
		public Maze call() throws Exception 
		{
			MazeGenerator mg=new DFSMazeGenerator();
			maze = mg.generateMaze(rows,cols);
			return maze;
		}
		public CallableMaze(int rows,int cols) 
		{
			this.rows = rows;
			this.cols = cols;
		}
		
	}
	
	public class CallableSolution implements Callable<Solution>
	{
		Maze m;
		public CallableSolution(Maze m) 
		{
			this.m = m;
		}

		@Override
		public Solution call() throws Exception 
		{
			System.out.println("\nSolution A* without diagonals");
			MazeSearch ams1 = new MazeSearch(m,false);
			AStar sol5 = new AStar();
			sol5.setH(new MazeManhattanDistance());
			sol = sol5.search(ams1);
			return sol;
		}
	
	}
	
	
	
}
