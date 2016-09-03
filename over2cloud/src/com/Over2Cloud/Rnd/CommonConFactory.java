package com.Over2Cloud.Rnd;

public class CommonConFactory implements CommonInterFace{

	@Override
	public CommonOperInterface createInterface() {
		// TODO Auto-generated method stub
		return new createTable();
	}

}
