package value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VarMap implements XQValue, Map<String,QList>{
	
	private Map<String,QList> vm;
	
	public VarMap(){
		vm = new HashMap<String,QList>();
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return vm.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return vm.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return vm.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return vm.containsValue(value);
	}

	@Override
	public QList get(Object key) {
		// TODO Auto-generated method stub
		return vm.get(key);
	}

	@Override
	public QList put(String key, QList value) {
		// TODO Auto-generated method stub
		return vm.put(key, value);
	}

	@Override
	public QList remove(Object key) {
		// TODO Auto-generated method stub
		return vm.remove(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends QList> m) {
		// TODO Auto-generated method stub
		vm.putAll(m);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		vm.clear();
	}

	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return vm.keySet();
	}

	@Override
	public Collection<QList> values() {
		// TODO Auto-generated method stub
		return vm.values();
	}

	@Override
	public Set<java.util.Map.Entry<String, QList>> entrySet() {
		// TODO Auto-generated method stub
		return vm.entrySet();
	}
	
	public VarMap copy(){
		VarMap res = new VarMap();
		res.putAll(vm);
		return res;
	}
	
}