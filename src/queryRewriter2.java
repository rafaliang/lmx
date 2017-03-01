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

public class queryRewriter2 extends XQueryBaseVisitor<Integer>{
	private Map<String,List<String>> varComparison = new HashMap<String,List<String>>();    //variable and comparison of its group
	private Map<String,List<String>> varGroup = new HashMap<String,List<String>>();     //variable and its 'children'
	private Map<String,String> varMap = new HashMap<String,String>();     //variable and its definition
	private Map<String,String> var2var = new HashMap<String,String>();    //the group a variable belongs to
	private Map<String,String> comparison = new HashMap<String,String>();  //var1 eq var2
	//private String query;
	
	queryRewriter2(){
		//query = q;
	}
	
	public String rewrite(){
		String res = "";
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
			
		}
		for (String var:varGroup.keySet()){
			System.out.println(var);
			List<String> group = varGroup.get(var);
			for (String var2:group){
				System.out.println(var2);
				System.out.println(varMap.get(var2));
			}
		}
		return 0;
	}
	
	/*public Integer visitWhereClause(XQueryParser.WhereClauseContext ctx) { 
		String condition = ctx.cond().getText();
		System.out.println(condition);
		return 0;
	}*/
	
	public Integer visitCondEQ(XQueryParser.CondEQContext ctx) { 
		String var1 = ctx.left.getText();
		String var2 = ctx.right.getText();
		return 0;
	}
}