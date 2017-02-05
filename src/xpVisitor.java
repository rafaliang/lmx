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

public class xpVisitor extends XQueryBaseVisitor<List<Node>>{
	private List<Node> curList;
	private xpathEvaluator xpEvaluator;
	private xqueryEvaluator xqEvaluator;
	
	xpVisitor(){
		curList = new ArrayList<Node>();
		xpEvaluator = new xpathEvaluator(this,curList);
		xqEvaluator = new xqueryEvaluator(this,curList);
	}
	
	public List<Node> visitApSL(XQueryParser.ApSLContext ctx){
		return xpEvaluator.evalApSL(ctx);
	}
	
	public List<Node> visitApDSL(XQueryParser.ApDSLContext ctx){
		return xpEvaluator.evalApDSL(ctx);
	}
	
	public List<Node> visitRpSL(XQueryParser.RpSLContext ctx) {
		return xpEvaluator.evalRpSL(ctx);
	}
	
	public List<Node> visitRpDSL(XQueryParser.RpDSLContext ctx) {
		return xpEvaluator.evalRpDSL(ctx);
	}
	
	public List<Node> visitRpTAG(XQueryParser.RpTAGContext ctx) {
		return xpEvaluator.evalRpTAG(ctx);
	}
	
	public List<Node> visitRpATT(XQueryParser.RpATTContext ctx) {
		return xpEvaluator.evalRpATT(ctx);
	}
	
	public List<Node> visitRpDOT(XQueryParser.RpDOTContext ctx) {
		return xpEvaluator.evalRpDOT(ctx);
	}
	
	public List<Node> visitRpDDOT(XQueryParser.RpDDOTContext ctx) {
		return xpEvaluator.evalRpDDOT(ctx);
	}
	
	public List<Node> visitRpTEXT(XQueryParser.RpTEXTContext ctx) {
		return xpEvaluator.evalRpTEXT(ctx);
	}
	
	public List<Node> visitRpPARA(XQueryParser.RpPARAContext ctx) {
		return xpEvaluator.evalRpPARA(ctx);
	}
	
	public List<Node> visitRpSTAR(XQueryParser.RpSTARContext ctx) { 
		return xpEvaluator.evalRpSTAR(ctx);
	}
	
	public List<Node> visitRpCOMMA(XQueryParser.RpCOMMAContext ctx) { 
		return xpEvaluator.evalRpCOMMA(ctx);
	}
	
	public List<Node> visitRpF(XQueryParser.RpFContext ctx) {
		return xpEvaluator.evalRpF(ctx);
	}
	
	public List<Node> visitFilterIS(XQueryParser.FilterISContext ctx) { 
		return xpEvaluator.evalFilterIS(ctx);
	}
	
	public List<Node> visitFilterPara(XQueryParser.FilterParaContext ctx) { 
		return xpEvaluator.evalFilterPara(ctx);
	}
	
	public List<Node> visitFilterRP(XQueryParser.FilterRPContext ctx) { 
		return xpEvaluator.evalFilterRP(ctx);
	}
	
	public List<Node> visitFilterNOT(XQueryParser.FilterNOTContext ctx) {
		return xpEvaluator.evalFilterNOT(ctx);
	}
	
	public List<Node> visitFilterEQ(XQueryParser.FilterEQContext ctx) { 
		return xpEvaluator.evalFilterEQ(ctx);
	}
	
	public List<Node> visitFilterOR(XQueryParser.FilterORContext ctx) { 
		return xpEvaluator.evalFilterOR(ctx);
	}
	
	public List<Node> visitFilterAND(XQueryParser.FilterANDContext ctx) { 
		return xpEvaluator.evalFilterAND(ctx);
	}
}