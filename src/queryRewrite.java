import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class queryRewrite{

	private	String query;
	private Map<String,List<String>> comparison = new HashMap<String,List<String>>();
	private Map<String,List<String>> varGroup = new HashMap<String,List<String>>();
	public	queryRewrite(String q){
		query = q;
	}
	
	public String rewrite(){
		String res="";
		String[] partition = query.split(" ");
		return res;
	}
}