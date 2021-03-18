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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParsing {
	public static void main (String[] args) {
		
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
			Document doc = docBuilder.newDocument();
			
			String path = "C:/SimpleIR/OpenSourceSW/html";
			File dir = new File(path);
			File[] htmllist = dir.listFiles();
			int id = 0;
			
			Element docs = doc.createElement("docs");
			doc.appendChild(docs);
			
			for(File file : htmllist) {
				if(file.isFile()) {
					String fileUrl = "file:///" + path + "/" + file.getName();
					Document html = docBuilder.parse(fileUrl);
					
					Element doc2 = doc.createElement("doc");
					docs.appendChild(doc2);
					
					doc2.setAttribute("id", Integer.toString(id));
					
					Element title = doc.createElement("title");
					NodeList tables = html.getElementsByTagName("title");
					Element htmlTitle = (Element)tables.item(0);
					title.appendChild(doc.createTextNode(htmlTitle.getTextContent()));
					doc2.appendChild(title);
					
					Element body = doc.createElement("body");
					NodeList divs = html.getElementsByTagName("div");
					Element htmlDiv = (Element)divs.item(0);
					NodeList ps = htmlDiv.getChildNodes();
					String str = "";
					for(int i = 0; i < ps.getLength(); i++) {
						Node n = ps.item(i);
						if(n.getNodeType() == Node.ELEMENT_NODE) {
							Element htmlp = (Element)n;
							str += htmlp.getTextContent();
						}
					}
					body.appendChild(doc.createTextNode(str));
					doc2.appendChild(body);
					
					id++;
				}
			}		
			
			//XML 파일로 쓰기
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File("src/data/collection.xml")));
			
			transformer.transform(source, result);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
