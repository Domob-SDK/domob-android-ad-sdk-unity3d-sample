package cn.domob.ads.sample;

import cn.domob.android.ads.AdEventListener;
import cn.domob.android.ads.AdView;
import cn.domob.android.ads.InterstitialAd;
import cn.domob.android.ads.InterstitialAdListener;
import cn.domob.android.ads.AdManager.ErrorCode;

import com.unity3d.player.UnityPlayerActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends UnityPlayerActivity {

	public static final String PUBLISHER_ID = "56OJyM1ouMGoaSnvCK";
	public static final String InlinePPID = "16TLwebvAchksY6iO_8oSb-i";
	public static final String InterstitialPPID = "16TLwebvAchksY6iOa7F4DXs";

	private RelativeLayout bannerLayout;
	private AdView adView;
	private InterstitialAd mInterstitialAd;
	private Context context;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		bannerLayout = new RelativeLayout(context);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:// showBanner
					if (bannerLayout.getChildCount() == 0) {
						adView = new AdView(MainActivity.this, PUBLISHER_ID, InlinePPID);
						// set banner size
						// adView.setAdSize(AdView.INLINE_SIZE_320X50);
						// set banner is'not auto refresh
						// adView.setRefreshable(false)
						adView.setKeyword("game");
						adView.setUserGender("male");
						adView.setUserBirthdayStr("2000-08-08");
						adView.setUserPostcode("123456");
						adView.setAdEventListener(new AdEventListener() {
							@Override
							public void onAdOverlayPresented(AdView adView) {
								Log.i("DomobSDKUnity3dDemo", "overlayPresented");
							}

							@Override
							public void onAdOverlayDismissed(AdView adView) {
								Log.i("DomobSDKUnity3dDemo", "Overrided be dismissed");
							}

							@Override
							public void onAdClicked(AdView adView) {
								Log.i("DomobSDKUnity3dDemo", "onDomobAdClicked");
							}

							@Override
							public void onLeaveApplication(AdView adView) {
								Log.i("DomobSDKUnity3dDemo", "onDomobLeaveApplication");
							}

							@Override
							public Context onAdRequiresCurrentContext() {
								return null;
							}

							@Override
							public void onAdFailed(AdView adView, ErrorCode errorCode) {
								Log.i("DomobSDKUnity3dDemo", "onDomobAdFailed");
							}

							@Override
							public void onEventAdReturned(AdView adView) {
								Log.i("DomobSDKUnity3dDemo", "onDomobAdReturned");
							}
						});
						RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
								RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
						layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
						layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
						bannerLayout.addView(adView, layoutParams);
						addContentView(bannerLayout, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
					} else {
						if (adView != null) {
							adView.setVisibility(View.VISIBLE);
							adView.requestRefreshAd();
						}
					}
					break;
				case 1:
					if (adView != null) {
						adView.setVisibility(View.GONE);
					}
					break;
				case 2:
					if (mInterstitialAd != null) {
						return;
					}
					mInterstitialAd = new InterstitialAd(MainActivity.this, PUBLISHER_ID, InterstitialPPID);

					mInterstitialAd.setInterstitialAdListener(new InterstitialAdListener() {
						@Override
						public void onInterstitialAdReady() {
							Log.i("DomobSDKUnity3dDemo", "onAdReady");
							Toast.makeText(getApplicationContext(), "InterstitialAd init complete", Toast.LENGTH_SHORT)
									.show();
						}

						@Override
						public void onLandingPageOpen() {
							Log.i("DomobSDKUnity3dDemo", "onLandingPageOpen");
						}

						@Override
						public void onLandingPageClose() {
							Log.i("DomobSDKUnity3dDemo", "onLandingPageClose");
						}

						@Override
						public void onInterstitialAdPresent() {
							Log.i("DomobSDKUnity3dDemo", "onInterstitialAdPresent");
						}

						@Override
						public void onInterstitialAdDismiss() {
							// Request new ad when the previous interstitial ad
							// was closed.
							mInterstitialAd.loadInterstitialAd();
							Log.i("DomobSDKUnity3dDemo", "onInterstitialAdDismiss");
						}

						@Override
						public void onInterstitialAdFailed(ErrorCode errorCode) {
							Log.i("DomobSDKUnity3dDemo", "onInterstitialAdFailed");
						}

						@Override
						public void onInterstitialAdLeaveApplication() {
							Log.i("DomobSDKUnity3dDemo", "onInterstitialAdLeaveApplication");

						}

						@Override
						public void onInterstitialAdClicked(InterstitialAd interstitialAd) {
							Log.i("DomobSDKUnity3dDemo", "onInterstitialAdClicked");
						}
					});
					mInterstitialAd.loadInterstitialAd();
					break;
				case 3:
					if (mInterstitialAd != null) {
						if (mInterstitialAd.isInterstitialAdReady()) {
							mInterstitialAd.showInterstitialAd(MainActivity.this);
						} else {
							Log.i("DomobSDKUnity3dDemo", "Interstitial Ad is not ready");
							mInterstitialAd.loadInterstitialAd();
						}
					} else {
						Log.i("DomobSDKUnity3dDemo", "Interstitial Ad is not init");
					}
					break;
				default:
					break;
				}
			}
		};
	}

	public void showBanner() {
		handler.sendEmptyMessage(0);
	}

	public void hideBanner() {
		handler.sendEmptyMessage(1);
	}

	public void initInterstitial() {
		handler.sendEmptyMessage(2);
	}

	public void showInterstitial() {
		handler.sendEmptyMessage(3);
	}
}
