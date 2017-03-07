package value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class QList implements XQValue, List<Node>{
	private List<Node> lst;
	
	public QList(){
		lst = new ArrayList<Node>();
	}
	
	public QList(Node node){
		lst = new ArrayList<Node>();
		lst.add(node);
	}
	
	public QList(NodeList nodeLst){
		lst = new ArrayList<Node>();
		for (int i=0;i<nodeLst.getLength();++i)
			lst.add(nodeLst.item(i));
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return lst.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return lst.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return lst.contains(o);
	}

	@Override
	public Iterator<Node> iterator() {
		// TODO Auto-generated method stub
		return lst.iterator();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return lst.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return lst.toArray(a);
	}

	@Override
	public boolean add(Node e) {
		// TODO Auto-generated method stub
		return lst.add(e);
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return lst.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return lst.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Node> c) {
		// TODO Auto-generated method stub
		return lst.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Node> c) {
		// TODO Auto-generated method stub
		return lst.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return lst.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return lst.retainAll(c);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		lst.clear();
	}

	@Override
	public Node get(int index) {
		// TODO Auto-generated method stub
		return lst.get(index);
	}

	@Override
	public Node set(int index, Node element) {
		// TODO Auto-generated method stub
		return lst.set(index, element);
	}

	@Override
	public void add(int index, Node element) {
		// TODO Auto-generated method stub
		lst.add(index, element);
	}

	@Override
	public Node remove(int index) {
		// TODO Auto-generated method stub
		return lst.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return lst.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return lst.lastIndexOf(o);
	}

	@Override
	public ListIterator<Node> listIterator() {
		// TODO Auto-generated method stub
		return lst.listIterator();
	}

	@Override
	public ListIterator<Node> listIterator(int index) {
		// TODO Auto-generated method stub
		return lst.listIterator(index);
	}

	@Override
	public List<Node> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return lst.subList(fromIndex, toIndex);
	}
	
	public Boolean eq(QList ql) {
		if (lst.isEmpty() || ql.isEmpty()) return false;
		for (Node node1:lst){
			for (Node node2:ql){
				if (node1.isEqualNode(node2))
					return true;
			}
		}
		return false;
	}
	
	public Boolean is(QList ql) {
		if (lst.isEmpty() || ql.isEmpty()) return false;
		for (Node node1:lst){
			for (Node node2:ql){
				if (node1.isSameNode(node2))
					return true;
			}
		}
		return false;
	}
	
	public Boolean or(QList ql) {
		return !lst.isEmpty() || !ql.isEmpty();
	}
	
	public Boolean and(QList ql) {
		return !lst.isEmpty() && !ql.isEmpty();
	}
	
	public QList getChildren(){
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
	
	public QList getDescedants(){
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
		return res.removeDup();
	}
	
	public QList getParents(){
		QList res = new QList();
		for (Node node:lst){
			if (node==null) continue;
			if (node.getNodeType()==2){
				res.add(((Attr)node).getOwnerElement());
			}
			else res.add(node.getParentNode());
		}
		return res.removeDup();
	}
	
	public QList removeDup(){
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
	
	public QList copy(){
		QList res = new QList();
		res.addAll(lst);
		return res;
	}
	
	public Node getChildByTag(String tag){
		QList childList = this.getChildren();
		for (int j=0;j<childList.size();++j){
			if (childList.get(j).getNodeName().equals(tag)){
				return childList.get(j);
				
			}
		}
		return null;
	}
}