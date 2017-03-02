import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
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

public class queryRewriter2 extends XQueryBaseVisitor<Integer>{
	//private Map<String,List<String>> varComparison = new HashMap<String,List<String>>();    //variable and comparison of its group
	private Map<String,List<String>> varGroup = new HashMap<String,List<String>>();     //variable and its 'children'
	private Map<String,String> varMap = new HashMap<String,String>();     //variable and its definition
	private Map<String,String> var2var = new HashMap<String,String>();    //the group a variable belongs to
	//private Map<String,String> comparison = new HashMap<String,String>();  //var1 eq var2
	private Map<String,String> eqConstant = new HashMap<String,String>();  //var eq constant
	//private Map<String,String> var1EqVar2 = new HashMap<String,String>();  //var1 eq var2
	//private Map<String,String> var2EqVar1 = new HashMap<String,String>();  //var2 eq var1
	private Map<String,List<List<String>>> varEqElem = new HashMap<String,List<List<String>>>();  // which elem of a var is joined
	private String returnClause="";
	//private String query;
	
	queryRewriter2(){
		//query = q;
	}
	
	public String rewrite(){
		String res = "";
		Set<String> used = new HashSet<String>();
		//Set<String> unused = new HashSet

		for (Map.Entry<String,List<List<String>>> entry : varEqElem.entrySet()) { 
			String str = entry.getKey();
			List<List<String>> lstLst = entry.getValue();
			String[] varLst = str.split("-");
			String var1 = varLst[0];
			String var2 = varLst[1];
		}
		
		return res;
	}
	
	public Integer visitForClause(XQueryParser.ForClauseContext ctx) {
		for (int i=0;i<ctx.Var().size();++i){
			String varName = ctx.Var().get(i).getText();
			//varName = varName.substring(1);
			String path = ctx.xq().get(i).getText();
			varMap.put(varName, path);
			if (path.startsWith("$")){
				String var = "";
				for (int j=0;j<path.length();++j){
					if (path.charAt(j)=='/') break;
					var += path.charAt(j);
				}
				//System.out.println(var);
				var = var2var.get(var);
				if (varMap.containsKey(var)){
					List<String> lst= varGroup.get(var);
					lst.add(varName);
					varGroup.put(var, lst);
					var2var.put(varName, var);
				}
				else{
					System.out.println("Illegal input");
					return 0;
				}
				
			}
			else{
				//System.out.println(varName);
				var2var.put(varName, varName);
				List<String> lst = new ArrayList<String>();
				lst.add(varName);
				varGroup.put(varName, lst);
			}
			
		}/*
		for (String var:varGroup.keySet()){
			System.out.println(var);
			List<String> group = varGroup.get(var);
			for (String var2:group){
				System.out.println(var2);
				System.out.println(varMap.get(var2));
			}
		}*/
		return 0;
	}
	
	/*public Integer visitWhereClause(XQueryParser.WhereClauseContext ctx) { 
		String condition = ctx.cond().getText();
		System.out.println(condition);
		return 0;
	}*/
	
	public Integer visitCondEQ(XQueryParser.CondEQContext ctx) { 
		String str1 = ctx.left.getText();
		String str2 = ctx.right.getText();
		if (!str2.startsWith("$")){
			eqConstant.put(str1, str2);
		}
		else{
			//var1EqVar2.put(str1, str2);
			//var2EqVar1.put(str2, str1);
			String var1 = var2var.get(str1);
			String var2 = var2var.get(str2);
			//System.out.println(var1);
			//System.out.println(var2);
			String str="";
			if (var1.compareTo(var2)<0)
				str = var1+"-"+var2;
			else{
				String tmp;
				tmp = str1;
				str1 = str2;
				str2 = tmp;
				str = var2+"-"+var1;
			}
			if (varEqElem.containsKey(str)){
				List<List<String>> lst = varEqElem.get(str);
				lst.get(0).add(str1);
				lst.get(1).add(str2);
				//System.out.println(lst);
			}
			else{
				List<List<String>> lst = new ArrayList<List<String>>();
				List<String> lst0 = new ArrayList<String>();
				lst0.add(str1);
				List<String> lst1 = new ArrayList<String>();
				lst1.add(str2);
				lst.add(lst0);
				lst.add(lst1);
				varEqElem.put(str, lst);
				//System.out.println(lst);
			}
			//varEqElem.put(str, value)
		}
		return 0;
	}
	
	public Integer visitReturnClause(XQueryParser.ReturnClauseContext ctx) { 
		String str = ctx.xq().getText();
		StringBuffer strBuffer = new StringBuffer(str);
		System.out.println(strBuffer);
		Boolean isVar = false;
		for (int i=0;i<strBuffer.length();++i){
			if (strBuffer.charAt(i)=='$'){
				isVar = true;
				//strBuffer.replace(i, i+1, "$tuple/");
				//System.out.println(i);
				continue;
			}
			if (isVar){
				if (strBuffer.charAt(i)==',' ||strBuffer.charAt(i)=='}'){
					//System.out.println(i);
					strBuffer.insert(i, "/*");
				}
				else if (strBuffer.charAt(i)=='/'){
					//System.out.println(i);
					strBuffer.insert(i, "/*");
				}
				else continue;
				isVar = false;
			}
		}
		
		for (int i=0;i<strBuffer.length();++i){
			if (strBuffer.charAt(i)=='$'){
				strBuffer.replace(i, i+1, "$tuple/");
				//System.out.println(i);
				//continue;
			}
		}
		returnClause = new String(strBuffer);
		//System.out.println(returnClause);
		return 0; 
	}
}