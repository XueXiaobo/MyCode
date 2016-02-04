package com.xc.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebUtil {

	public static void wrapWebView(WebView webView, final ProgressBar progress) {
		wrapWebView(null, webView, progress);
	}

	public static void wrapWebView(Context context, WebView webView, final ProgressBar progress) {
		wrapWebView(context, webView, new BaseWebViewClient(progress));
	}

	// 封装WebView
	public static void wrapWebView(Context context, WebView webView, WebViewClient webclient) {
		WebSettings webSettings = webView.getSettings();
		webSettings.setSavePassword(false);
		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setSupportZoom(false);

		// 设置定位可用
		if (context != null) {
			webSettings.setDatabaseEnabled(true);
			String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();

			// 启用地理定位
			webSettings.setGeolocationEnabled(true);
			// 设置定位的数据库路径
			webSettings.setGeolocationDatabasePath(dir);

			// 最重要的方法，一定要设置，这就是出不来的主要原因

			webSettings.setDomStorageEnabled(true);
		}
		//
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}
		});
		webView.setWebViewClient(webclient);
		// webView.setWebViewClient(new WebViewClient() {
		//
		// // 加载链接
		// @Override
		// public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// view.loadUrl(url);
		// return true;
		// }
		//
		// // 链接开始加载时
		// @Override
		// public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// super.onPageStarted(view, url, favicon);
		// if (progress != null) {
		// progress.setVisibility(View.VISIBLE);
		// }
		// }
		//
		// // 链接加载完成
		// @Override
		// public void onPageFinished(WebView view, String url) {
		// super.onPageFinished(view, url);
		// if (progress != null) {
		// progress.setVisibility(View.GONE);
		// }
		// }
		//
		// @Override
		// public void onReceivedSslError(WebView view, SslErrorHandler handler,
		// SslError error) {
		// super.onReceivedSslError(view, handler, error);
		// }
		//
		// // webview无网络情况下的人性化处理
		// @Override
		// public void onReceivedError(WebView view, int errorCode, String
		// description, String failingUrl) {
		// super.onReceivedError(view, errorCode, description, failingUrl);
		// view.loadData("", "text/html", "utf-8");
		// Toast.makeText(view.getContext(), "网络不给力",
		// android.widget.Toast.LENGTH_LONG).show();
		// }
		// });
	}

	public static class BaseWebViewClient extends WebViewClient {
		private ProgressBar progress;

		public BaseWebViewClient(ProgressBar progress) {
			this.progress = progress;
		}

		// 加载链接
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		// 链接开始加载时
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			if (progress != null) {
				progress.setVisibility(View.VISIBLE);
			}
		}

		// 链接加载完成
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if (progress != null) {
				progress.setVisibility(View.GONE);
			}
		}

		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			super.onReceivedSslError(view, handler, error);
		}

		// webview无网络情况下的人性化处理
		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			view.loadData("", "text/html", "utf-8");
			Toast.makeText(view.getContext(), "网络不给力", android.widget.Toast.LENGTH_LONG).show();
		}

	}

}
