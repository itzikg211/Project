package boot;

import presenter.Presenter;
import presenter.TestMVPCommand;
import model.MyModel;
import view.MyView;

public class Run {

	public static void main(String[] args) 
	{
		//System.out.println("hello");
		MyModel m = new MyModel();
		MyView v = new MyView();
		Presenter p = new Presenter(v,m);
		m.addObserver(p);
		v.addObserver(p);
		v.start();

	}

}
