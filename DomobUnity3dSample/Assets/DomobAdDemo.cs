using UnityEngine;
using System.Collections;

public class DomobAdDemo : MonoBehaviour {

	private AndroidJavaClass jc;
	private AndroidJavaObject jo;

	// Use this for initialization
	void Start () {
		jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		jo = jc.GetStatic<AndroidJavaObject>("currentActivity");
	}

	void OnGUI()
	{
		int x = 15;
		int y = 5;
		int pad = 20;
		int h = 100;
		int width = 300;
		y += h + pad+10+20;
		y += h + pad;
		GUI.skin.button.fontSize = 25;
		if (GUI.Button(new Rect(x, y, width, h), "showBanner"))
		{
			jo.Call("showBanner");
		}
		y += h + pad;
		if (GUI.Button(new Rect(x, y, width, h), "hideBanner"))
		{
			jo.Call("hideBanner");
		}
		y += h + pad;
		if (GUI.Button(new Rect(x, y, width, h), "initInterstitial"))
		{
			jo.Call("initInterstitial");
		}
		y += h + pad;
		if (GUI.Button(new Rect(x, y, width, h), "showInterstitial")) {
			jo.Call("showInterstitial");
		}
	}
	
	// Update is called once per frame
	void Update () {

	}
}
