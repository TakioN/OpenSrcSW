package jblee.project01;

import java.io.FileInputStream;
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
	public void InnerProduct(String filePath, String query) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
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
		
		for(int i = 0; i < qid.length; i++) {
			qid[i] = 0.0;
			Iterator<String> it = kkmaHash.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if(hashMap.containsKey(key)) {
					ArrayList value = (ArrayList)hashMap.get(key);
					qid[i] += (((Number)(kkmaHash.get(key))).doubleValue() * ((Number)(value.get(2 * i + 1))).doubleValue());
				}
				else
					qid[i] += 0.0;
			}
		}
		
		for(int i = 0; i < qid.length; i++)
			System.out.println("Q.id(" + i + ") : " + String.format("%.2f",qid[i]));
		
		double[] sortedQid = qid.clone();
		Arrays.sort(sortedQid);
		
		int[] top = new int[3];
		int flag = 0;
		for(int i = sortedQid.length - 1; i > sortedQid.length - 4; i--) {
			for(int j = 0; j < qid.length; j++) {
				if(sortedQid[i] == qid[j]) {
					top[flag] = j;
					flag++;
				}
				if(flag == 3) break;
			}
			if(flag == 3) break;
		}
		
		System.out.println("<Title Ãâ·Â>");
		
		for(int i = 0; i < top.length; i++ ) {
			Node n = titles.item(top[i]);
			Element e = (Element)n;
			String str = e.getTextContent();
			System.out.println(str);
		}
		
		scan.close();
	}
}
