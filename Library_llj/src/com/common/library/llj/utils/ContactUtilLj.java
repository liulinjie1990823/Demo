package com.common.library.llj.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import com.common.library.llj.R;

/**
 * 获取手机联系人信息
 * 
 * @author liulj
 * 
 */
public class ContactUtilLj {
	private static final String[] PHONES_PROJECTION = new String[] { Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };

	/**
	 * 得到手机通讯录联系人信息
	 * 
	 * @param context
	 * @return
	 */
	public static List<ContactVo> getPhoneContacts(Context context) {
		List<ContactVo> mContactVos = new ArrayList<ContactUtilLj.ContactVo>();
		ContentResolver resolver = context.getContentResolver();
		// 获取手机联系人
		Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {
				// 得到手机号码
				String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.DISPLAY_NAME));
				// 得到联系人ID
				Long contactid = phoneCursor.getLong(phoneCursor.getColumnIndex(Phone.CONTACT_ID));
				// 得到联系人头像ID
				Long photoid = phoneCursor.getLong(phoneCursor.getColumnIndex(Photo.PHOTO_ID));
				// 得到联系人头像Bitamp
				Bitmap contactPhoto = null;
				// photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
				if (photoid > 0) {
					Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
					InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
					contactPhoto = BitmapFactory.decodeStream(input);
				} else {
					contactPhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
				}
				ContactVo contactVo = new ContactUtilLj.ContactVo();
				contactVo.setName(contactName);
				contactVo.setPhoneNumber(phoneNumber);
				contactVo.setBitmap(contactPhoto);
				mContactVos.add(contactVo);
			}
			phoneCursor.close();
		}
		return mContactVos;
	}

	/**
	 * 得到手机SIM卡联系人人信息
	 * 
	 * @param context
	 * @return
	 */
	public static List<ContactVo> getSIMContacts(Context context) {
		List<ContactVo> mContactVos = new ArrayList<ContactUtilLj.ContactVo>();
		ContentResolver resolver = context.getContentResolver();
		// 获取Sims卡联系人
		Uri uri = Uri.parse("content://icc/adn");
		Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
		if (phoneCursor != null) {
			while (phoneCursor.moveToNext()) {

				// 得到手机号码
				String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.NUMBER));
				// 当手机号码为空的或者为空字段 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
					continue;
				// 得到联系人名称
				String contactName = phoneCursor.getString(phoneCursor.getColumnIndex(Phone.DISPLAY_NAME));
				// Sim卡中没有联系人头像
				ContactVo contactVo = new ContactUtilLj.ContactVo();
				contactVo.setName(contactName);
				contactVo.setPhoneNumber(phoneNumber);
				mContactVos.add(contactVo);
			}
			phoneCursor.close();
		}
		return mContactVos;
	}

	public static class ContactVo {
		private String name;
		private String phoneNumber;
		private Bitmap bitmap;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public Bitmap getBitmap() {
			return bitmap;
		}

		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}

	}
}
