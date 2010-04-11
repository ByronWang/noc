package noc.frame.vo.imp;

import java.util.ArrayList;
import java.util.Iterator;

import noc.frame.vo.Vo;
import noc.frame.vo.Vol;

public class VOLImp implements Vol {
	ArrayList<Vo> datas = new ArrayList<Vo>(100);

	public VOLImp() {}

	public VOLImp(String type, Vo... oa) {
		for (Vo o : oa) {
			datas.add(o);
		}
	}

	@Override
	public void add(Vo vo) {
		datas.add(vo);
	}

	@Override
	public Vo get(int index) {
		return datas.get(index);
	}

	@Override
	public Vo get(String keys) {
//		for (VO v : datas) {
//			if (keys.equals(v.getKey())) {
//				return v;
//			}
//		}
		return null;
	}

	@Override
	public Vol lookup(String cause) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void put(Vo vo) {
//		for (VO v : datas) {
//			if (vo.getKey().equals(v.getKey())) {
//				v.add(vo);
//				return;
//			}
//		}
		datas.add(vo);

	}

	@Override
	public Iterator<Vo> iterator() {
		return datas.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (Vo o : datas) {
			sb.append("\n");
			sb.append(o.toString());
			sb.append(",");
		}
		if (sb.length() > 1) {
			sb.setCharAt(sb.length() - 1, '}');
		} else {
			sb.append('}');
		}
		return sb.toString();
	}

	@Override
	public int size() {
		return datas.size();
	}

	@Override
	public String getCanonicalForm() {

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (Vo vo : datas) {
			sb.append(vo.getCanonicalForm());
			sb.append(',');
			sb.append('\n');
		}
		if (sb.length() > 2) {
			sb.setCharAt(sb.length() - 2, ']');
		}else{
			sb.append(']');			
		}
		return sb.toString();
	}

}
