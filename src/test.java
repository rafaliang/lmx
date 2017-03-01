
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
    	
    	public static void main( String[] args) throws Exception 
        {
        	//String testcase = "doc(\"./src/a.xml\")//age/text()/../.. [text()]";
        	//String testcase = "<hello>{\"hello\"}</hello>";
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actor let $y:=$x/.., $z:=$y/*/text() where $z=\'Michael Caine\' return $z";
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actors where some $y in $x/actor/text(), $z in $y/../age/text() satisfies $y='Michael Caine' and $z='41' return $x";
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actors let $y:=$x/actor,$z:=$y/age/text() where $y/text()=\"Michael Caine\" and $z='41' return $x";
        	//String testcase = "for $x in doc(\"./src/a.xml\")//actors where some  $y in $x/actor,$z in $y/age/text() satisfies $y/text()=\"Michael Caine\" and $z='41' return $x";
        	
        	// test cases for xquery (4 in total)
        	//String testcase = "<acts> {	for $a in doc(\"./src/j_caesar.xml\")//ACT where not empty( for $sp in $a/SCENE/SPEECH/SPEAKER where $sp/text() = \"CASCA\" return <speaker> {$sp/text()}</speaker> )return <act>{$a/TITLE/text()}</act>}</acts>";
        	
        	//String testcase="<result>{  for $a in (for $s in doc(\"./src/j_caesar.xml\")//ACT return $s), $sc in (for $t in $a/SCENE return $t), $sp in (for $d in $sc/SPEECH return $d) where $sp/LINE/text() = 'Et tu, Brute! Then fall, Caesar.' return <who>{$sp/SPEAKER/text()}</who>, <when>{ <act>{$a/TITLE/text()}</act>, <scene>{$sc/TITLE/text()}</scene> }</when>}</result>" ;
        	//String testcase = "<result>{ for $a in doc(\"./src/j_caesar.xml\")//PERSONAE, $b in $a/PERSONA where ($b/text() = \'JULIUS CAESAR\') or ($b/text() = 'Another Poet') return $b}</result>" ;
        	//String testcase = "<result>{ for $a in doc(\"./src/j_caesar.xml\")//PERSONAE, $b in $a/PERSONA where not (($b/text() = 'JULIUS CAESAR') or ($b/text() = 'Another Poet') ) return $b}</result>" ;
        	
        	//String testcase = "<result>{ for $a in document(\"./src/j_caesar.xml\")//ACT, $sc in $a//SCENE,$sp in $sc/SPEECH where $sp/LINE/text() eq \"Et tu, Brute! Then fall, Caesar.\" return <who>{$sp/SPEAKER/text()}</who>, <when>{<act>{$a/TITLE/text()}</act>,<scene>{$sc/TITLE/text()}</scene>}</when>}</result>";
        	//String testcase = "for $s in document(\"./src/j_caesar.xml\")//SPEAKER return <speaks>{<who>{$s/text()}</who>, for $a in document(\"./src/j_caesar.xml\")//ACT where some $s1 in $a//SPEAKER satisfies $s1 eq $s return <when>{$a/TITLE/text()}</when>} </speaks>";
        	String testcase=readQuery();
        	
        	         	
        	
        	// DEMOOOOOOOOOOOOOOOOOOOO
        	
        	
        	
        	
        	
        	//ANTLRInputStream input = new ANTLRInputStream( System.in);
        	//String testcase = "for $id in \"ids\" return $id";
        	ANTLRInputStream input = new ANTLRInputStream( testcase);
            XQueryLexer lexer = new XQueryLexer(input);
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            XQueryParser parser = new XQueryParser(tokens);
            //ParseTree tree = parser.ap(); // begin parsing at rule 'ap'
            ParseTree tree = parser.xq();
            System.out.println(tree.toStringTree(parser)); // print LISP-style tree
            
            
            queryRewriter2 qw= new queryRewriter2();
            qw.visit(tree);
            
            //xpVisitor xpVisitor = new xpVisitor();
            //QList res = (QList) xpVisitor.visit(tree);
            
            //write2xml(res);
            
            
            
            
            
            
        }
        
    }
