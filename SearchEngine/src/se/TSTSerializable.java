package se;

import java.io.Serializable;
import java.util.Arrays;

import textprocessing.TST;

public class TSTSerializable<Value> extends TST<Value> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		Object[] objList; 
				
		StringBuilder sb= new StringBuilder("");
		for (Object o: this.keys()) {
			sb.append(o.toString() + " => ");
			objList = (Object[]) this.get(o.toString());
			sb.append(Arrays.toString(objList));
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
