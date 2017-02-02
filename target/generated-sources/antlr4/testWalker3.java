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

public class testWalker3 extends XPathBaseVisitor<List<Node>>{
	
	private List<Node> curList = new ArrayList<Node>();
	
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
		return this.removeDup(res);
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
				res.add(((Attr)node).getOwnerElement());
			}
			else res.add(node.getParentNode());
		}
		return this.removeDup(res);
	}
	
	private List<Node> removeDup(List<Node> lst){
		List<Node> res = new ArrayList<Node>();
		HashSet<Node> h = new HashSet<Node>(lst);
		res.addAll(h);
		return res;
	}
	
	private Node readXML(String fileName){
		Document doc = null;
		try{
			File file = new File(fileName);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
		}
		catch (Exception e){e.printStackTrace();}
		return doc;
	}
	
	public List<Node> visitApSL(XPathParser.ApSLContext ctx){
		String xmlFile = ctx.fileName().getText();
		curList.add(this.readXML(xmlFile));
		return visit(ctx.rp());
	}
	
	public List<Node> visitApDSL(XPathParser.ApDSLContext ctx){
		String xmlFile = ctx.fileName().getText();
		curList.add(this.readXML(xmlFile));
		curList = this.getDescedants(curList);
		return visit(ctx.rp());
	}
	
	public List<Node> visitRpSL(XPathParser.RpSLContext ctx) {
		curList = this.visit(ctx.left);
		return this.visit(ctx.right);
	}
	
	public List<Node> visitRpDSL(XPathParser.RpDSLContext ctx) {
		curList = this.visit(ctx.left);
		curList = this.getDescedants(curList);
		return this.visit(ctx.right);
	}
	
	public List<Node> visitRpTAG(XPathParser.RpTAGContext ctx) {
		List<Node> res = new ArrayList<Node>();
		List<Node> candidate = this.getChildren(curList);
		for (int i=0;i<candidate.size();++i){
			if (candidate.get(i).getNodeName().equals(ctx.getText()))
				res.add(candidate.get(i));
		}
		return res;
	}
	
	public List<Node> visitRpATT(XPathParser.RpATTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		for (int i=0;i<curList.size();++i){
			Node node = curList.get(i);
			NamedNodeMap nnm = node.getAttributes();
			for (int j=0;j<nnm.getLength();++j){
				if (nnm.item(j).getNodeName().equals(ctx.getText().substring(1)))
					res.add(nnm.item(j));
			}
		}
		return res;
	}
	
	public List<Node> visitRpDOT(XPathParser.RpDOTContext ctx) {
		return curList;
	}
	
	public List<Node> visitRpDDOT(XPathParser.RpDDOTContext ctx) {
		return getParents(curList);
	}
	
	public List<Node> visitRpTEXT(XPathParser.RpTEXTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		for (Node node:curList){
			Node n = node.getChildNodes().item(0);
			if (n.getNodeType()==3)
				res.add(n);
		}
		return res; 
	}
	
	public List<Node> visitRpPARA(XPathParser.RpPARAContext ctx) {
		return visit(ctx.rp()); 
	}
	
	public List<Node> visitRpSTAR(XPathParser.RpSTARContext ctx) { 
		return this.getChildren(curList); 
	}
	
	public List<Node> visitRpCOMMA(XPathParser.RpCOMMAContext ctx) { 
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