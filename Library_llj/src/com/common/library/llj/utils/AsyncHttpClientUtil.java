package com.common.library.llj.utils;

/**
 * 网络请求的工具类
 * 
 * @author liulj
 * 
 */
public class AsyncHttpClientUtil {
//	private static AsyncHttpClient mAsyncHttpClient = null;
//	private static AsyncHttpClientUtil asyncHttpClientUtil = null;
//	private static String SYSTEM_IN_MAINTAIN = "系统维护中";
//	private static String NETWORK_INSTABILITY = "网络不稳定，请稍后再试试！";
//
//	/**
//	 *
//	 * @return
//	 */
//	public static AsyncHttpClientUtil get() {
//		if (asyncHttpClientUtil == null || mAsyncHttpClient == null) {
//			synchronized (AsyncHttpClientUtil.class) {
//				if (asyncHttpClientUtil == null) {
//					asyncHttpClientUtil = new AsyncHttpClientUtil();
//				}
//				if (mAsyncHttpClient == null) {
//					mAsyncHttpClient = new AsyncHttpClient();
//				}
//			}
//		}
//		return asyncHttpClientUtil;
//
//	}
//
//	public static AsyncHttpClient getAsyncHttpClient() {
//		return mAsyncHttpClient;
//	}
//
//	/**
//	 *
//	 * @param <T>
//	 * @param url
//	 * @param reponseClass
//	 * @param myResponseHandler
//	 */
//	public <T extends BaseReponse> void get(final Context context, String url, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
//		isNetworkAvailable(context, myResponseHandler);
//		mAsyncHttpClient.setTimeout(20000);
//		mAsyncHttpClient.get(context, url, myResponseHandler.setParams(myResponseHandler.getParams()), new TextHttpResponseHandler() {
//			@Override
//			public void onStart() {
//				super.onStart();
//				LogUtilLj.LLJi("url:" + getRequestURI());
//				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
//				if (myResponseHandler != null) {
//					myResponseHandler.onStart();
//				}
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, String responseString) {
//				LogUtilLj.LLJi("onSuccess:" + responseString);
//				LogUtilLj.LLJi("==================================================");
//				if (responseString != null) {
//					T baseReponse = null;
//					try {
//						baseReponse = new Gson().fromJson(responseString, reponseClass);
//					} catch (JsonSyntaxException e) {
//						ToastUtilLj.show(context, SYSTEM_IN_MAINTAIN);
//					}
//					if (baseReponse != null) {
//						switch (baseReponse.getStatus()) {
//						case 1:
//							if (myResponseHandler != null) {
//								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
//							}
//							break;
//						default:
//							if (myResponseHandler != null) {
//								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
//							}
//
//							break;
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				if (myResponseHandler != null) {
//					myResponseHandler.onFinish();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				LogUtilLj.LLJi("onFailure:" + responseString);
//				LogUtilLj.LLJi(throwable);
//				ToastUtilLj.show(context, NETWORK_INSTABILITY);
//				if (myResponseHandler != null) {
//					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
//				}
//			}
//		});
//	}
//
//	/**
//	 *
//	 * @param <T>
//	 * @param url
//	 * @param reponseClass
//	 * @param myResponseHandler
//	 */
//	public <T extends BaseReponse> void post(final Context context, String url, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
//		isNetworkAvailable(context, myResponseHandler);
//		mAsyncHttpClient.setTimeout(20000);
//		mAsyncHttpClient.post(context, url, myResponseHandler.setParams(myResponseHandler.getParams()), new TextHttpResponseHandler() {
//			@Override
//			public void onStart() {
//				LogUtilLj.LLJi("url:" + getRequestURI());
//				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
//				super.onStart();
//				if (myResponseHandler != null) {
//					myResponseHandler.onStart();
//				}
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, String responseString) {
//				LogUtilLj.LLJi("onSuccess:" + responseString);
//				LogUtilLj.LLJi("==================================================");
//				if (responseString != null) {
//					T baseReponse = null;
//					try {
//						baseReponse = new Gson().fromJson(responseString, reponseClass);
//					} catch (JsonSyntaxException e) {
//						ToastUtilLj.show(context, SYSTEM_IN_MAINTAIN);
//					}
//					if (baseReponse != null) {
//						switch (baseReponse.getStatus()) {
//						case 1:
//							if (myResponseHandler != null) {
//								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
//							}
//							break;
//
//						default:
//
//							if (myResponseHandler != null) {
//								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
//							}
//							break;
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				if (myResponseHandler != null) {
//					myResponseHandler.onFinish();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				LogUtilLj.LLJi("onFailure:" + responseString);
//				LogUtilLj.LLJi(throwable);
//				ToastUtilLj.show(context, NETWORK_INSTABILITY);
//				if (myResponseHandler != null) {
//					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
//				}
//			}
//		});
//	}
//
//	/**
//	 * 用来传json字符串的StringEntity用
//	 *
//	 * @param context
//	 * @param url
//	 * @param entity
//	 * @param contentType
//	 * @param reponseClass
//	 * @param myResponseHandler
//	 */
//	public <T extends BaseReponse> void post(final Context context, String url, HttpEntity entity, String contentType, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
//		isNetworkAvailable(context, myResponseHandler);
//		mAsyncHttpClient.setTimeout(20000);
//		mAsyncHttpClient.post(context, url, entity, contentType, new TextHttpResponseHandler() {
//			@Override
//			public void onStart() {
//				LogUtilLj.LLJi("url:" + getRequestURI());
//				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
//				super.onStart();
//				if (myResponseHandler != null) {
//					myResponseHandler.onStart();
//				}
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, String responseString) {
//				LogUtilLj.LLJi("onSuccess:" + responseString);
//				LogUtilLj.LLJi("==================================================");
//				if (responseString != null) {
//					T baseReponse = null;
//					try {
//						baseReponse = new Gson().fromJson(responseString, reponseClass);
//					} catch (JsonSyntaxException e) {
//						ToastUtilLj.show(context, SYSTEM_IN_MAINTAIN);
//					}
//					if (baseReponse != null) {
//						switch (baseReponse.getStatus()) {
//						case 1:
//							if (myResponseHandler != null) {
//								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
//							}
//							break;
//
//						default:
//
//							if (myResponseHandler != null) {
//								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
//							}
//							break;
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				if (myResponseHandler != null) {
//					myResponseHandler.onFinish();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				LogUtilLj.LLJi("onFailure:" + responseString);
//				LogUtilLj.LLJi(throwable);
//				ToastUtilLj.show(context, NETWORK_INSTABILITY);
//				if (myResponseHandler != null) {
//					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
//				}
//			}
//		});
//	}
//
//	/**
//	 *
//	 * @param <T>
//	 * @param url
//	 * @param reponseClass
//	 * @param myResponseHandler
//	 */
//	public <T extends BaseReponse> void delete(final Context context, String url, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
//		isNetworkAvailable(context, myResponseHandler);
//		mAsyncHttpClient.setTimeout(20000);
//		mAsyncHttpClient.delete(context, url, new TextHttpResponseHandler() {
//			@Override
//			public void onStart() {
//				LogUtilLj.LLJi("url:" + getRequestURI());
//				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
//				super.onStart();
//				if (myResponseHandler != null) {
//					myResponseHandler.onStart();
//				}
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, String responseString) {
//				LogUtilLj.LLJi("onSuccess:" + responseString);
//				LogUtilLj.LLJi("==================================================");
//				if (responseString != null) {
//					T baseReponse = null;
//					try {
//						baseReponse = new Gson().fromJson(responseString, reponseClass);
//					} catch (JsonSyntaxException e) {
//						ToastUtilLj.show(context, SYSTEM_IN_MAINTAIN);
//					}
//					if (baseReponse != null) {
//						switch (baseReponse.getStatus()) {
//						case 1:
//							if (myResponseHandler != null) {
//								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
//							}
//							break;
//
//						default:
//
//							if (myResponseHandler != null) {
//								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
//							}
//							break;
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				if (myResponseHandler != null) {
//					myResponseHandler.onFinish();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				LogUtilLj.LLJi("onFailure:" + responseString);
//				LogUtilLj.LLJi(throwable);
//				ToastUtilLj.show(context, NETWORK_INSTABILITY);
//				if (myResponseHandler != null) {
//					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
//				}
//			}
//		});
//	}
//
//	/**
//	 *
//	 * @param <T>
//	 * @param url
//	 * @param reponseClass
//	 * @param myResponseHandler
//	 */
//	public <T extends BaseReponse> void put(final Context context, String url, final Class<T> reponseClass, final MyResponseHandler<T> myResponseHandler) {
//		isNetworkAvailable(context, myResponseHandler);
//		mAsyncHttpClient.setTimeout(20000);
//		mAsyncHttpClient.put(context, url, myResponseHandler.setParams(myResponseHandler.getParams()), new TextHttpResponseHandler() {
//			@Override
//			public void onStart() {
//				LogUtilLj.LLJi("url:" + getRequestURI());
//				LogUtilLj.LLJi("Params:" + myResponseHandler.getParams().toString());
//				super.onStart();
//				if (myResponseHandler != null) {
//					myResponseHandler.onStart();
//				}
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers, String responseString) {
//				LogUtilLj.LLJi("onSuccess:" + responseString);
//				LogUtilLj.LLJi("==================================================");
//				if (responseString != null) {
//					T baseReponse = null;
//					try {
//						baseReponse = new Gson().fromJson(responseString, reponseClass);
//					} catch (JsonSyntaxException e) {
//						ToastUtilLj.show(context, SYSTEM_IN_MAINTAIN);
//					}
//					if (baseReponse != null) {
//						switch (baseReponse.getStatus()) {
//						case 1:
//							if (myResponseHandler != null) {
//								myResponseHandler.onSuccess(statusCode, headers, baseReponse);
//							}
//							break;
//
//						default:
//							if (myResponseHandler != null) {
//								myResponseHandler.onNotSuccess(context, statusCode, headers, baseReponse);
//							}
//							break;
//						}
//					}
//				}
//			}
//
//			@Override
//			public void onFinish() {
//				super.onFinish();
//				if (myResponseHandler != null) {
//					myResponseHandler.onFinish();
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//				LogUtilLj.LLJi("onFailure:" + responseString);
//				LogUtilLj.LLJi(throwable);
//				ToastUtilLj.show(context, NETWORK_INSTABILITY);
//				if (myResponseHandler != null) {
//					myResponseHandler.onFailure(statusCode, headers, responseString, throwable);
//				}
//			}
//		});
//	}
//
//	/**
//	 * 判断网络是否可以使用，不可以就回调
//	 *
//	 * @param context
//	 * @param myResponseHandler
//	 * @return
//	 */
//	private <T extends BaseReponse> boolean isNetworkAvailable(Context context, MyResponseHandler<T> myResponseHandler) {
//		if (!NetWorkUtilLj.isNetworkAvailable(context)) {
//			myResponseHandler.networkUnAvailable(context);
//			return false;
//		} else {
//			return true;
//		}
//	}
//
//	/**
//	 *
//	 * @param context
//	 * @param mayInterruptIfRunning
//	 */
//	public void cancelRequests(final Context context, final boolean mayInterruptIfRunning) {
//		mAsyncHttpClient.cancelRequests(context, mayInterruptIfRunning);
//	}
//
//	/**
//	 *
//	 * @param mayInterruptIfRunning
//	 */
//	public void cancelAllRequests(boolean mayInterruptIfRunning) {
//		mAsyncHttpClient.cancelAllRequests(mayInterruptIfRunning);
//	}

}
