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
	
	protected Stack<List<Node>> nodelstSt;
	protected xpVisitor visitor;
	
	xpathEvaluator(xpVisitor visitor, Stack<List<Node>> nodelstSt){
		this.visitor=visitor;
		this.nodelstSt=nodelstSt;
		
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
		List<Node> lst = new ArrayList<Node>();
		String xmlFile = ctx.fileName().getText();
		lst.add(this.readXML(xmlFile));
		nodelstSt.push(lst);
		lst = visitor.visit(ctx.rp());
		nodelstSt.pop();
		return lst;
	}
	
	public List<Node> evalApDSL(XQueryParser.ApDSLContext ctx){
		List<Node> lst = new ArrayList<Node>();
		String xmlFile = ctx.fileName().getText();
		lst.add(this.readXML(xmlFile));
		lst = this.getDescedants(lst);
		nodelstSt.push(lst);
		return visitor.visit(ctx.rp());
		
	}
	
	public List<Node> evalRpSL(XQueryParser.RpSLContext ctx) {
		nodelstSt.push(visitor.visit(ctx.left));
		List<Node> lst =  visitor.visit(ctx.right);
		nodelstSt.pop();
		return lst;
	}
	
	public List<Node> evalRpDSL(XQueryParser.RpDSLContext ctx) {
		List<Node> lst = visitor.visit(ctx.left);
		nodelstSt.push(this.getDescedants(lst));
		lst =  visitor.visit(ctx.right);
		nodelstSt.pop();
		return lst;
	}
	
	public List<Node> evalRpTAG(XQueryParser.RpTAGContext ctx) {
		List<Node> res = new ArrayList<Node>();
		//List<Node> lst = nodelstSt.peek();
		List<Node> candidate = this.getChildren(nodelstSt.peek());
		for (int i=0;i<candidate.size();++i){
			if (candidate.get(i).getNodeName().equals(ctx.getText()))
				res.add(candidate.get(i));
		}
		//nodelstSt.push(res);
		return res;
	}
	
	public List<Node> evalRpATT(XQueryParser.RpATTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		List<Node> lst = nodelstSt.peek();
		for (int i=0;i<lst.size();++i){
			Node node = lst.get(i);
			NamedNodeMap nnm = node.getAttributes();
			for (int j=0;j<nnm.getLength();++j){
				if (nnm.item(j).getNodeName().equals(ctx.getText().substring(1)))
					res.add(nnm.item(j));
			}
		}
		return res;
	}
	
	public List<Node> evalRpDOT(XQueryParser.RpDOTContext ctx) {
		//List<Node> res = new ArrayList<Node>(curList);
		return nodelstSt.peek();
	}
	
	public List<Node> evalRpDDOT(XQueryParser.RpDDOTContext ctx) {
		return this.getParents(nodelstSt.peek());
	}
	
	public List<Node> evalRpTEXT(XQueryParser.RpTEXTContext ctx) {
		List<Node> res = new ArrayList<Node>();
		for (Node node:nodelstSt.peek()){
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
		return this.getChildren(nodelstSt.peek()); 
	}
	
	public List<Node> evalRpCOMMA(XQueryParser.RpCOMMAContext ctx) { 
		List<Node> lst1 = visitor.visit(ctx.left);
		List<Node> lst2 = visitor.visit(ctx.right);
		lst1.addAll(lst2);
		return lst1;
	}
	
	public List<Node> evalRpF(XQueryParser.RpFContext ctx) {
		List<Node> tmp = new ArrayList<Node>();
		List<Node> lst = visitor.visit(ctx.rp());
		List<Node> res = new ArrayList<Node>();
		for (Node node:lst){
			tmp.clear();
			tmp.add(node);
			nodelstSt.push(tmp);
			if(!visitor.visit(ctx.f()).isEmpty()) res.add(node);
		}
		return res; 
	}
	
	public List<Node> evalFilterIS(XQueryParser.FilterISContext ctx) { 
		List<Node> leftRes = visitor.visit(ctx.left);
		List<Node> rightRes = visitor.visit(ctx.right);
		List<Node> res = new ArrayList<Node>();
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
		List<Node> leftRes = visitor.visit(ctx.left);
		List<Node> rightRes = visitor.visit(ctx.right);
		List<Node> res = new ArrayList<Node>();
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
		List<Node> leftRes = visitor.visit(ctx.leftF);
		List<Node> rightRes = visitor.visit(ctx.rightF);
		List<Node> res = new ArrayList<Node>();
		if (!leftRes.isEmpty() || !rightRes.isEmpty()) res.add(null);
		return res;
	}
	
	
	public List<Node> evalFilterAND(XQueryParser.FilterANDContext ctx) { 
		List<Node> leftRes = visitor.visit(ctx.leftF);
		List<Node> rightRes = visitor.visit(ctx.rightF);
		List<Node> res = new ArrayList<Node>();
		if (!leftRes.isEmpty() && !rightRes.isEmpty()) res.add(null);
		return res;
	}
	
}