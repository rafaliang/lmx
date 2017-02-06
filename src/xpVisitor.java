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
import value.VarMap;
import value.XQValue;
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
	private Stack<VarMap> varSt;
	private xpathEvaluator xpEvaluator;
	private xqueryEvaluator xqEvaluator;
	
	xpVisitor(){
		nodelstSt = new Stack<QList>();
		varSt = new Stack<VarMap>();
		varSt.push(new VarMap());
		nodelstSt.push(new QList());
		xpEvaluator = new xpathEvaluator(this,nodelstSt);
		xqEvaluator = new xqueryEvaluator(this,nodelstSt,varSt);
	}
	
	public XQValue visitApSL(XQueryParser.ApSLContext ctx){
		return xpEvaluator.evalApSL(ctx);
	}
	
	public XQValue visitApDSL(XQueryParser.ApDSLContext ctx){
		return xpEvaluator.evalApDSL(ctx);
	}
	
	public XQValue visitRpSL(XQueryParser.RpSLContext ctx) {
		return xpEvaluator.evalRpSL(ctx);
	}
	
	public XQValue visitRpDSL(XQueryParser.RpDSLContext ctx) {
		return xpEvaluator.evalRpDSL(ctx);
	}
	
	public XQValue visitRpTAG(XQueryParser.RpTAGContext ctx) {
		return xpEvaluator.evalRpTAG(ctx);
	}
	
	public XQValue visitRpATT(XQueryParser.RpATTContext ctx) {
		return xpEvaluator.evalRpATT(ctx);
	}
	
	public XQValue visitRpDOT(XQueryParser.RpDOTContext ctx) {
		return xpEvaluator.evalRpDOT(ctx);
	}
	
	public XQValue visitRpDDOT(XQueryParser.RpDDOTContext ctx) {
		return xpEvaluator.evalRpDDOT(ctx);
	}
	
	public XQValue visitRpTEXT(XQueryParser.RpTEXTContext ctx) {
		return xpEvaluator.evalRpTEXT(ctx);
	}
	
	public XQValue visitRpPARA(XQueryParser.RpPARAContext ctx) {
		return xpEvaluator.evalRpPARA(ctx);
	}
	
	public XQValue visitRpSTAR(XQueryParser.RpSTARContext ctx) { 
		return xpEvaluator.evalRpSTAR(ctx);
	}
	
	public XQValue visitRpCOMMA(XQueryParser.RpCOMMAContext ctx) { 
		return xpEvaluator.evalRpCOMMA(ctx);
	}
	
	public XQValue visitRpF(XQueryParser.RpFContext ctx) {
		return xpEvaluator.evalRpF(ctx);
	}
	
	public XQValue visitFilterIS(XQueryParser.FilterISContext ctx) { 
		return xpEvaluator.evalFilterIS(ctx);
	}
	
	public XQValue visitFilterPara(XQueryParser.FilterParaContext ctx) { 
		return xpEvaluator.evalFilterPara(ctx);
	}
	
	public XQValue visitFilterRP(XQueryParser.FilterRPContext ctx) { 
		return xpEvaluator.evalFilterRP(ctx);
	}
	
	public XQValue visitFilterNOT(XQueryParser.FilterNOTContext ctx) {
		return xpEvaluator.evalFilterNOT(ctx);
	}
	
	public XQValue visitFilterEQ(XQueryParser.FilterEQContext ctx) { 
		return xpEvaluator.evalFilterEQ(ctx);
	}
	
	public XQValue visitFilterOR(XQueryParser.FilterORContext ctx) { 
		return xpEvaluator.evalFilterOR(ctx);
	}
	
	public XQValue visitFilterAND(XQueryParser.FilterANDContext ctx) { 
		return xpEvaluator.evalFilterAND(ctx);
	}
	
	public XQValue visitXqAP(XQueryParser.XqAPContext ctx) { 
		return xqEvaluator.evalXqAP(ctx); 
	}
	
	public XQValue visitXqVAR(XQueryParser.XqVARContext ctx) { 
		return xqEvaluator.evalXqVAR(ctx); 
	}
	
	public XQValue visitXqPARA(XQueryParser.XqPARAContext ctx) { 
		return xqEvaluator.evalXqPARA(ctx); 
	}
	
	public XQValue visitXqSL(XQueryParser.XqSLContext ctx) { 
		return xqEvaluator.evalXqSL(ctx); 
	}
	
	public XQValue visitXqDSL(XQueryParser.XqDSLContext ctx) { 
		return xqEvaluator.evalXqDSL(ctx); 
	}
	
	public XQValue visitXqString(XQueryParser.XqStringContext ctx) { 
		return xqEvaluator.evalXqString(ctx); 
	}
	
	public XQValue visitXqLET(XQueryParser.XqLETContext ctx) { 
		return xqEvaluator.evalXqLET(ctx); 
	}
	
	public XQValue visitXqTAG(XQueryParser.XqTAGContext ctx) { 
		return xqEvaluator.evalXqTAG(ctx); 
	}
	
	public XQValue visitXqCOMMA(XQueryParser.XqCOMMAContext ctx) { 
		return xqEvaluator.evalXqCOMMA(ctx); 
	}
	
	public XQValue visitXqFOR(XQueryParser.XqFORContext ctx) { 
		return xqEvaluator.evalXqFOR(ctx); 
	}
	
	public XQValue visitForClause(XQueryParser.ForClauseContext ctx) { 
		return xqEvaluator.evalForClause(ctx); 
	}
	
	public XQValue visitLetClause(XQueryParser.LetClauseContext ctx) { 
		return xqEvaluator.evalLetClause(ctx); 
	}
	
	public XQValue visitWhereClause(XQueryParser.WhereClauseContext ctx) { 
		return xqEvaluator.evalWhereClause(ctx); 
	}
	
	public XQValue visitReturnClause(XQueryParser.ReturnClauseContext ctx) { 
		return xqEvaluator.evalReturnClause(ctx); 
	}
	
	public XQValue visitCondEQ(XQueryParser.CondEQContext ctx) { 
		return xqEvaluator.evalCondEQ(ctx); 
	}
	
	public XQValue visitCondPARA(XQueryParser.CondPARAContext ctx) { 
		return xqEvaluator.evalCondPARA(ctx); 
	}
	
	public XQValue visitCondIS(XQueryParser.CondISContext ctx) { 
		return xqEvaluator.evalCondIS(ctx); 
	}
	
	public XQValue visitCondEMPTY(XQueryParser.CondEMPTYContext ctx) { 
		return xqEvaluator.evalCondEMPTY(ctx); 
	}
	
	public XQValue visitCondSATISFY(XQueryParser.CondSATISFYContext ctx) { 
		return xqEvaluator.evalCondSATISFY(ctx); 
	}
	
	public XQValue visitCondAND(XQueryParser.CondANDContext ctx) { 
		return xqEvaluator.evalCondAND(ctx); 
	}
	
	public XQValue visitCondOR(XQueryParser.CondORContext ctx) { 
		return xqEvaluator.evalCondOR(ctx); 
	}
	
	public XQValue visitCondNOT(XQueryParser.CondNOTContext ctx) { 
		return xqEvaluator.evalCondNOT(ctx); 
	}
}