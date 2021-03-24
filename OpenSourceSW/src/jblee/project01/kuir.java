package jblee.project01;

public class kuir {

	public static void main(String[] args) {
		String command = args[0];
		String path = args[1];
//		String command = "-k";
//		String path = "C:/SimpleIR/OpenSourceSW/src/data/collection.xml";
		if (command.equals("-c")) { 
			makeCollection col = new makeCollection();
			col.xmlParsing(path);
		}
		else if(command.equals("-k")) {
			makeKeyword k = new makeKeyword();
			k.kkma(path);
		}
	}

}
