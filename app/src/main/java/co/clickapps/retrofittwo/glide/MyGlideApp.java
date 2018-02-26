package co.clickapps.retrofittwo.glide;

import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * Created by clickapps on 20/9/17.
 */
@GlideModule
public final class MyGlideApp extends AppGlideModule {


    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        super.applyOptions(context, builder);
        int size = 250 * 1024 * 1024;
//        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context,"retrofitGlideDiskName",size));
        builder.setDiskCache(new DiskLruCacheFactory(new DiskLruCacheFactory.CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                return context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            }
        },size));
    }
}
