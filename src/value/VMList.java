package value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class VMList implements XQValue, List<VarMap>{

	private List<VarMap> lst;
	
	public VMList(){
		lst = new ArrayList<VarMap>();
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
	public Iterator<VarMap> iterator() {
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
	public boolean add(VarMap e) {
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
	public boolean addAll(Collection<? extends VarMap> c) {
		// TODO Auto-generated method stub
		return lst.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends VarMap> c) {
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
	public VarMap get(int index) {
		// TODO Auto-generated method stub
		return lst.get(index);
	}

	@Override
	public VarMap set(int index, VarMap element) {
		// TODO Auto-generated method stub
		return lst.set(index, element);
	}

	@Override
	public void add(int index, VarMap element) {
		// TODO Auto-generated method stub
		lst.add(index, element);
	}

	@Override
	public VarMap remove(int index) {
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
	public ListIterator<VarMap> listIterator() {
		// TODO Auto-generated method stub
		return lst.listIterator();
	}

	@Override
	public ListIterator<VarMap> listIterator(int index) {
		// TODO Auto-generated method stub
		return lst.listIterator(index);
	}

	@Override
	public List<VarMap> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return lst.subList(fromIndex, toIndex);
	}
	
}