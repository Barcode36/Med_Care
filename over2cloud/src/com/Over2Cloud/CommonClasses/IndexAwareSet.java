package com.Over2Cloud.CommonClasses;

import java.util.Set;

public class IndexAwareSet<T> {
	
	 public  int getIndex(Set<? extends T> set, T value) {
		   int result = 0;
		   for (T entry:set) {
			  if (entry.equals(value))
		    	 return result;
		     result++;
		   }
		   return -1;
		 }
	 }