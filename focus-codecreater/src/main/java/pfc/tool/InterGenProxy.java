package pfc.tool;

public class InterGenProxy extends CommonGen implements InterGen {

	private InterGen interGen;
	private String filename;
	
	public InterGenProxy(InterGen interGen,String filename) {
		this.interGen=interGen;
		this.filename=filename;
	}
	public String gen(String beanname) {
		String str=interGen.gen(beanname);
		bufferwriterToFile(str, filename);
		return str;
	}

}
