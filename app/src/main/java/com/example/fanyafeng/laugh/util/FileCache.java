package com.example.fanyafeng.laugh.util;

import java.io.File;

import com.example.fanyafeng.laugh.util.FileManagerUtil;

import android.content.Context;

public class FileCache extends AbstractFileCache {

	public FileCache(Context context) {
		super(context);

	}

	@Override
	public String getSavePath(String url) {
		String filename = String.valueOf(url.hashCode());
		return getCacheDir() + filename;
	}

	@Override
	public String getCacheDir() {

		return FileManagerUtil.getSaveFilePath();
	}

}
