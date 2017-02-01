
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
    public class test 
    {
    	private static void write2xml(List<Node> lst){
    		
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
    		try {
    			builder = factory.newDocumentBuilder();
    			Document document = builder.newDocument();
    			Element tmp = document.createElement("result");
    			for (Node node:lst){
    				if (node.getNodeType()==2){
    					Node imported = document.importNode(node, true);
    					Element ele = document.createElement("Attribute");
    					ele.setAttributeNodeNS((Attr)imported);
    					tmp.appendChild(ele);
    				}
    				else if (node.getNodeType()==9){
    					//System.out.println("doc");
    					TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	                Transformer transformer = transformerFactory.newTransformer();
    	                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    	                transformer.transform(new DOMSource(node), new StreamResult("/Users/rafaliang/code/CSE232B/antlrTutorial/src/output.xml"));
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
                transformer.transform(new DOMSource(tmp), new StreamResult("/Users/rafaliang/code/CSE232B/antlrTutorial/src/output.xml"));
    		}
    		catch (Exception e){System.out.println(e);}
    	          
    	        
    	}
    	
        public static void main( String[] args) throws Exception 
        {
            ANTLRInputStream input = new ANTLRInputStream( System.in);

            XPathLexer lexer = new XPathLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            XPathParser parser = new XPathParser(tokens);
            ParseTree tree = parser.ap(); // begin parsing at rule 'exp'
            ParseTreeWalker walker = new ParseTreeWalker();
            testWalker tw = new testWalker();
            //walker.walk(tw, tree);
            //tw.getResult();
            //testWalker2 tw2 = new testWalker2();
            testWalker3 tw3 = new testWalker3();
            //walker.walk(tw3, tree);
            List<Node> res = tw3.visit(tree);
            //System.out.println(res.size());
            write2xml(res);
            //for (Node node:res)
            	//System.out.println(node.getNodeType());
            System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        }
        
    }
