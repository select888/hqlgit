package pfc.tool;

import java.io.File;

public class test {

	public static void main(String[] args) {
		File directory = new File("");
		String path=directory.getAbsolutePath()+"\\src\\pfc\\platform\\";
		InterGen ig=new ServiceGen();
		InterGenProxy proxy=new InterGenProxy(ig, path+"vstest\\VstestService.java");
		System.out.println(proxy.gen("Vstest"));
		ig=new RepositoryGen();
	    proxy=new InterGenProxy(ig, path+"vstest\\VstestServiceImpl.java");
		System.out.println(proxy.gen("Vstest"));
		ig=new ActionGen();
	    proxy=new InterGenProxy(ig, path+"vstest\\VstestAction.java");
		System.out.println(proxy.gen("Vstest"));
		
		String pagepath=directory.getAbsolutePath()+"\\WebRoot\\pages\\";
		ig=new ListUIGen();
	    proxy=new InterGenProxy(ig, pagepath+"vstest\\vstestlist.jsp");
		System.out.println(proxy.gen("Vstest"));
		ig=new InsertUIGen();
	    proxy=new InterGenProxy(ig, pagepath+"vstest\\vstestinsertUI.jsp");
		System.out.println(proxy.gen("Vstest"));
		ig=new UpdateUIGen();
	    proxy=new InterGenProxy(ig, pagepath+"vstest\\vstestupdateUI.jsp");
		System.out.println(proxy.gen("Vstest"));
		ig=new ViewUIGen();
	    proxy=new InterGenProxy(ig, pagepath+"vstest\\vstestview.jsp");
		System.out.println(proxy.gen("Vstest"));
	}
}
