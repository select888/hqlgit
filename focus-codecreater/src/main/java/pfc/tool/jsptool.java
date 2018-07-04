package pfc.tool;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class jsptool {

	public static void readBufferByLine() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("c:\\demo.txt"));
			String line = null;
			while ((line = br.readLine()) != null) {
				String str="append(content, \""+line.replace("\"", "\\\"")+"\",1);";
				System.out.println(new String(str.getBytes(), "utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new jsptool().readBufferByLine();
	}
}
