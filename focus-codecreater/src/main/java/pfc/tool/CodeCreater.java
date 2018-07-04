package pfc.tool;

import java.io.File;

public class CodeCreater extends CommonGen{

	
	public static void main(String[] args) {
		//new CodeCreater().execute("CsrSkill");
	}
	public static void execute(String beanname){
		
		//src下包创建
		/*File packagefile=new File(srcpath+beanname.toLowerCase());
		if(!packagefile.exists())packagefile.mkdir();*/
		
		//pages包页面下创建
		File pagepathfile=new File(pagepath+beanname.toLowerCase());
		if(!pagepathfile.exists())pagepathfile.mkdir();
		
	
		InterGen igaction=new ActionGen();
		InterGenProxy proxyigaction=new InterGenProxy(igaction, srcpath+"action\\"+returnUstr(beanname)+"Action.java");
		InterGen igservice=new ServiceGen();
		InterGenProxy proxyigservice=new InterGenProxy(igservice, srcpath+"service\\"+returnUstr(beanname)+"Service.java");
		InterGen igrepository=new RepositoryGen();
		InterGenProxy proxyigrepository=new InterGenProxy(igrepository, srcpath+"repository\\"+returnUstr(beanname)+"Repository.java");
	
		InterGen iglist=new ListUIGen();
		InterGenProxy proxyiglist=new InterGenProxy(iglist, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"list.html");
		
		InterGen iginsert=new InsertUIGen();
		InterGenProxy proxyinsert=new InterGenProxy(iginsert, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"InsertUI.html");
		
		InterGen igupdate=new UpdateUIGen();
		InterGenProxy proxyupdate=new InterGenProxy(igupdate, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"UpdateUI.html");
		
		InterGen igview=new ViewUIGen();
		InterGenProxy proxyview=new InterGenProxy(igview, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"view.html");
		
		InterGen igquery=new QueryUIGen();
		InterGenProxy proxyquery=new InterGenProxy(igquery, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"query.html");
		InterGen igcolumn=new ColumnUIGen();
		InterGenProxy proxycolumn=new InterGenProxy(igcolumn, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"column.html");
		InterGen listlookui=new ListLookUIGen();
		InterGenProxy proxylistlook=new InterGenProxy(listlookui, pagepath+beanname.toLowerCase()+"\\"+beanname.toLowerCase()+"listlook.html");
		
		proxyigaction.gen(beanname);
		proxyigrepository.gen(beanname);
		proxyigservice.gen(beanname);
		proxyiglist.gen(beanname);
		proxycolumn.gen(beanname);
		proxyquery.gen(beanname);
		proxyinsert.gen(beanname);
		proxyupdate.gen(beanname);
		proxyview.gen(beanname);
		proxylistlook.gen(beanname);
		System.out.println(beanname+"生成完毕!");
	}
	
}
