package com.pfw.popsicle.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author wulibing
 *
 */
public class CollectionUtils {
	/**
	 * 正序
	 */
	public static final String SORT_ORDER_ASC = "asc";

	/**
	 * 逆序
	 */
	public static final String SORT_ORDER_DESC = "desc";

	/**
	 * 方法名称：sortTheList 内容摘要：根据指定的字段数组对List中的对象进行排序
	 * 
	 * @param list
	 * @param sortFields
	 * @param sortOrder
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sortTheList(List list, String[] sortFields,
			String sortOrder) {
		if (sortFields == null || sortFields.length <= 0) {
			return;
		}
		ArrayList<BeanComparator> sorts = new ArrayList<BeanComparator>();
		Comparator c = ComparableComparator.getInstance();
		c = ComparatorUtils.nullLowComparator(c); // 允许null
		if (StringUtils.equals(sortOrder, CollectionUtils.SORT_ORDER_DESC)) {
			c = ComparatorUtils.reversedComparator(c); // 逆序
		}

		String sortField = null;
		for (int i = 0; i < sortFields.length; i++) {
			sortField = sortFields[i];
			if (StringUtils.isNotEmpty(sortField)) {
				sorts.add(new BeanComparator(sortField, c));
			}
		}
		ComparatorChain multiSort = new ComparatorChain(sorts);
		Collections.sort(list, multiSort);
	}

	/**
	 * 方法名称：sortTheList 内容摘要：根据指定的字段对List中的对象进行排序
	 * 
	 * @param list
	 * @param sortFiled
	 * @param sortOrder
	 */
	@SuppressWarnings({ "rawtypes" })
	public static void sortTheList(List list, String sortFiled, String sortOrder) {
		if (StringUtils.isEmpty(sortFiled)) {
			return;
		}
		String[] sortFields = new String[] { sortFiled };
		sortTheList(list, sortFields, sortOrder);
	}

}
