
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;


public class PieLanguageParser {

	public PieLanguageParser(){
        Lexicon lexicon = Lexicon.getDefaultLexicon();
        NLGFactory nlgFactory = new NLGFactory(lexicon);
        Realiser realiser = new Realiser(lexicon);
        SPhraseSpec p = nlgFactory.createClause();
        
        
        int message;
        int messageValue;  
        int valueChange; //if there is a slice with the same name, find the difference
        
        valueChange = nodeCompare(node1, node2);
        
        if(messageValue>50){
        	message= 1;
        }
        if(messageValue<5){
        	message= 2;
        }
        if(valueChange>15){
        	message= 3;
        }
        if(valueChange<-15){
        	message= 4;
        }
        
        switch(message){
        	case 1:
        		System.out.println("is the largest element in");
        		p.setVerb("is the largest element in");
        		break;
        	case 2:
        		System.out.println("is the smallest element in");
        		p.setVerb("is the smallest element in");
        		break;
        	case 3:
        		System.out.println("has signficantly increased in");
        		p.setVerb("has signficantly increased in");
        		break;
        	case 4:
        		System.out.println("has signficantly decreased in");
        		p.setVerb("has signficantly decreased in");
        		break;
        }
        
        String[] verbs = {"is the largest element in",
        				  "is the smallest element in",	
        				  "has signficantly increased in",
        				  "has signficantly decreased in"};

        
	      try {	
	    	 //This file will have to be replaced with the XML file being tested
	         File inputFile = new File("PieInput.txt");
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Root element :" 
	            + doc.getDocumentElement().getNodeName());
	         NodeList pList = doc.getElementsByTagName("Piechart");
	         
	         System.out.println("----------------------------");
	         for (int i = 0; i < pList.getLength(); i++) {
		         System.out.println("\nPie Number: " + (i+1));	 
	        	 NodeList nList = doc.getElementsByTagName("slice");
		         for (int temp = 0; temp < nList.getLength(); temp++) {
		            Node nNode = nList.item(temp);
		            System.out.println("\nCurrent Element :" 
		               + nNode.getNodeName());
		            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		               Element eElement = (Element) nNode;
		               System.out.println("Slice ID : " 
		                  + eElement.getAttribute("id"));
		               System.out.println("Label : " 
		                  + eElement
		                  .getElementsByTagName("label")
		                  .item(0)
		                  .getTextContent());
		               System.out.println("Value : " 
		               + eElement
		                  .getElementsByTagName("value")
		                  .item(0)
		                  .getTextContent());
		               p.setSubject(eElement
				                  .getElementsByTagName("label")
				                  .item(0)
				                  .getTextContent());
		               p.setVerb(verbs[1]);
		               p.setObject("Pie Number " + (i+1));
		               String output3 = realiser.realiseSentence(p); 
		               System.out.println(output3);
		            }
		         }
		      }
	         } catch (Exception e) {
		         e.printStackTrace();
		      }
	   
	}
	
	/* Psuedo Code for Increase/Decrease Function
	 * Still needs to be made
	 * Compares a node from one chart to another node of the same name in another
	int nodeCompare(node node1, node node2){
		if(node1.name==node2.name){
			return node1.value - node2.value;
		}
	}
	*/
}
