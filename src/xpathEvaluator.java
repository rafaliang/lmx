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
	
	protected List<Node> curList;
	protected xpVisitor visitor;
	
	xpathEvaluator(xpVisitor visitor, List<Node> curList){
		this.visitor=visitor;
		this.curList=curList;
		
	}
	
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
			if (node==null) continue;
			if (node.getNodeType()==2){
				res.add(((Attr)node).getOwnerElement());
			}
			else res.add(node.getParentNode());
		}
		return this.removeDup(res);
	}
	
	private List<Node> removeDup(List<Node> lst){
		List<Node> res = new ArrayList<Node>();
		HashSet<Node> h = new HashSet<Node>();
		for (Node node:lst){
			if (!h.contains(node)){
				res.add(node);
				h.add(node);
			}
		}
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
	
	public List<Node> evalApSL(XQueryParser.ApSLContext ctx){
		String xmlFile = ctx.fileName().getText();
		curList.add(this.readXML(xmlFile));
		return visitor.visit(ctx.rp());
	}
	
	public List<Node> evalApDSL(XQueryParser.ApDSLContext ctx){
		String xmlFile = ctx.fileName().getText();
		curList.add(this.readXML(xmlFile));
		curList = this.getDescedants(curList);
		return visitor.visit(ctx.rp());
	}
	
	public List<Node> evalRpSL(XQueryParser.RpSLContext ctx) {
		curList = visitor.visit(ctx.left);
		return visitor.visit(ctx.right);
	}
	
	public List<Node> evalRpDSL(XQueryParser.RpDSLContext ctx) {
		curList = visitor.visit(ctx.left);
		curList = this.getDescedants(curList);
		return visitor.visit(ctx.right);
	}
	
	public List<Node> evalRpTAG(XQueryParser.RpTAGContext ctx) {
		List<Node> res = new ArrayList<Node>();
		List<Node> candidate = this.getChildren(curList);
		for (int i=0;i<candidate.size();++i){
			if (candidate.get(i).getNodeName().equals(ctx.getText()))
				res.add(candidate.get(i));
		}
		return res;
	}
	
	public List<Node> evalRpATT(XQueryParser.RpATTContext ctx) {
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
	
	public List<Node> evalRpDOT(XQueryParser.RpDOTContext ctx) {
		List<Node> res = new ArrayList<Node>(curList);
		return res;
	}
	
	public List<Node> evalRpDDOT(XQueryParser.RpDDOTContext ctx) {
		return getParents(curList);
	}
	
	public List<Node> evalRpTEXT(XQueryParser.RpTEXTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		for (Node node:curList){
			Node n = node.getChildNodes().item(0);
			if (n.getNodeType()==3)
				res.add(n);
		}
		return res; 
	}
	
	public List<Node> evalRpPARA(XQueryParser.RpPARAContext ctx) {
		return visitor.visit(ctx.rp()); 
	}
	
	public List<Node> evalRpSTAR(XQueryParser.RpSTARContext ctx) { 
		return this.getChildren(curList); 
	}
	
	public List<Node> evalRpCOMMA(XQueryParser.RpCOMMAContext ctx) { 
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = new ArrayList<Node>();
		tmp.addAll(curList);
		res.addAll(visitor.visit(ctx.left));
		curList.clear();
		curList.addAll(tmp);
		res.addAll(visitor.visit(ctx.right));
		return res; 
	}
	
	public List<Node> evalRpF(XQueryParser.RpFContext ctx) {
		List<Node> tmp = new ArrayList<Node>();
		tmp = visitor.visit(ctx.rp());
		List<Node> res = new ArrayList<Node>();
		for (Node node:tmp){
			curList.clear();
			curList.add(node);
			if(!visitor.visit(ctx.f()).isEmpty()) res.add(node);
		}
		return res; 
	}
	
	public List<Node> evalFilterIS(XQueryParser.FilterISContext ctx) { 
		List<Node> leftRes,rightRes,tmp = new ArrayList<Node>();
		List<Node> res = new ArrayList<Node>();
		tmp.addAll(curList);
		leftRes = visitor.visit(ctx.left);
		curList.clear();
		curList.addAll(tmp);
		rightRes = visitor.visit(ctx.right);
		if (leftRes.isEmpty() || rightRes.isEmpty()) return res;
		for (Node node1:leftRes){
			for (Node node2:rightRes){
				if (node1.isSameNode(node2))
					return leftRes;
			}
		}
		return res; 
	}
	
	public List<Node> evalFilterPara(XQueryParser.FilterParaContext ctx) { 
		return visitor.visit(ctx.f()); 
	}
	
	public List<Node> evalFilterRP(XQueryParser.FilterRPContext ctx) { 
		return visitor.visit(ctx.rp()); 
	}
	
	public List<Node> evalFilterNOT(XQueryParser.FilterNOTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = visitor.visit(ctx.f());
		if (tmp.isEmpty())
			res.add(null);
		return res; 
	}
	
	public List<Node> evalFilterEQ(XQueryParser.FilterEQContext ctx) { 
		List<Node> leftRes,rightRes,tmp = new ArrayList<Node>();
		List<Node> res = new ArrayList<Node>();
		tmp.addAll(curList);
		leftRes = visitor.visit(ctx.left);
		curList.clear();
		curList.addAll(tmp);
		rightRes = visitor.visit(ctx.right);
		if (leftRes.isEmpty() || rightRes.isEmpty()) return res;
		for (Node node1:leftRes){
			for (Node node2:rightRes){
				if (node1.isEqualNode(node2))
					return leftRes;
			}
		}
		return res; 
	}
	
	public List<Node> evalFilterOR(XQueryParser.FilterORContext ctx) { 
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = new ArrayList<Node>();
		tmp.addAll(curList);
		List<Node> left = visitor.visit(ctx.leftF);
		curList.clear();
		curList.addAll(tmp);
		List<Node> right = visitor.visit(ctx.rightF);
		if (!left.isEmpty() || !right.isEmpty()) res.add(null);
		return res;
	}
	
	
	public List<Node> evalFilterAND(XQueryParser.FilterANDContext ctx) { 
		List<Node> res = new ArrayList<Node>();
		List<Node> tmp = new ArrayList<Node>();
		tmp.addAll(curList);
		List<Node> left = visitor.visit(ctx.leftF);
		curList.clear();
		curList.addAll(tmp);
		List<Node> right = visitor.visit(ctx.rightF);
		if (!left.isEmpty() && !right.isEmpty()) res.add(null);
		return res;
	}
	
}