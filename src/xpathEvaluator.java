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

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 

public class xpathEvaluator{
	
	private List<Node> curList = new ArrayList<Node>();
	private xpVisitor visitor;
	
	private List<Node> getDescedants(List<Node> lst){
		List<Node> res = new ArrayList<Node>();
		Queue<Node> nodeSt = new LinkedBlockingQueue<Node>();
		Node node;
		NodeList nl;
		nodeSt.addAll(lst);
		res.addAll(lst);
		while(!nodeSt.isEmpty()){
			node=nodeSt.poll();
			nl=node.getChildNodes();
			for (int j=0;j<nl.getLength();++j){
				if (nl.item(j)!=null&& nl.item(j).getNodeType()!=10){
					res.add(nl.item(j));
					nodeSt.offer(nl.item(j));
				}
			}
		}
		HashSet<Node> h = new HashSet<Node>(res);
		res.clear();
		res.addAll(h);
		return res;
	}
	
	private List<Node> getChildren(List<Node> lst){
		List<Node> res = new ArrayList<Node>();
		NodeList nl;
		for (Node node:lst){
			nl=node.getChildNodes();
			for (int j=0;j<nl.getLength();++j){
				if (nl.item(j)!=null && nl.item(j).getNodeType()!=10)
					res.add(nl.item(j));
			}
		}
		return res;
	}
	
	private List<Node> getParents(List<Node> lst){
		List<Node> res = new ArrayList<Node>();
		for (Node node:lst){
			if (node.getNodeType()==2){
				//System.out.println(((Attr)node).getOwnerElement());
				res.add(((Attr)node).getOwnerElement());
			}
			else res.add(node.getParentNode());
		}
		HashSet<Node> h = new HashSet<Node>(res);
		res.clear();
		res.addAll(h);
		return res;
	}
	
	public List<Node> evaluateApSL(XPathParser.ApSLContext ctx){
		//List<Node> lst = new ArrayList<Node>();
		try{
			String xmlFile = ctx.fileName().getText();
			File file = new File(xmlFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();			
			DocumentBuilder builder = factory.newDocumentBuilder();			
			Document doc = builder.parse(file);
			curList.add(doc);  
		}
		catch (Exception e){e.printStackTrace();}
		//visitor.setNodeList(lst);
		return visitor.visit(ctx.rp());
	}
	
	public List<Node> evaluateApDSL(XPathParser.ApDSLContext ctx){
		List<Node> lst = new ArrayList<Node>();
		try{
			String xmlFile = ctx.fileName().getText();
			File file = new File(xmlFile);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			curList.add(doc);
			curList = this.getDescedants(curList);  
		}
		catch (Exception e){e.printStackTrace();}
		return visitor.visit(ctx.rp());
	}
	
	
	public List<Node> evaluateRpSL(XPathParser.RpSLContext ctx, List<Node> cur) {
		curList=visitor.visit(ctx.left);
		return visitor.visit(ctx.right);
	}
	
	public List<Node> evaluateRpDSL(XPathParser.RpDSLContext ctx, List<Node> cur) {
		visitor.setNodeList(cur);
		visitor.setNodeList(this.getDescedants(visitor.visit(ctx.left)));
		return visitor.visit(ctx.right);
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