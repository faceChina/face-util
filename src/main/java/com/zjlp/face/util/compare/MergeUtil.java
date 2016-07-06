package com.zjlp.face.util.compare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * 集合操作工具�?
 * 
 * @ClassName: MergeUtil
 * @Description: (这里用一句话描述这个类的作用)
 * @author lys
 * @date 2015�?�?�?下午5:57:21
 */
public final class MergeUtil {

	private MergeUtil() {
		// no-implement
	}

	/**
	 * 两个列表的并集（自定义比较器�?
	 * 
	 * @Title: differentialList
	 * @Description: (这里用一句话描述这个方法的作�?
	 * @param l1
	 *            列表1
	 * @param l2
	 *            列表2
	 * @param comp
	 *            比较�?
	 * @return
	 * @date 2015�?�?�?下午5:24:25
	 * @author lys
	 */
	public static <T> List<T> differentialList(List<T> l1, List<T> l2,
			Comparator<T> comp) {
		List<T> cloneList = new ArrayList<T>(l1);
		removeAll(cloneList, l2, comp);
		cloneList.addAll(l2);
		return cloneList;
	}

	/**
	 * 两个列表的并集（自定义比较器�?
	 * 
	 * @Title: differentialList
	 * @Description: (这里用一句话描述这个方法的作�?
	 * @param l1
	 *            列表1
	 * @param l2
	 *            列表2
	 * @return
	 * @date 2015�?�?�?下午5:24:55
	 * @author lys
	 */
	public static <T> List<T> differentialList(List<T> l1, List<T> l2) {
		List<T> cloneList = new ArrayList<T>(l1);
		cloneList.removeAll(l2);
		cloneList.addAll(l2);
		return cloneList;
	}

	/**
	 * 两个列表的交集（自定义比较器�?
	 * 
	 * @Title: intersectionList
	 * @Description: (这里用一句话描述这个方法的作�?
	 * @param l1
	 *            列表1
	 * @param l2
	 *            列表2
	 * @param comp
	 *            比较�?
	 * @return
	 * @date 2015�?�?�?下午3:09:13
	 * @author lys
	 */
	public static <T> List<T> intersectionList(List<T> l1, List<T> l2,
			Comparator<T> comp) {
		List<T> cloneList = new ArrayList<T>(l1);
		Iterator<T> t = cloneList.iterator();
		while (t.hasNext()) {
			if (!contains(l2, t.next(), comp)) {
				t.remove();
			}
		}
		return cloneList;
	}

	/**
	 * 两个列表的交�?
	 * 
	 * @Title: intersectionList
	 * @Description: (这里用一句话描述这个方法的作�?
	 * @param l1
	 *            列表1
	 * @param l2
	 *            列表2
	 * @return
	 * @date 2015�?�?�?下午3:08:47
	 * @author lys
	 */
	public static <T> List<T> intersectionList(List<T> l1, List<T> l2) {
		List<T> cloneList = new ArrayList<T>(l1);
		cloneList.retainAll(l2);
		return cloneList;
	}

	/**
	 * 集合l1中排除集合l2中的元素
	 * 
	 * @Title: removeAll
	 * @Description: (这里用一句话描述这个方法的作�?
	 * @param l1
	 *            列表1
	 * @param l2
	 *            列表2
	 * @param comp
	 *            比较�?
	 * @return
	 * @date 2015�?�?�?下午6:00:46
	 * @author lys
	 */
	public static <T> int removeAll(List<T> l1, List<T> l2, Comparator<T> comp) {
		int removeCount = 0;
		Iterator<T> e = l1.iterator();
		while (e.hasNext()) {
			if (contains(l2, e.next(), comp)) {
				e.remove();
				removeCount++;
			}
		}
		return removeCount;
	}

	private static <T> boolean contains(List<T> list, T t, Comparator<T> comp) {
		return -1 != indexOf(list, t, comp);
	}

	private static <T> int indexOf(List<T> list, T t, Comparator<T> comp) {
		Integer size = list.size();
		if (t == null) {
			for (int i = 0; i < size; i++)
				if (list.get(i) == null)
					return i;
		} else {
			for (int i = 0; i < size; i++)
				if (equals(list.get(i), t, comp))
					return i;
		}
		return -1;
	}

	@SuppressWarnings("unused")
	private static <T> boolean remove(List<T> list, T t, Comparator<T> comp) {
		boolean isRemove = false;
		Iterator<T> e = list.iterator();
		while (e.hasNext()) {
			if (equals(t, e.next(), comp)) {
				e.remove();
				isRemove = true;
			}
		}
		return isRemove;
	}

	private static <T> boolean equals(T t1, T t2, Comparator<T> comp) {
		int result = comp.compare(t1, t2);
		return 0 == result;
	}
}
