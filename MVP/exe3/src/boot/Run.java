package boot;

import model.MyModel;
import presenter.Presenter;
import view.MyView;

public class Run {

	public static void main(String[] args) 
	{
		//my Program
		//System.out.println("hello");
		MyModel m = new MyModel();
		MyView v = new MyView();
		Presenter p = new Presenter(v,m);
		m.addObserver(p);
		v.addObserver(p);
		v.start();

	}

}
