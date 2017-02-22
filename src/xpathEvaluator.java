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
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 

public class xpathEvaluator{
	
	protected Stack<QList> nodelstSt;
	protected xpVisitor visitor;
	
	xpathEvaluator(xpVisitor visitor, Stack<QList> nodelstSt){
		this.visitor=visitor;
		this.nodelstSt=nodelstSt;
		
	}
	/*
	private QList getDescedants(QList lst){
		QList res = new QList();
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
	
	private QList getChildren(QList lst){
		QList res = new QList();
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
	
	private QList getParents(QList lst){
		QList res = new QList();
		for (Node node:lst){
			if (node==null) continue;
			if (node.getNodeType()==2){
				res.add(((Attr)node).getOwnerElement());
			}
			else res.add(node.getParentNode());
		}
		return this.removeDup(res);
	}
	
	private QList removeDup(QList lst){
		QList res = new QList();
		HashSet<Node> h = new HashSet<Node>();
		for (Node node:lst){
			if (!h.contains(node)){
				res.add(node);
				h.add(node);
			}
		}
		return res;
	}
	*/
	private Node readXML(String fileName){
		Document doc = null;
		try{
			//System.out.println(fileName);
			File file = new File(fileName);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(file);
		}
		catch (Exception e){e.printStackTrace();}
		return doc;
	}
	
	public QList evalApSL(XQueryParser.ApSLContext ctx){
		QList lst = new QList();
		String xmlFile = ctx.fileName().getText();
		xmlFile = xmlFile.substring(1,xmlFile.length()-1);
		lst.add(this.readXML(xmlFile));
		nodelstSt.push(lst);
		lst = (QList) visitor.visit(ctx.rp());
		nodelstSt.pop();
		return lst;
	}
	
	public QList evalApDSL(XQueryParser.ApDSLContext ctx){
		QList lst = new QList();
		String xmlFile = ctx.fileName().getText();
		xmlFile = xmlFile.substring(1,xmlFile.length()-1);
		lst.add(this.readXML(xmlFile));
		lst = lst.getDescedants();
		nodelstSt.push(lst);
		return (QList) visitor.visit(ctx.rp());
		
	}
	
	public QList evalRpSL(XQueryParser.RpSLContext ctx) {
		nodelstSt.push((QList)visitor.visit(ctx.left));
		QList lst =  (QList) visitor.visit(ctx.right);
		nodelstSt.pop();
		return lst;
	}
	
	public QList evalRpDSL(XQueryParser.RpDSLContext ctx) {
		QList lst = (QList) visitor.visit(ctx.left);
		nodelstSt.push(lst.getDescedants());
		lst =  (QList) visitor.visit(ctx.right);
		nodelstSt.pop();
		return lst;
	}
	
	public QList evalRpTAG(XQueryParser.RpTAGContext ctx) {
		QList res = new QList();
		//QList lst = nodelstSt.peek();
		QList candidate = nodelstSt.peek().getChildren();
		for (int i=0;i<candidate.size();++i){
			if (candidate.get(i).getNodeName().equals(ctx.getText()))
				res.add(candidate.get(i));
		}
		//nodelstSt.push(res);
		return res;
	}
	
	public QList evalRpATT(XQueryParser.RpATTContext ctx) {
		QList res = new QList();
		QList lst = nodelstSt.peek();
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
	
	public QList evalRpDOT(XQueryParser.RpDOTContext ctx) {
		//QList res = new QList(curList);
		return nodelstSt.peek();
	}
	
	public QList evalRpDDOT(XQueryParser.RpDDOTContext ctx) {
		return nodelstSt.peek().getParents();
	}
	
	public QList evalRpTEXT(XQueryParser.RpTEXTContext ctx) {
		QList res = new QList();
		for (Node node:nodelstSt.peek()){
			Node n = node.getChildNodes().item(0);
			if (n!=null && n.getNodeType()==3)
				res.add(n);
		}
		return res; 
	}
	
	public QList evalRpPARA(XQueryParser.RpPARAContext ctx) {
		return (QList) visitor.visit(ctx.rp()); 
	}
	
	public QList evalRpSTAR(XQueryParser.RpSTARContext ctx) { 
		return nodelstSt.peek().getChildren(); 
	}
	
	public QList evalRpCOMMA(XQueryParser.RpCOMMAContext ctx) { 
		QList lst1 = (QList) visitor.visit(ctx.left);
		QList lst2 = (QList) visitor.visit(ctx.right);
		lst1.addAll(lst2);
		return lst1;
	}
	
	public QList evalRpF(XQueryParser.RpFContext ctx) {
		QList lst = (QList) visitor.visit(ctx.rp());
		QList res = new QList();
		for (Node node:lst){
			QList tmp = new QList(node);
			nodelstSt.push(tmp);
			if(!((QList)visitor.visit(ctx.f())).isEmpty()) res.add(node);
			nodelstSt.pop();
		}
		return res; 
	}
	
	public QList evalFilterIS(XQueryParser.FilterISContext ctx) { 
		QList leftRes = (QList) visitor.visit(ctx.left);
		QList rightRes = (QList) visitor.visit(ctx.right);
		QList res = new QList();
		if (leftRes.is(rightRes)) return leftRes;
		else return res; 
	}
	
	public QList evalFilterPara(XQueryParser.FilterParaContext ctx) { 
		return (QList) visitor.visit(ctx.f()); 
	}
	
	public QList evalFilterRP(XQueryParser.FilterRPContext ctx) { 
		return (QList) visitor.visit(ctx.rp()); 
	}
	
	public QList evalFilterNOT(XQueryParser.FilterNOTContext ctx) {
		QList res = new QList();
		QList tmp = (QList) visitor.visit(ctx.f());
		if (tmp.isEmpty())
			res.add(null);
		return res; 
	}
	
	public QList evalFilterEQ(XQueryParser.FilterEQContext ctx) { 
		QList leftRes = (QList) visitor.visit(ctx.left);
		QList rightRes = (QList) visitor.visit(ctx.right);
		QList res = new QList();
		if (leftRes.eq(rightRes)) return leftRes;
		else return res;
	}
	
	public QList evalFilterOR(XQueryParser.FilterORContext ctx) { 
		QList leftRes = (QList) visitor.visit(ctx.leftF);
		QList rightRes = (QList) visitor.visit(ctx.rightF);
		QList res = new QList();
		if (leftRes.or(rightRes)) res.add(null);
		return res;
	}
	
	
	public QList evalFilterAND(XQueryParser.FilterANDContext ctx) { 
		QList leftRes = (QList) visitor.visit(ctx.leftF);
		QList rightRes = (QList) visitor.visit(ctx.rightF);
		QList res = new QList();
		if (leftRes.and(rightRes)) res.add(null);
		return res;
	}
	
}