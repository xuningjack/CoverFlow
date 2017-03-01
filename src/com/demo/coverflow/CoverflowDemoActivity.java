package com.demo.coverflow;

import android.app.Activity;

import android.graphics.Color;
import android.os.Bundle;

/**
 * 主界面
 * @author Jack
 * @version 创建时间：2013-10-31  下午2:13:47
 */
public class CoverflowDemoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		CoverFlow cf = new CoverFlow(this);
		cf.setBackgroundColor(Color.BLACK);
		cf.setAdapter(new ImageAdapter(this));
		ImageAdapter imageAdapter = new ImageAdapter(this);
		cf.setAdapter(imageAdapter);
//		 cf.setAlphaMode(false);			//两边未selected的图片不透明
//		 cf.setCircleMode(false);
		cf.setSelection(Integer.MAX_VALUE / 2, true);
		cf.setAnimationDuration(1000);
		setContentView(cf);
	}
}