
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.antlr.v4.runtime.*;
    import org.antlr.v4.runtime.tree.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import value.QList;
    public class test 
    {
    	private static void write2xml(QList lst){
    		
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			String fileName = "./src/output.xml";
    		try {
    			builder = factory.newDocumentBuilder();
    			Document document = builder.newDocument();
    			Element tmp = document.createElement("result");
    			for (Node node:lst){
    				if (node==null) continue;
    				// attribute node
    				if (node.getNodeType()==2){
    					Node imported = document.importNode(node, true);
    					Element ele = document.createElement("Attribute");
    					ele.setAttributeNodeNS((Attr)imported);
    					tmp.appendChild(ele);
    				}
    				// text node
    				else if (node.getNodeType()==3){
    					Node imported = document.importNode(node, true);
    					Element ele = document.createElement("TEXT");
    					ele.appendChild(imported);
    					tmp.appendChild(ele);
    				}
    				// document node
    				else if (node.getNodeType()==9){
    					//System.out.println("doc");
    					TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	                Transformer transformer = transformerFactory.newTransformer();
    	                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	                transformer.transform(new DOMSource(node), new StreamResult(fileName));
    	                return;
    				}
    				else{
    					Node imported = document.importNode(node, true);
    					tmp.appendChild(imported);
    				}
        		}
    			TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(tmp), new StreamResult(fileName));
    		}
    		catch (Exception e){System.out.println(e);}
    	          
    	        
    	}
    	
        public static void main( String[] args) throws Exception 
        {
        	
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actor let $y:=$x/.., $z:=$y/*/text() where $z='Michael Caine' return $x/..";
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actors where some $y in $x/actor/text(), $z in $y/../age/text() satisfies $y='Michael Caine' and $z='41' return $x";
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actors let $y:=$x/actor/text(),$z:=$y/../age/text() where $y='Michael Caine' and $z='41' return $x";
        	String testcase = "<test>{for $x in doc(\"./src/a.xml\")//actor/../../.. return $x}</test>";
        	//ANTLRInputStream input = new ANTLRInputStream( System.in);
        	//String testcase = "for $id in \"ids\" return $id";
        	ANTLRInputStream input = new ANTLRInputStream( testcase);
            XQueryLexer lexer = new XQueryLexer(input);
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            XQueryParser parser = new XQueryParser(tokens);
            //ParseTree tree = parser.ap(); // begin parsing at rule 'ap'
            ParseTree tree = parser.xq();
            
            
            xpVisitor xpVisitor = new xpVisitor();
            QList res = (QList) xpVisitor.visit(tree);
            write2xml(res);
            
            
            System.out.println(tree.toStringTree(parser)); // print LISP-style tree
            
            
            
        }
        
    }
