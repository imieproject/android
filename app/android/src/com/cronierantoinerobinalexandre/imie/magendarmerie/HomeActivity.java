/**************************************************************************
 * HomeActivity.java, magendarmerie Android
 *
 * Copyright 2014
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Feb 15, 2014
 *
 **************************************************************************/
package com.cronierantoinerobinalexandre.imie.magendarmerie;

import com.cronierantoinerobinalexandre.imie.magendarmerie.harmony.view.HarmonyFragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Home Activity.
 * This is from where you can access to your entities activities by default.
 * BEWARE : This class is regenerated with orm:generate:crud. Don't modify it.
 * @see android.app.Activity
 */
public class HomeActivity extends HarmonyFragmentActivity 
		implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState)	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		
		this.initButtons();
	}

	/**
	 * Initialize the buttons click listeners.
	 */
	private void initButtons() {
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			default:
				intent = null;
				break;
		}

		if (intent != null) {
			this.startActivity(intent);
		}
	}

}
