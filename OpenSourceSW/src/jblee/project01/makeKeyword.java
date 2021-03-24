package jblee.project01;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class makeKeyword {
	public void kkma(String xmlPath) {
		try
		{
//			String path = "C:/SimpleIR/OpenSourceSW/src/data/collection.xml";
			String path = xmlPath;
			String url = "file:///" + path;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(url);
								
			NodeList bodys = doc.getElementsByTagName("body");
			for(int i = 0; i < bodys.getLength(); i++) {
				Node n = bodys.item(i);
				Element bodytxt = (Element)n;
				String str = bodytxt.getTextContent();
				KeywordExtractor ke = new KeywordExtractor();
				KeywordList kl = ke.extractKeyword(str, true);
				String newtxt = "";
				for(int j = 0; j < kl.size(); j++) {
					Keyword kwrd = kl.get(j);
					newtxt += kwrd.getString() + " : " + kwrd.getCnt() + " # ";
				}
				bodytxt.setTextContent(newtxt);
			}
			//XML 파일로 쓰기
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File("C:/SimpleIR/OpenSourceSW/src/data/index.xml")));
			
			transformer.transform(source, result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

//	public void kkma(String path) {
//		// TODO Auto-generated method stub
//		
//	}	
}
