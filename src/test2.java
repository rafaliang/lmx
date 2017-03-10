

import java.util.ArrayList;
import java.util.List;


    
public class test2 
{
	public class attribute{
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
	
	public class relation{
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
		
		public relation join(relation r2, String str){
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
			str = "aaa";
			return relationJoined;
		}
	}
	
	public static void main( String[] args) throws Exception 
    {
    	relation student = new test2().new relation("student");
    	relation teacher = new test2().new relation("teacher");
    	relation classes = new test2().new relation("classes");
    	
    	attribute studentName = new test2().new attribute("studentName");
    	attribute takeClass = new test2().new attribute("takeClass");
    	attribute teacherName = new test2().new attribute("teacherName");
    	attribute teachClass = new test2().new attribute("teachClass");
    	attribute classroom = new test2().new attribute("classroom");
    	attribute classId = new test2().new attribute("classId");
    	
    	student.addAttr(studentName);
    	student.addAttr(takeClass);
    	teacher.addAttr(teachClass);
    	teacher.addAttr(teacherName);
    	classes.addAttr(classroom);
    	classes.addAttr(classId);
    	
    	studentName.setRelation(student);
    	takeClass.setRelation(student);
    	teacherName.setRelation(teacher);
    	teachClass.setRelation(teacher);
    	classroom.setRelation(classes);
    	classId.setRelation(classes);
    	
    	takeClass.addEqTo(teachClass);
    	teachClass.addEqTo(takeClass);
    	classId.addEqTo(takeClass);
    	takeClass.addEqTo(classId);
    	
    	
    	String str="abcde";
    	System.out.println(str.substring(1));
    	/*
    	System.out.println("join1");
    	//System.out.println(student.canJoin(teacher));
    	relation studentTeacher = student.join(teacher, str);
    	for (attribute attr:studentTeacher.getAttributes()){
    		System.out.println(attr.getName());
    	}
    	System.out.println("join2");
    	relation studentTeacherClasses = studentTeacher.join(classes, str);
    	for (attribute attr:studentTeacherClasses.getAttributes()){
    		System.out.println(attr.getName());
    	}*/
    	//System.out.println(str);
        
    }
    
}
