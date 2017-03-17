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
	private Map<String,List<String>> eqConstant = new HashMap<String,List<String>>();  //var eq constant
	//private Map<String,String> var1EqVar2 = new HashMap<String,String>();  //var1 eq var2
	//private Map<String,String> var2EqVar1 = new HashMap<String,String>();  //var2 eq var1
	//private Map<String,List<List<String>>> varEqElem = new HashMap<String,List<List<String>>>();  // which elem of a var is joined
	private String res="";
	private List<relation> relationLst = new ArrayList<relation>();
	private Map<String,relation> relationMap = new HashMap<String,relation>();
	private boolean canRewrite = true;
	//private String query;
	
	queryRewriter2(){
	}
	
	private class attribute{
		private String name="";
		private List<attribute> eqTo = new ArrayList<attribute>();
		private relation rel= new relation();
		
		attribute(){}
		
		attribute(String n){
			name = n;
			//eqTo.addAll(lst);
		}
		
		attribute(String n, List<attribute> lst){
			name = n;
			eqTo.addAll(lst);
		}
		
		public String getName(){return name;}
		public void setName(String n){name = n;return;}
		public void addEqTo(attribute jnode){
			eqTo.add(jnode);
		}
		public List<attribute> getEqTo(){
			return eqTo;
		}
		public relation getRelation(){
			return rel;
		}
		public void setRelation(relation r){
			rel = r;
		}
	}
	
	private class relation{
		private String name = "";
		private List<attribute> attributes = new ArrayList<attribute>();
		
		relation(){};
		relation(String n){
			name = n;
			//children.addAll(lst);
		}
		relation(String n, List<attribute> lst){
			name = n;
			attributes.addAll(lst);
		}
		
		public String getName(){return name;}
		public void setName(String n){name = n;return;}
		public void addAttr(attribute attr){attributes.add(attr);}
		public List<attribute> getAttributes(){return attributes;}
		
		public boolean canJoin(relation r2){
			for (attribute attr:attributes){
				for (attribute attr2:attr.getEqTo()){
					if (attr2.getRelation()==r2)
						return true;
				}
			}
			return false;
		}
		
		public relation join(relation r2){
			List<attribute> eq1 = new ArrayList<attribute>();
			List<attribute> eq2 = new ArrayList<attribute>();
			for (attribute attr:attributes){
				for (attribute attr2:attr.getEqTo()){
					if (attr2.getRelation()==r2){
						eq1.add(attr);
						eq2.add(attr2);
					}
				}
			}
			List<attribute> newAttributes = new ArrayList<attribute>();
			newAttributes.addAll(attributes);
			newAttributes.addAll(r2.getAttributes());
			relation relationJoined = new relation("relationJoined",newAttributes);
			
			
			
			return relationJoined;
		}
		
		public String getJoinAttributes(relation r2){
			List<attribute> eq1 = new ArrayList<attribute>();
			List<attribute> eq2 = new ArrayList<attribute>();
			for (attribute attr:attributes){
				for (attribute attr2:attr.getEqTo()){
					if (attr2.getRelation()==r2){
						eq1.add(attr);
						eq2.add(attr2);
					}
				}
			}
			String res = "";
			if (eq1.isEmpty()) res+="[], []";
			else{
				res+="[";
				for (attribute att:eq1)
					res += (att.getName().substring(1)+", ");
				res = res.substring(0, res.length()-2);
				res += "], ";
				res+="[";
				for (attribute att:eq2)
					res += (att.getName().substring(1)+", ");
				res = res.substring(0, res.length()-2);
				res += "]";
			}
			return res;
		}
		
		public attribute getAttrByName(String attName){
			for (attribute att:attributes){
				if (att.getName().equals(attName))
					return att;
			}
			return null;
		}
		
		public String toStr(List<String> eqConst, Map<String,String>varMap){
			String res="";
			//res += ("for "+this.getName()+" in "+varMap.get(this.getName())+",\n");
			res += ("for ");
			for (attribute att:attributes){
				String attName=att.getName();
				res+=(attName+" in "+varMap.get(attName)+",\n");
			}
			res = res.substring(0, res.length()-2);
			res+="\n";
			if (eqConst!=null && !eqConst.isEmpty()){
				res += "where ";
				for (String str:eqConst){
					res += (str+" and ");
				}
				res = res.substring(0, res.length()-4);
				res += "\n";
			}
			res+= "return <tuple>{\n";
			String relName = this.getName().substring(1);
			//res+=("<"+relName+">"+"{$"+relName+"}</"+relName+">,\n");
			for (attribute att:attributes){
				String attName=att.getName();
				attName = attName.substring(1);
				res+=("<"+attName+">"+"{$"+attName+"}</"+attName+">,\n");
			}
			res = res.substring(0, res.length()-2);
			res += "\n}</tuple>,";
			return res;
		}
	}
	
	public String rewrite(){
		if (canRewrite)
			//System.out.println(res);
			System.out.println("A rewrite is applied");
		else{
			System.out.println("No rewrite is applied");
			res = "";
		}
		
		return res;
	}
	
	public Integer visitForClause(XQueryParser.ForClauseContext ctx) {
		for (int i=0;i<ctx.xq().size();++i)
			this.visit(ctx.xq(i));
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
					canRewrite = false;
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
		
		
		for (String r:varGroup.keySet()){
			relation rel = new relation(r);
			for (String att:varGroup.get(r)){
				attribute attr = new attribute(att);
				attr.setRelation(rel);
				rel.addAttr(attr);
			}
			relationLst.add(rel);
			relationMap.put(r, rel);
		}
		
		
		
		/*
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
		String var1 = ctx.left.getText();
		String var2 = ctx.right.getText();
		if (var2.startsWith("$")){
			String r1 = var2var.get(var1);
			String r2 = var2var.get(var2);
			if (r1!=r2){
				relation rel1 = relationMap.get(r1);
				relation rel2 = relationMap.get(r2);
				attribute att1 = rel1.getAttrByName(var1);
				attribute att2 = rel2.getAttrByName(var2);
				att1.addEqTo(att2);
				att2.addEqTo(att1);
			}
			else{
				//relation rel1 = relationMap.get(r1);
				//System.out.println(r1);
				if (eqConstant.containsKey(r1)){
					List<String> tmp = eqConstant.get(r1);
					String str = var1+" eq "+var2;
					tmp.add(str);
					//System.out.println(str);
					eqConstant.put(r1, tmp);
				}
				else{
					List<String> tmp = new ArrayList<String>();
					tmp.add(var1+" eq "+var2);
					eqConstant.put(r1, tmp);
				}
			}
		}
		else{
			String r1 = var2var.get(var1);
			//relation rel1 = relationMap.get(r1);
			if (eqConstant.containsKey(r1)){
				List<String> tmp = eqConstant.get(r1);
				tmp.add(var1+" eq "+var2);
				eqConstant.put(r1, tmp);
			}
			else{
				List<String> tmp = new ArrayList<String>();
				tmp.add(var1+" eq "+var2);
				eqConstant.put(r1, tmp);
			}
			
		}
		return 0;
	}
	
	public Integer visitReturnClause(XQueryParser.ReturnClauseContext ctx) { 
		//System.out.println("return clause");
		relation joined = relationLst.get(0);
		res = joined.toStr(eqConstant.get(joined.getName()), varMap);
		relationLst.remove(0);
		
		/*
		while (!relationLst.isEmpty()){
			boolean hasRemoved = false;
			for (int i=0;i<relationLst.size();++i){
				relation rel = relationLst.get(i);
				if (joined.canJoin(rel)){
					relationLst.remove(i);
					hasRemoved = true;
					res += "\n\n";
					res += rel.toStr(eqConstant.get(rel.getName()), varMap);
					res += ("\n"+joined.getJoinAttributes(rel)+"\n),");
					res = "join ("+res;
					joined = joined.join(rel);
					break;
				}
			}
			if (!hasRemoved){
				System.out.println("cannot rewrite the query, please check the query!");
				canRewrite = false;
				return 0;
			}
		}*/
		
		// this implementation allows cartesian product
		for (int i=0;i<relationLst.size();++i){
			relation rel = relationLst.get(i);
			
				
			res += "\n\n";
			res += rel.toStr(eqConstant.get(rel.getName()), varMap);
			res += ("\n"+joined.getJoinAttributes(rel)+"\n),");
			res = "join ("+res;
			joined = joined.join(rel);

		}
		
		
		res = res.substring(0,res.length()-1);
		//System.out.println(res);
		
		res = "for $tuple in "+res;
		
		String str = ctx.xq().getText();
		StringBuffer strBuffer = new StringBuffer(str);
		//System.out.println(strBuffer);
		Boolean isVar = false;
		for (int i=0;i<strBuffer.length();++i){
			if (strBuffer.charAt(i)=='$'){
				isVar = true;
				//strBuffer.replace(i, i+1, "$tuple/");
				//System.out.println(i);
				continue;
			}
			if (isVar){
				if (strBuffer.charAt(i)==',' ||strBuffer.charAt(i)=='}'||strBuffer.charAt(i)==' '){
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
		res += ("\nreturn "+new String(strBuffer));
		//System.out.println(res);
		
		return 0; 
	}
	
	public Integer visitXqJoin(XQueryParser.XqJoinContext ctx) { 
		System.out.println("join already exists");
		canRewrite = false;
		return 0;
	}
	
	
}