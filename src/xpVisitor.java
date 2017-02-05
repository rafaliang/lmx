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

import value.QList;
import value.XQValue;

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

public class xpVisitor extends XQueryBaseVisitor<XQValue>{
	private Stack<QList> nodelstSt;
	private xpathEvaluator xpEvaluator;
	private xqueryEvaluator xqEvaluator;
	
	xpVisitor(){
		nodelstSt = new Stack<QList>();
		xpEvaluator = new xpathEvaluator(this,nodelstSt);
		xqEvaluator = new xqueryEvaluator(this,nodelstSt);
	}
	
	public QList visitApSL(XQueryParser.ApSLContext ctx){
		return xpEvaluator.evalApSL(ctx);
	}
	
	public QList visitApDSL(XQueryParser.ApDSLContext ctx){
		return xpEvaluator.evalApDSL(ctx);
	}
	
	public QList visitRpSL(XQueryParser.RpSLContext ctx) {
		return xpEvaluator.evalRpSL(ctx);
	}
	
	public QList visitRpDSL(XQueryParser.RpDSLContext ctx) {
		return xpEvaluator.evalRpDSL(ctx);
	}
	
	public QList visitRpTAG(XQueryParser.RpTAGContext ctx) {
		return xpEvaluator.evalRpTAG(ctx);
	}
	
	public QList visitRpATT(XQueryParser.RpATTContext ctx) {
		return xpEvaluator.evalRpATT(ctx);
	}
	
	public QList visitRpDOT(XQueryParser.RpDOTContext ctx) {
		return xpEvaluator.evalRpDOT(ctx);
	}
	
	public QList visitRpDDOT(XQueryParser.RpDDOTContext ctx) {
		return xpEvaluator.evalRpDDOT(ctx);
	}
	
	public QList visitRpTEXT(XQueryParser.RpTEXTContext ctx) {
		return xpEvaluator.evalRpTEXT(ctx);
	}
	
	public QList visitRpPARA(XQueryParser.RpPARAContext ctx) {
		return xpEvaluator.evalRpPARA(ctx);
	}
	
	public QList visitRpSTAR(XQueryParser.RpSTARContext ctx) { 
		return xpEvaluator.evalRpSTAR(ctx);
	}
	
	public QList visitRpCOMMA(XQueryParser.RpCOMMAContext ctx) { 
		return xpEvaluator.evalRpCOMMA(ctx);
	}
	
	public QList visitRpF(XQueryParser.RpFContext ctx) {
		return xpEvaluator.evalRpF(ctx);
	}
	
	public QList visitFilterIS(XQueryParser.FilterISContext ctx) { 
		return xpEvaluator.evalFilterIS(ctx);
	}
	
	public QList visitFilterPara(XQueryParser.FilterParaContext ctx) { 
		return xpEvaluator.evalFilterPara(ctx);
	}
	
	public QList visitFilterRP(XQueryParser.FilterRPContext ctx) { 
		return xpEvaluator.evalFilterRP(ctx);
	}
	
	public QList visitFilterNOT(XQueryParser.FilterNOTContext ctx) {
		return xpEvaluator.evalFilterNOT(ctx);
	}
	
	public QList visitFilterEQ(XQueryParser.FilterEQContext ctx) { 
		return xpEvaluator.evalFilterEQ(ctx);
	}
	
	public QList visitFilterOR(XQueryParser.FilterORContext ctx) { 
		return xpEvaluator.evalFilterOR(ctx);
	}
	
	public QList visitFilterAND(XQueryParser.FilterANDContext ctx) { 
		return xpEvaluator.evalFilterAND(ctx);
	}
}