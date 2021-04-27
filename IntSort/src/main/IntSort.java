package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class IntSort {
	public static void arraySortWithBounds (int array[] ,int lb, int rb) {
		if( rb <= lb )
			return;
		int m = (rb + lb) / 2;
		arraySortWithBounds(array ,lb ,m);
		arraySortWithBounds(array ,m+1 , rb);
		int i = lb ,j = m+1;
		int tmp[] = new int[rb -lb + 1];
		int tmpCursor = 0;
		while(tmpCursor < tmp.length){
				if(i > m ) {
					System.arraycopy(array, j, tmp, tmpCursor, rb - j + 1);
					break;
				}
				if(j > rb ) {
					System.arraycopy(array, i, tmp, tmpCursor, m - i + 1);
					break;
				}
				if(array[i] >= array[j]) {
					tmp[tmpCursor] = array[j];
					j++;
					tmpCursor++;
				}
				else {
					tmp[tmpCursor] = array[i];
					i++;
					tmpCursor++;
				}
		}
		System.arraycopy(tmp, 0, array, lb, tmp.length);
	}
	
	public static void listSort (List<Integer> list) {
		if(list.size() < 2)
			return;
		int m=(list.size()-1) / 2;
		IntSort.listSort(list.subList(0, m+1));
		IntSort.listSort(list.subList(m+1,list.size()));
		List<Integer> tmp = new ArrayList<Integer>(list.size());
		for(int i = 0; i < list.size(); i++)
			tmp.add(0);
		int i = 0, j = m+1, tmpCursor = 0;
		while(tmpCursor < list.size()){
			if(i == m + 1 ) {
				Collections.copy(tmp.subList(tmpCursor, tmp.size()), list.subList(j, list.size()));
				break;
			}
			if(j == list.size()) {
				Collections.copy(tmp.subList(tmpCursor, tmp.size()), list.subList(i, m + 1));
				break;
			}
			if(list.get(i) >= list.get(j)) {
				tmp.set(tmpCursor, list.get(j));
				j++;
				tmpCursor++;
			}
			else {
				tmp.set(tmpCursor, list.get(i));
				i++;
				tmpCursor++;
			}
		}
		Collections.copy(list, tmp);
	}
  public static void sort (int array[]) {
	  IntSort.arraySortWithBounds(array, 0, array.length - 1);
  }

  public static void sort (List<Integer> list) {
	  IntSort.listSort(list);
  }
}
