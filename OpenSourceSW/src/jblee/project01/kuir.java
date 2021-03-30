package jblee.project01;

public class kuir {

	public static void main(String[] args) {
		String command = args[0];
		String path = args[1];

		if (command.equals("-c")) { 
			makeCollection c = new makeCollection();
			c.xmlParsing(path);
		}
		else if(command.equals("-k")) {
			makeKeyword k = new makeKeyword();
			k.kkma(path);
		}
		else if(command.equals("-i")) {
			indexer i = new indexer();
			i.hash(path);
		}
	}

}
