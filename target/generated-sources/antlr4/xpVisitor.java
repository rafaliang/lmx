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

public class xpVisitor extends XPathBaseVisitor<List<Node>>{
	private List<Node> cur = new ArrayList<Node>();
	private xpathEvaluator evaluator;
	
	public void setNodeList(List<Node> lst){
		cur.clear();
		cur.addAll(lst);
	}
	
	public List<Node> visitApSL(XPathParser.ApSLContext ctx){
		return evaluator.evaluateApSL(ctx);
	}
	
	public List<Node> visitApDSL(XPathParser.ApDSLContext ctx){
		//List<Node> nodeList = new ArrayList<Node>();
		//System.out.println("this is a ApDSL");
		try{
			String xmlFile = ctx.fileName().getText();
			//String xmlFile ="/Users/rafaliang/code/CSE232B/antlrTutorial/src/j_caesar.xml";
			File file = new File(xmlFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document doc = builder.parse(file);
			//doc
			/*NodeList nl = doc.getChildNodes();
			//nodeList.addAll((Collection<? extends Node>) nl);
			
			for (int i=0;i<nl.getLength();++i)
				curList.add(nl.item(i));*/
			curList.add(doc);
			curList = this.getDescedants(curList);
			
			//System.out.println("No:"+ doc.getElementsByTagName("NO").item(0).getFirstChild().getNodeValue());   
		}
		catch (Exception e){e.printStackTrace();}
		//System.out.println(curList.size());
		return visit(ctx.rp());
		//return curList;
	}
	
	public List<Node> visitRpSL(XPathParser.RpSLContext ctx) {
		//System.out.println("this is a RpSL");
		curList = this.visit(ctx.left);
		//List<Node> res = new ArrayList<Node>();
		//for (int i=0;i<llst.size();++i)
		return this.visit(ctx.right);
	}
	
	public List<Node> visitRpDSL(XPathParser.RpDSLContext ctx) {
		//System.out.println("this is a RpDsL");
		
		curList = this.visit(ctx.left);
		curList = this.getDescedants(curList);
		//List<Node> res = new ArrayList<Node>();
		//for (int i=0;i<llst.size();++i)
		return this.visit(ctx.right);
	}
	
	public List<Node> visitRpTAG(XPathParser.RpTAGContext ctx) {
		//System.out.println("this is a RpTag");
		List<Node> res = new ArrayList<Node>();
		//System.out.println(ctx.tagName().getText());
		List<Node> candidate = this.getChildren(curList);
		for (int i=0;i<candidate.size();++i){
			if (candidate.get(i).getNodeName().equals(ctx.getText()))
				res.add(candidate.get(i));
		}
		//curList.clear();
		//curList.addAll(res);
		return res;
	}
	
	public List<Node> visitRpATT(XPathParser.RpATTContext ctx) {
		//System.out.println("this is a RpAtt");
		List<Node> res = new ArrayList<Node>();
		//System.out.println(ctx.attName().getText());
		for (int i=0;i<curList.size();++i){
			Node node = curList.get(i);
			NamedNodeMap nnm = node.getAttributes();
			for (int j=0;j<nnm.getLength();++j){
				//System.out.println(nnm.item(j));
				if (nnm.item(j).getNodeName().equals(ctx.getText().substring(1)))
					res.add(nnm.item(j));
			}
		}
		//curList.clear();
		//curList.addAll(res);
		return res;
	}
	
	public List<Node> visitRpDOT(XPathParser.RpDOTContext ctx) {
		//System.out.println("this is a RpDot");
		return curList;
	}
	
	public List<Node> visitRpDDOT(XPathParser.RpDDOTContext ctx) {
		//System.out.println("this is a RpDdot");
		return getParents(curList);
	}
	
	public List<Node> visitRpTEXT(XPathParser.RpTEXTContext ctx) {
		//System.out.println("this is a RpText");
		List<Node> res = new ArrayList<Node>();
		for (Node node:curList){
			Node n = node.getChildNodes().item(0);
			//System.out.println(n.getNodeType());
			if (n.getNodeType()==3)
				res.add(n);
			//node.TEXT_NODE
		}
		return res; 
	}
	
	public List<Node> visitRpPARA(XPathParser.RpPARAContext ctx) {
		//System.out.println("this is a RpPara");
		return visit(ctx.rp()); 
	}
	
	public List<Node> visitRpSTAR(XPathParser.RpSTARContext ctx) { 
		//System.out.println("this is a RpStar");
		return this.getChildren(curList); 
	}
	
	public List<Node> visitRpCOMMA(XPathParser.RpCOMMAContext ctx) { 
		//System.out.println("this is a RpComma");
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = new ArrayList<Node>();
		tmp.addAll(curList);
		res.addAll(this.visit(ctx.left));
		curList.clear();
		curList.addAll(tmp);
		res.addAll(this.visit(ctx.right));
		return res; 
	}
	
	public List<Node> visitRpF(XPathParser.RpFContext ctx) {
		List<Node> tmp = this.visit(ctx.rp());
		List<Node> res = new ArrayList<Node>();
		//List<Node> satisfied = new ArrayList<Node>();
		for (Node node:tmp){
			curList.clear();
			curList.add(node);
			if(!visit(ctx.f()).isEmpty()) res.add(node);
		}
		return res; 
	}
	
	public List<Node> visitFilterIS(XPathParser.FilterISContext ctx) { 
		List<Node> leftRes,rightRes,tmp = new ArrayList<Node>();
		List<Node> res = new ArrayList<Node>();
		tmp.addAll(curList);
		leftRes = this.visit(ctx.left);
		curList.clear();
		curList.addAll(tmp);
		rightRes = this.visit(ctx.right);
		//Collections.sort((List<Node>) leftRes);
		if (leftRes.isEmpty() || rightRes.isEmpty()) return res;
		for (Node node1:leftRes){
			for (Node node2:rightRes){
				if (node1.isSameNode(node2))
					return leftRes;
			}
		}
		return res; 
	}
	
	public List<Node> visitFilterPara(XPathParser.FilterParaContext ctx) { 
		return visit(ctx.f()); 
	}
	
	public List<Node> visitFilterRP(XPathParser.FilterRPContext ctx) { 
		return visit(ctx.rp()); 
	}
	
	public List<Node> visitFilterNOT(XPathParser.FilterNOTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = visit(ctx.f());
		if (tmp.isEmpty())
			res.add(null);
		return res; 
	}
	
	public List<Node> visitFilterEQ(XPathParser.FilterEQContext ctx) { 
		List<Node> leftRes,rightRes,tmp = new ArrayList<Node>();
		List<Node> res = new ArrayList<Node>();
		tmp.addAll(curList);
		leftRes = this.visit(ctx.left);
		curList.clear();
		curList.addAll(tmp);
		rightRes = this.visit(ctx.right);
		//System.out.println(leftRes);
		//System.out.println(curList);
		//System.out.println(rightRes);
		//Collections.sort((List<Node>) leftRes);
		if (leftRes.isEmpty() || rightRes.isEmpty()) return res;
		for (Node node1:leftRes){
			for (Node node2:rightRes){
				if (node1.getNodeValue().equals(node2.getNodeValue()))
					return leftRes;
			}
		}
		return res; 
	}
	
	public List<Node> visitFilterOR(XPathParser.FilterORContext ctx) { 
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = new ArrayList<Node>();
		tmp.addAll(curList);
		List<Node> left = visit(ctx.leftF);
		curList.clear();
		curList.addAll(tmp);
		List<Node> right = visit(ctx.rightF);
		if (!left.isEmpty() || !right.isEmpty()) res.add(null);
		return res;
	}
	
	public List<Node> visitFilterAND(XPathParser.FilterANDContext ctx) { 
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = new ArrayList<Node>();
		tmp.addAll(curList);
		List<Node> left = visit(ctx.leftF);
		curList.clear();
		curList.addAll(tmp);
		List<Node> right = visit(ctx.rightF);
		if (!left.isEmpty() && !right.isEmpty()) res.add(null);
		return res;
	}
}