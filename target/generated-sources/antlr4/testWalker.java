import java.io.File;
import java.util.ArrayList;
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

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 

public class testWalker extends XPathBaseListener{
	private Document doc;
	private Queue<Boolean> singleSL = new LinkedBlockingQueue<Boolean>();
	//private List<String> res = new ArrayList<String>();
	//NodeList nl;
	private Boolean inF = false;
	private List<Node> nodeList = new ArrayList<Node>();
	private Map<Integer, String> typeMap=new HashMap<Integer, String>();
	
	testWalker(){
		typeMap.put(1, "element");
		typeMap.put(2, "attribute");
		typeMap.put(3, "text");
		typeMap.put(9, "document");
		//System.out.println(typeMap.get(1));
	}
	
	private List<Node> getChildrenByTag(List<Node> list, String TagName){
		List<Node> res = new ArrayList<Node>();
		Node node;
		NodeList nl;
		for (int i=0;i<list.size();++i){
			node=list.get(i);
			nl=node.getChildNodes();
			for (int j=0;j<nl.getLength();++j){
				//System.out.println("finding: "+nl.item(j).getNodeName());
				if (nl.item(j).getNodeName().equals(TagName)){
					res.add(nl.item(j));
				}
			}
		}
		return res;
	}
	
	private List<Node> getDescendantByTag(List<Node> list, String TagName){
		List<Node> res = new ArrayList<Node>();
		Stack<Node> nodeSt = new Stack<Node>();
		Node node;
		NodeList nl;
		for (int i=0;i<list.size();++i)
			nodeSt.push(list.get(i));
		while(!nodeSt.isEmpty()){
			node=nodeSt.pop();
			nl=node.getChildNodes();
			for (int j=0;j<nl.getLength();++j){
				//System.out.println("finding: "+nl.item(j).getNodeName());
				if (nl.item(j).getNodeName().equals(TagName)){
					res.add(nl.item(j));
				}
				nodeSt.push(nl.item(j));
			}
		}
		return res;
	}
	
	private List<Node> getChildrenByAtt(List<Node> list, String AttName){
		List<Node> res = new ArrayList<Node>();
		Node node;
		//NodeList nl;
		NamedNodeMap nnm;
		AttName=AttName.substring(1);
		for (int i=0;i<list.size();++i){
			node=list.get(i);
			//nl=node.getChildNodes();
			nnm = node.getAttributes();
			if (nnm==null) continue;
			for (int k=0;k<nnm.getLength();++k){
				//System.out.println(AttName);
				if (nnm.item(k).getNodeName().equals(AttName)){
					//System.out.println((int)(nnm.item(k).getNodeType()));
					//System.out.println(typeMap.get((int)nnm.item(k).getNodeType()));
					res.add(nnm.item(k));
					System.out.println(nnm.item(k).getNodeType());
				}
				
			}
		}
		return res;
	}
	
	private List<Node> getDescendantByAtt(List<Node> list, String AttName){
		List<Node> res = new ArrayList<Node>();
		Stack<Node> nodeSt = new Stack<Node>();
		Node node;
		NodeList nl;
		NamedNodeMap nnm;
		AttName=AttName.substring(1);
		for (int i=0;i<list.size();++i)
			nodeSt.push(list.get(i));
		while(!nodeSt.isEmpty()){
			node=nodeSt.pop();
			nnm = node.getAttributes();
			if (nnm==null) continue;
			for (int k=0;k<nnm.getLength();++k){
				//System.out.println(AttName);
				if (nnm.item(k).getNodeName().equals(AttName)){
					//System.out.println((int)(nnm.item(k).getNodeType()));
					//System.out.println(typeMap.get((int)nnm.item(k).getNodeType()));
					res.add(nnm.item(k));
				}
				
			}
			nl=node.getChildNodes();
			for (int j=0;j<nl.getLength();++j){
				//System.out.println("finding: "+nl.item(j).getNodeName());
				nodeSt.push(nl.item(j));
			}
		}
		return res;
	}
	
	
	public void enterApSL(XPathParser.ApSLContext ctx){
		singleSL.offer(true);
		try{
			
			String xmlFile = ctx.fileName().getText();
			File file = new File(xmlFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			doc = builder.parse(file);
			NodeList nl = doc.getChildNodes();
			for (int i=0;i<nl.getLength();++i)
				nodeList.add(nl.item(i));
			//System.out.println("No:"+ doc.getElementsByTagName("NO").item(0).getFirstChild().getNodeValue());   
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	public void enterApDSL(XPathParser.ApDSLContext ctx){
		singleSL.offer(false);
		try{
			String xmlFile = ctx.fileName().getText();
			File file = new File(xmlFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
			NodeList nl = doc.getChildNodes();
			for (int i=0;i<nl.getLength();++i)
				nodeList.add(nl.item(i));
			//System.out.println("No:"+ doc.getElementsByTagName("NO").item(0).getFirstChild().getNodeValue());   
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	public void enterRpSL(XPathParser.RpSLContext ctx){
		//System.out.println("rpSL");
		singleSL.offer(true);
		
	}
	
	public void enterRpDSL(XPathParser.RpDSLContext ctx){
		//System.out.println("rpDSL");
		singleSL.offer(false);
		
	}
	
	public void enterRpDDOT(XPathParser.RpDDOTContext ctx) {
		singleSL.poll();
		for (int i=0;i<nodeList.size();++i)
			nodeList.set(i, nodeList.get(i).getParentNode());
		nodeList.remove(null);
		HashSet<Node> h = new HashSet<Node>(nodeList);
		nodeList.clear();
		nodeList.addAll(h);
	}
	
	public void enterTagName(XPathParser.TagNameContext ctx){
		//System.out.println(ctx.getText());
		Boolean isSingle = singleSL.poll();
		if (!inF){
			if (isSingle) nodeList = getChildrenByTag(nodeList, ctx.getText());
			else nodeList = getDescendantByTag(nodeList, ctx.getText());
		//nodeList = getChildrenByTag(nodeList, ctx.getText());
		}
		
	}
	
	public void enterRpATT(XPathParser.RpATTContext ctx){
		Boolean isSingle = singleSL.poll();
		if (!inF){
			if (isSingle) nodeList = getChildrenByAtt(nodeList, ctx.getText());
			else nodeList = getDescendantByAtt(nodeList, ctx.getText());
		//nodeList = getChildrenByTag(nodeList, ctx.getText());
		}
	}
	
	public void getResult(){
		//System.out.println(res.toString());
		Node node;
		for (int i=0;i<nodeList.size();++i){
			node = nodeList.get(i);
			System.out.println(typeMap.get((int)node.getNodeType())+" "+node.toString());
			/*
			switch(node.getNodeType()){
			case 2:
				
				System.out.println("attribute "+node.getNodeName()+" : "+node.getNodeValue());
			default:
				System.out.println("element: "+node.toString());
			}
			*/
			
			
		}
	}
}