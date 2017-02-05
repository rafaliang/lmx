import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import value.QList;

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList;
import org.w3c.dom.Text; 

public class xqueryEvaluator{
	protected xpVisitor visitor;
	protected Stack<QList> nodelstSt;
	
	xqueryEvaluator(xpVisitor visitor, Stack<QList> nodelstSt){
		this.visitor = visitor;
		this.nodelstSt = nodelstSt;
	}
	
	private Node makeElem(List<Node> lst, String tagName){
		Element ele=null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			ele = document.createElement(tagName);
			for (Node n:lst)
				ele.appendChild(n);
		}
		catch(Exception e){System.out.println(e);}
		return ele;
	}
	
	private Node makeText(String text){
		Text txt=null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			txt = document.createTextNode(text);
		}
		catch(Exception e){System.out.println(e);}
		return txt;
	}
	
}