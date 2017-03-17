
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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
    			Element tmp = document.createElement("myResult");
    			for (Node node:lst){
    				if (node==null) continue;
    				//System.out.println(node.getTextContent());
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
    	
        private static String readQuery(){
        	String path = "./src/query.txt";
        	String res="";
        	try{
        		File file=new File(path);
        		if(file.isFile() && file.exists()){ 
                    InputStreamReader read = new InputStreamReader(
                    		new FileInputStream(file));
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        res+=(lineTxt+" ");
                    }
                    read.close();
        		}
        	}
    		catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        	
        	
        	return res;
        }
    	
        private static void saveRewrite(String rewrite){
        	String path = "./src/rewrite.txt";
        	try{
        		File file=new File(path);
        		if(file.isFile() && file.exists()){ 
        			//System.out.println("aa");
        			FileOutputStream o = new FileOutputStream(file);
                    o.write(rewrite.getBytes("GBK"));
                    o.close();
        		}
        	}
    		catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
        	return;
        }
        
        public static void main( String[] args) throws Exception 
        {
        	String testcase=readQuery();

        	//rewrite
        	ANTLRInputStream input = new ANTLRInputStream( testcase);
            XQueryLexer lexer = new XQueryLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            XQueryParser parser = new XQueryParser(tokens);
            ParseTree tree = parser.xq();
            //System.out.println(tree.toStringTree(parser));
            queryRewriter2 qw= new queryRewriter2();
            qw.visit(tree);
            String queryRewrite = qw.rewrite();
            if (queryRewrite.equals(""))
            	queryRewrite = testcase;
            saveRewrite(queryRewrite);
            
            //queryRewrite = testcase;
            //System.out.println(x);
            
            // run rewrite query
            ANTLRInputStream inputRewrite = new ANTLRInputStream( queryRewrite);
            XQueryLexer lexerRewrite = new XQueryLexer(inputRewrite);
            CommonTokenStream tokensRewrite = new CommonTokenStream(lexerRewrite);
            XQueryParser parserRewrite = new XQueryParser(tokensRewrite);
            ParseTree treeRewrite = parserRewrite.xq();
            //System.out.println(treeRewrite.toStringTree(parserRewrite)); // print LISP-style tree
            
            
            xpVisitor xpVisitor = new xpVisitor();
            QList res = (QList) xpVisitor.visit(treeRewrite);
            //System.out.println(res.size());
            write2xml(res);
            
            
            
            
            
            
        }
        
    }
