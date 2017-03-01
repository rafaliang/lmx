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
import value.VMList;
import value.VarMap;
import value.XQValue;

import javax.xml.parsers.DocumentBuilder;   
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
	protected Stack<VarMap> varSt;
	
	xqueryEvaluator(xpVisitor visitor, Stack<QList> nodelstSt, Stack<VarMap> varSt){
		this.visitor = visitor;
		this.nodelstSt = nodelstSt;
		this.varSt=varSt;
	}
	
	private Node makeElem(QList lst, String tagName){
		Element ele=null;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			ele = document.createElement(tagName);
			for (Node node:lst){
				if (node.getNodeType()==2){
					Node imported = document.importNode(node, true);
					Element e = document.createElement("Attribute");
					e.setAttributeNodeNS((Attr)imported);
					ele.appendChild(e);
				}
				// text node
				else if (node.getNodeType()==3){
					Node imported = document.importNode(node, true);
					Element e = document.createElement("TEXT");
					ele.appendChild(imported);
					e.appendChild(ele);
				}
				// document node
				else if (node.getNodeType()==9){
					//System.out.println("doc");
					System.out.println("document node cannot be created");
	                return node;
				}
				else{
					Node imported = document.importNode(node, true);
					ele.appendChild(imported);
				}
				//ele.appendChild(node);
			}
		}
		catch(Exception e){System.out.println(e);}
		return ele;
	}
	
	private Node makeText(String text){
		Text txt=null;
		text=(String) text.subSequence(1, text.length()-1);
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			txt = document.createTextNode(text);
		}
		catch(Exception e){System.out.println(e);}
		return txt;
	}
	
	public QList evalXqAP(XQueryParser.XqAPContext ctx) { 
		return (QList) visitor.visit(ctx.ap()); 
	}
	
	public QList evalXqVAR(XQueryParser.XqVARContext ctx) { 
		VarMap vm = varSt.peek();
		String var = ctx.Var().getText();
		if (vm.containsKey(var)) return vm.get(var);
		else return new QList();
	}
	
	public QList evalXqPARA(XQueryParser.XqPARAContext ctx) { 
		return (QList) visitor.visit(ctx.xq()); 
	}
	
	public QList evalXqSL(XQueryParser.XqSLContext ctx) { 
		QList leftRes = (QList) visitor.visit(ctx.xq());
		nodelstSt.push(leftRes);
		QList res = (QList) visitor.visit(ctx.rp());
		nodelstSt.pop();
		return res;
	}
	
	public QList evalXqDSL(XQueryParser.XqDSLContext ctx) { 
		QList leftRes = (QList) visitor.visit(ctx.xq());
		nodelstSt.push(leftRes.getDescedants());
		QList res = (QList) visitor.visit(ctx.rp());
		nodelstSt.pop();
		return res;
	}
	
	public QList evalXqString(XQueryParser.XqStringContext ctx) { 
		return new QList(makeText(ctx.StringConstant().getText())); 
	}
	
	public QList evalXqLET(XQueryParser.XqLETContext ctx) { 
		varSt.push((VarMap) visitor.visit(ctx.letClause()));
		QList res = (QList) visitor.visit(ctx.xq());
		varSt.pop();
		return res;
	}
	
	public QList evalXqTAG(XQueryParser.XqTAGContext ctx) {
		if (!ctx.leftT.getText().equals(ctx.rightT.getText())){
			System.out.println(ctx.leftT.getText());
			System.out.println(ctx.rightT.getText());
			System.out.println("Two tagnames are not the same");
			return new QList();
		}
		//QList xqRes = (QList) visitor.visit(ctx.xq());
		return new QList(makeElem((QList) visitor.visit(ctx.xq()),ctx.leftT.getText()));
	}
	
	public QList evalXqFOR(XQueryParser.XqFORContext ctx) { 
		VMList vmList = (VMList) visitor.visit(ctx.forClause());
		QList res = new QList();
		for (VarMap vm:vmList){
			varSt.push(vm);
			if (ctx.letClause()!=null){
				varSt.push((VarMap) visitor.visit(ctx.letClause()));
			}
			if (ctx.whereClause()!=null){
				if (!((QList) visitor.visit(ctx.whereClause())).isEmpty())
					res.addAll((QList) visitor.visit(ctx.returnClause()));
			}
			else res.addAll((QList) visitor.visit(ctx.returnClause()));
			if (ctx.letClause()!=null){
				varSt.pop();
			}
			varSt.pop();
		}
		return res;
	}
	
	public QList evalXqCOMMA(XQueryParser.XqCOMMAContext ctx) { 
		QList left = (QList) visitor.visit(ctx.left);
		QList right = (QList) visitor.visit(ctx.right);
		left.addAll(right);
		return left;
	}
	
	private VMList getVMListFor(int idx, XQueryParser.ForClauseContext ctx, VarMap prevVar){
		VMList res = new VMList();
		QList lst = (QList) visitor.visit(ctx.xq(idx));
		if (idx+1==ctx.xq().size()){
			for (Node node:lst){
				VarMap curVar = prevVar.copy();
				curVar.put(ctx.Var(idx).getText(), new QList(node));
				res.add(curVar);
			}
			return res;
		}
		for (Node node:lst){
			nodelstSt.push(new QList(node));
			VarMap curVar = prevVar.copy();
			curVar.put(ctx.Var(idx).getText(), new QList(node));
			varSt.push(curVar);
			res.addAll(getVMListFor(idx+1,ctx,curVar));
			varSt.pop();
			nodelstSt.pop();
		}
		return res;
	}
	
	public VMList evalForClause(XQueryParser.ForClauseContext ctx) { 
		VarMap prevVar = varSt.peek();
		return getVMListFor(0,ctx,prevVar);
		
	}
	
	public VarMap evalLetClause(XQueryParser.LetClauseContext ctx) {
		VarMap curPeek = varSt.peek();
		VarMap curPeekCopy = varSt.peek().copy();
		for (int i=0;i<ctx.Var().size();++i){
			QList lst = (QList) visitor.visit(ctx.xq(i));
			curPeek.put(ctx.Var(i).getText(), lst);
		}
		VarMap res = curPeek.copy();
		varSt.pop();
		varSt.push(curPeekCopy);
		return res;
	}
	
	public QList evalWhereClause(XQueryParser.WhereClauseContext ctx) { 
		return (QList) visitor.visit(ctx.cond());
	}
	
	public QList evalReturnClause(XQueryParser.ReturnClauseContext ctx) { 
		return (QList) visitor.visit(ctx.xq());
	}
	
	public QList evalCondEQ(XQueryParser.CondEQContext ctx) { 
		QList res = new QList();
		QList left = (QList) visitor.visit(ctx.left);
		QList right = (QList) visitor.visit(ctx.right);
		if (left.eq(right)) res.add(null);
		return res;
	}
	
	public QList evalCondPARA(XQueryParser.CondPARAContext ctx) { 
		return (QList) visitor.visit(ctx.cond());
	}
	
	public QList evalCondIS(XQueryParser.CondISContext ctx) { 
		QList res = new QList();
		QList left = (QList) visitor.visit(ctx.left);
		QList right = (QList) visitor.visit(ctx.right);
		if (left.is(right)) res.add(null);
		return res;
	}
	
	public QList evalCondEMPTY(XQueryParser.CondEMPTYContext ctx) {
		QList res = new QList();
		if (((QList) visitor.visit(ctx.xq())).isEmpty()) res.add(null);
		return res;
	}
	
	private VMList getVMListSome(int idx, XQueryParser.CondSATISFYContext ctx, VarMap prevVar){
		VMList res = new VMList();
		QList lst = (QList) visitor.visit(ctx.xq(idx));
		if (idx+1==ctx.xq().size()){
			for (Node node:lst){
				VarMap curVar = prevVar.copy();
				curVar.put(ctx.Var(idx).getText(), new QList(node));
				res.add(curVar);
			}
			return res;
		}
		for (Node node:lst){
			nodelstSt.push(new QList(node));
			VarMap curVar = prevVar.copy();
			curVar.put(ctx.Var(idx).getText(), new QList(node));
			varSt.push(curVar);
			res.addAll(getVMListSome(idx+1,ctx,curVar));
			varSt.pop();
			nodelstSt.pop();
		}
		return res;
	}
	
	public QList evalCondSATISFY(XQueryParser.CondSATISFYContext ctx) {
		QList res = new QList();
		VarMap curVarCopy = varSt.peek().copy();
		VMList vmList = getVMListSome(0,ctx,curVarCopy);
		for (VarMap vm:vmList){
			varSt.push(vm);
			if (!((QList) visitor.visit(ctx.cond())).isEmpty()){
				res.add(null);
				varSt.pop();
				return res;
			}
			varSt.pop();
		}
		return res;
	}
	
	public QList evalCondAND(XQueryParser.CondANDContext ctx) {
		QList res = new QList();
		QList left = (QList) visitor.visit(ctx.leftCond);
		QList right = (QList) visitor.visit(ctx.rightCond);
		if (left.and(right)) res.add(null);
		return res;
	}
	
	public QList evalCondOR(XQueryParser.CondORContext ctx) {
		QList res = new QList();
		QList left = (QList) visitor.visit(ctx.leftCond);
		QList right = (QList) visitor.visit(ctx.rightCond);
		if (left.or(right)) res.add(null);
		return res;
	}
	
	public QList evalCondNOT(XQueryParser.CondNOTContext ctx) {
		QList res = new QList();
		QList lst = (QList) visitor.visit(ctx.cond());
		if (lst.isEmpty()) res.add(null);
		return res;
	}
	
	public QList evalXqJoin(XQueryParser.XqJoinContext ctx) { 
		return (QList) visitor.visit(ctx.joinClause());
	}
	
	public QList evalJoinClause(XQueryParser.JoinClauseContext ctx) { 
		QList res = new QList();
		QList ql1 = (QList) visitor.visit(ctx.xq1);
		QList ql2 = (QList) visitor.visit(ctx.xq2);
		String var1 = ctx.varList1.getText();
		String var2 = ctx.varList2.getText();
		String[] varList1= var1.substring(1,var1.length()-1).split(",");
		String[] varList2= var2.substring(1,var2.length()-1).split(",");
		//System.out.println(ql1.toString());
		for (Node node1:ql1){
			for (Node node2:ql2){
				Boolean equal = true;
				for (int i=0;i<varList1.length;++i){
					String tag1 = varList1[i];
					String tag2 = varList2[i];
					//System.out.println(tag1);
					NodeList node1Child = node1.getChildNodes();
					NodeList node2Child = node2.getChildNodes();
					Node node1Tag = null;
					Node node2Tag = null;
					for (int j=0;j<node1Child.getLength();++j){
						if (node1Child.item(j).getNodeName().equals(tag1)){
							node1Tag=node1Child.item(j);
							break;
						}
					}
					for (int j=0;j<node2Child.getLength();++j){
						if (node2Child.item(j).getNodeName().equals(tag2)){
							node2Tag=node2Child.item(j);
							break;
						}
					}
					QList node1TagChild = new QList(node1Tag).getChildren();
					QList node2TagChild = new QList(node2Tag).getChildren();
					
					if (!node1TagChild.eq(node2TagChild)){
						equal = false;
						break;
					}
				}
				if (!equal) continue;
				QList tmp = new QList();
				tmp.addAll(new QList(node1).getChildren());
				tmp.addAll(new QList(node2).getChildren());
				//for (int i=0;i<tmp.size();++i)
					//System.out.println(tmp.get(i).getTextContent());
				Node tuple = makeElem(tmp,"tuple");
				//System.out.println(tuple.getChildNodes().getLength());
				//System.out.println(tuple.getTextContent());
				res.add(tuple);
			}
		}
		
		
		return res;
	}
}