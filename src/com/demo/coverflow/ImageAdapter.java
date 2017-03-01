package com.demo.coverflow;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


/**
 * CoverFlow的Adapter
 * @author Jack
 * @version 创建时间：2013-10-31  下午4:16:17
 */
public class ImageAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context mContext;
	/*private Integer[] mImageIds = { R.drawable.a, R.drawable.b,
			R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, 
			R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j
			, R.drawable.k, R.drawable.l, R.drawable.m};*/
	private String[] mImageUrls = {"http://img0.pcgames.com.cn/pcgames/1408/02/4162655_IMG_9462.jpg", 
			"http://img0.pcgames.com.cn/pcgames/1408/02/4162655_IMG_9466_thumb.jpg", 
			"http://img0.pcgames.com.cn/pcgames/1408/02/4162655_IMG_9478_thumb.jpg", 
			"http://img0.pcgames.com.cn/pcgames/1408/02/4162655_IMG_9504_thumb.jpg", 
			"http://img0.pcgames.com.cn/pcgames/1408/02/4162655_IMG_9552_thumb.jpg", 
			"http://img0.pcgames.com.cn/pcgames/1408/02/4162655_IMG_9581_thumb.jpg"};
	
	public ImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = createReflectedImages(mContext, mImageUrls[position % mImageUrls.length]);
		imageView.setLayoutParams(new CoverFlow.LayoutParams(120, 100));
		imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		return imageView;
	}

	
	/**
	 * 倒影处理逻辑
	 * @param mContext
	 * @param imageUrl
	 * @return
	 */
	public ImageView createReflectedImages(Context mContext, String imageUrl) {
		ImageView imageView = new ImageView(mContext);
		ImageLoader.getInstance().displayImage(imageUrl, imageView, ImageLoaderUtils.getDefaultOptions(), new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				
			}
			
			@Override
			public void onLoadingComplete(String url, View imageView, Bitmap originalImage) {
				final int REFLECTIONGAP = 4;
				int width = originalImage.getWidth();
				int height = originalImage.getHeight();

				//Matrix 旋转、镜像处理的矩阵类
				Matrix matrix = new Matrix();
				matrix.preScale(1, -1);
				Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
				Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmapWithReflection);
				canvas.drawBitmap(originalImage, 0, 0, null);
				Paint deafaultPaint = new Paint();
				canvas.drawRect(0, height, width, height + REFLECTIONGAP, deafaultPaint);
				canvas.drawBitmap(reflectionImage, 0, height + REFLECTIONGAP, null);
				Paint paint = new Paint();
				//线性渐变
				LinearGradient shader = new LinearGradient(0,
						originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
								+ REFLECTIONGAP, 0x70ffffff, 0x00ffffff, TileMode.MIRROR);
				paint.setShader(shader);
				//设置两张图片相交时的模式 ，倒影图片隐藏一部分
				paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));	
				//按照设置绘制倒影图片
				canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + REFLECTIONGAP, paint);
				((ImageView)imageView).setImageBitmap(bitmapWithReflection);
				// 设置的抗锯齿
				BitmapDrawable drawable = (BitmapDrawable) ((ImageView)imageView).getDrawable();
				drawable.setAntiAlias(true);
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
			}
		});
		return imageView;
	}
}
