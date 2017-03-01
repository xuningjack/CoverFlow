package com.demo.coverflow;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.util.DisplayMetrics;

public class MyApp extends Application {

	@Override
	public void onCreate() {
		
		super.onCreate();
		//注册ImageLoader
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
		ImageLoaderConfiguration config = ImageLoaderUtils.getApplicationOptions(displayMetrics.widthPixels , displayMetrics.heightPixels, getApplicationContext());
	    ImageLoader.getInstance().init(config);
	}
}
