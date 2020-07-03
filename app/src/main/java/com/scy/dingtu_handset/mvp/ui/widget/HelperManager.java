package com.scy.dingtu_handset.mvp.ui.widget;


public class HelperManager {

	private HelperManager(){
		
	}

	public static AnimationsHelper getAnimationsHelper(){
		return AnimationsHelper.getSington();
	}

}