package jblee.project01;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class indexer {
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void hash(String xmlPath) {
		try {
//			String path = "C:/SimpleIR/OpenSourceSW/src/data/index.xml";
			String path = xmlPath;
			String url = "file:///" + path;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(url);
			
			NodeList bodys = doc.getElementsByTagName("body");
			String[][] token = new String[bodys.getLength()][];
			for(int i = 0; i < bodys.getLength(); i++) {
				Node n = bodys.item(i);
				Element bodytxt = (Element)n;
				String str = bodytxt.getTextContent();
				str = str.replaceAll(" ", "");
				token[i] = str.split("#|:|\\s");
			}
		
			HashMap keywordMap = new HashMap();
			HashMap eachTF = new HashMap();
			HashMap eachDF = new HashMap();
			int docsCount = bodys.getLength();		
			
			for(int i = 0; i < token.length; i++) {
				for(int j = 0; j < token[i].length; j += 2) {	
					if(eachDF.containsKey(token[i][j])) {
						eachDF.put(token[i][j], (int)(eachDF.get(token[i][j])) + 1);
					}
					else {
						eachDF.put(token[i][j], 1);
					}					
				}
			}
			for(int i = 0; i < token.length; i++) {
				for(int j = 0; j < token[i].length; j += 2) {
					double w = Integer.parseInt(token[i][j + 1]) * (Math.log((double)docsCount / (int)eachDF.get(token[i][j])));
					if(!(keywordMap.containsKey(token[i][j]))) {
						ArrayList tfidf = new ArrayList();
						for(int k = 0; k < docsCount; k++) {
							tfidf.add(k);
							tfidf.add(0);
						}
						tfidf.set(2 * i + 1, w);
						keywordMap.put(token[i][j], tfidf);	
					}
					else {
						ArrayList reTfidf = (ArrayList)(keywordMap.get(token[i][j]));
						reTfidf.set(2 * i + 1, w);
						keywordMap.put(token[i][j], reTfidf);
					}
				}
			}
			
			//Hashmap : 파일에 객체 저장
			
			FileOutputStream fileStream = new FileOutputStream("C:/SimpleIR/OpenSourceSW/src/data/index.post");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
			objectOutputStream.writeObject(keywordMap);
			objectOutputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
