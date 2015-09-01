package com.common.library.llj.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;

/**
 * 将资源文件解析成字符串
 * 
 * @author llj
 * 
 */
public class ResourceUtilLj {
	public static String geFileFromAssets(Context context, String fileName) {
		if (context == null || TextUtils.isEmpty(fileName)) {
			return null;
		}
		StringBuilder s = new StringBuilder("");
		try {
			InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				s.append(line);
			}
			return s.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String geFileFromRaw(Context context, int resId) {
		if (context == null) {
			return null;
		}

		StringBuilder s = new StringBuilder();
		try {
			InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				s.append(line);
			}
			return s.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> geFileToListFromAssets(Context context, String fileName) {
		if (context == null || TextUtils.isEmpty(fileName)) {
			return null;
		}

		List<String> fileContent = new ArrayList<String>();
		try {
			InputStreamReader in = new InputStreamReader(context.getResources().getAssets().open(fileName));
			BufferedReader br = new BufferedReader(in);
			String line;
			while ((line = br.readLine()) != null) {
				fileContent.add(line);
			}
			br.close();
			return fileContent;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<String> geFileToListFromRaw(Context context, int resId) {
		if (context == null) {
			return null;
		}

		List<String> fileContent = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			InputStreamReader in = new InputStreamReader(context.getResources().openRawResource(resId));
			reader = new BufferedReader(in);
			String line = null;
			while ((line = reader.readLine()) != null) {
				fileContent.add(line);
			}
			reader.close();
			return fileContent;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
