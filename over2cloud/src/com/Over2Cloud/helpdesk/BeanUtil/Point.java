package com.Over2Cloud.helpdesk.BeanUtil;

public class Point {
	
	int first_value=0;
	int second_value=0;
	public Point(int i, int j)
	{
		this.first_value=i;
		this.second_value=j;
	}
	public int getFirst_value() {
		return first_value;
	}
	public void setFirst_value(int first_value) {
		this.first_value = first_value;
	}
	public int getSecond_value() {
		return second_value;
	}
	public void setSecond_value(int second_value) {
		this.second_value = second_value;
	}
}
