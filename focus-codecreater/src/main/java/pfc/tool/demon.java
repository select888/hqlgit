package pfc.tool;

import java.io.File;

public class demon {

	public static void main(String[] args) {
		File directory = new File("");
		String path=directory.getAbsolutePath()+"\\src\\main\\java";
		System.out.println(path);
	}
}
