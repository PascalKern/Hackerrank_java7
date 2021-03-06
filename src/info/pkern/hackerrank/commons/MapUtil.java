package info.pkern.hackerrank.commons;
/* ============================================================================
 * Copyright (c) 2015 Pascal Kern
 * 
 * http://github.com/PascalKern/Hackerrank_java7
 * http://www.pkern.info/
 * ============================================================================
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.SortOrder;

/**
 * @version 0.1 - (Hackerrank Solution-Tools)
 * @author Pascal Kern
 */
public class MapUtil {

	public static enum SORT_ORDER{ASC, DESC};
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValuesDescending(Map<K, V> map) {
		return sortByValues(map, SORT_ORDER.DESC);
	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValues(Map<K, V> map, final SORT_ORDER sortOrder) {
		List<Entry<K, V>> sorted = sortAsListByValues(map, sortOrder);
		Map<K, V> sortedMap = new LinkedHashMap<>(sorted.size());
		for(Iterator<Map.Entry<K, V>> iterator = sorted.listIterator(); iterator.hasNext();) {
			Map.Entry<K, V> entry = iterator.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortAsListByValuesDescending(Map<K, V> map) {
		return sortAsListByValues(map, SORT_ORDER.DESC);
	}
	public static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortAsListByValues(Map<K, V> map, final SORT_ORDER sortOrder) {
		List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(map.entrySet());
		Collections.sort(sortedEntries, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> e1, Entry<K, V> e2) {
				if (sortOrder.equals(SORT_ORDER.ASC)) {
					return e1.getValue().compareTo(e2.getValue());
				} else {
					return e2.getValue().compareTo(e1.getValue());
				}
			}
		});
		return sortedEntries;
	}
	
	
	public static <K, V extends Comparable<? super V>> void sortByValueDescending(List<Entry<K, V>> list) {
		sortByValue(list, SORT_ORDER.DESC);
	}
	public static <K, V extends Comparable<? super V>> void sortByValue(List<Entry<K, V>> list, final SORT_ORDER sortOrder) {
		Collections.sort(list, new Comparator<Entry<K, V>>() {
			@Override
			public int compare(Entry<K, V> o1, Entry<K, V> o2) {
				if (sortOrder.equals(SORT_ORDER.ASC)) {
					return o1.getValue().compareTo(o2.getValue());
				} else {
					return o2.getValue().compareTo(o1.getValue());
				}
			}
		});
	}
	
	
	//TODO Fix or remove!
	//Just a try. Not yet "working"/usable.
	public class EntryComparator<K, V extends Comparable<? super V>> implements Comparator<Entry<K, V>> {
		private SORT_ORDER sortOrder;
		public EntryComparator(SORT_ORDER sortOrder) {
			this.sortOrder = sortOrder;
		}
		public EntryComparator() {
			this(SORT_ORDER.ASC);
		}
		@Override
		public int compare(Entry<K, V> o1, Entry<K, V> o2) {
			if (sortOrder.equals(SORT_ORDER.ASC)) {
				return o1.getValue().compareTo(o2.getValue());
			} else {
				return o2.getValue().compareTo(o1.getValue());
			}
		}
	}
}
