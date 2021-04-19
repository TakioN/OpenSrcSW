package jblee.project01;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class kuir {

	public static void main(String[] args) throws ClassNotFoundException, IOException, ParserConfigurationException, SAXException {
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
		else if(command.equals("-s")) {
			searcher s = new searcher();
			s.Calcsim(path, args[3]);

		}
	}

}
