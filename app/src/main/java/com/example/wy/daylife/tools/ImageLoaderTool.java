package com.example.wy.daylife.tools;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by wy on 2016/10/25.
 */

public class ImageLoaderTool {

    public static ImageLoader loader;
    private static ImageLoaderConfiguration config;

    private ImageLoaderTool(){

    }

    public static ImageLoader getInstance(Context context){
 //       config= new ImageLoaderConfiguration.Builder(context).build();
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");
        config = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheSize(50*1024*1024)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();//开始构建
        // Initialize ImageLoader with configuration.

        loader=ImageLoader.getInstance();
        loader.init(config);
        return loader;
    }

    public static DisplayImageOptions getOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        return options;
    }
}
