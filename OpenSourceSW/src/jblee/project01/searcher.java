package jblee.project01;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class searcher {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Calcsim2(String filePath, String query) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
		Scanner scan = new Scanner(System.in);
		
		String q = query;
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(q, true);
		HashMap kkmaHash = new HashMap();
		for(int i = 0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			kkmaHash.put(kwrd.getString(), 1);
		}
		
		String path = filePath;
		FileInputStream fileStream = new FileInputStream(path);
		ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hashMap = (HashMap)object;
		
		String url = "file:///C:/SimpleIR/OpenSourceSW/src/data/collection.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(url);
		
		NodeList titles = doc.getElementsByTagName("title");
		
		double[] qid = new double[titles.getLength()];
		double[] cosQid = new double[titles.getLength()];
		double kkmaWeight = 0.0; 
		double hashWeight = 0.0; 
		
		for(int i = 0; i < qid.length; i++) {
			qid[i] = 0.0;
			Iterator<String> it = kkmaHash.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				double queryWeight = ((Number)(kkmaHash.get(key))).doubleValue();   //입력한 쿼리의 단어 weight
				if(hashMap.containsKey(key)) {
					ArrayList value = (ArrayList)hashMap.get(key);
					double indexWeight = ((Number)(value.get(2 * i + 1))).doubleValue(); //index.post의 weight
					hashWeight += (indexWeight * indexWeight);
					kkmaWeight += (queryWeight * queryWeight);
					qid[i] += queryWeight * indexWeight;
				}			
			}
		}
		
		for(int i = 0; i < cosQid.length; i++) {
			if(qid[i] == 0)
				cosQid[i] = 0;
			else 
				cosQid[i] = qid[i] / (Math.sqrt(kkmaWeight) * Math.sqrt(hashWeight));
			System.out.println("Sim(Q,id(" + i + ")) = " + String.format("%.2f", cosQid[i]));
		}	
		
		double[] sortedCos = cosQid.clone();
		Arrays.sort(sortedCos);
		int[] top = new int[3];
		
		int flag = 0;
		for(int i = sortedCos.length - 1; i > sortedCos.length - 4; i--) {
			for(int j = 0; j < sortedCos.length; j++) {
				if(sortedCos[i] == cosQid[j]) {
					top[flag] = j;
					flag++;
				}
				if(flag == 3) break;
			}
			if(flag == 3) break;
		}
		

		System.out.println("Title Top 3");
		
		for(int i = 0; i < top.length; i++) {
			Node n = titles.item(top[i]);
			Element e = (Element)n;
			String str = e.getTextContent();
			System.out.println(str);
		}
		
		scan.close();
	}
}
