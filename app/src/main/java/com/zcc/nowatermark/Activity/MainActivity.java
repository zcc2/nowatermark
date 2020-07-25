package com.zcc.nowatermark.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.pbrx.mylib.permission.RequestResult;
import com.zcc.nowatermark.Activity.home.VideoFragment;
import com.zcc.nowatermark.R;
import com.zcc.nowatermark.base.BaseActivity;

import java.lang.reflect.Field;

import butterknife.BindView;


public class MainActivity extends BaseActivity {


    @BindView(R.id.frameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
	private VideoFragment indexFragment;
	private VideoFragment myFragment;
	private Fragment curFragment;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    protected void findView() {
		navigation.setItemIconTintList(null);
		navigation.setSelectedItemId(R.id.navigation_index);
		disableShiftMode(navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
		indexFragment =new VideoFragment();
		myFragment=new VideoFragment();
		switchFragment(curFragment, indexFragment);
		requestPremission(new RequestResult() {
			@Override
			public void successResult() {

			}

			@Override
			public void failuerResult() {
				toastShort("权限拒绝无法登陆");
			}
		}, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void initView() {

    }
	@SuppressLint("RestrictedApi")
	public  void disableShiftMode(BottomNavigationView view) {
		BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
		try {
			Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
			shiftingMode.setAccessible(true);
			shiftingMode.setBoolean(menuView, false);
			shiftingMode.setAccessible(false);
			for (int i = 0; i < menuView.getChildCount(); i++) {
				BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
				//noinspection RestrictedApi
				item.setShiftingMode(false);
				// set once again checked value, so view will be updated
				//noinspection RestrictedApi
				item.setChecked(item.getItemData().isChecked());
			}
		} catch (NoSuchFieldException e) {
			Log.e("BNVHelper", "Unable to get shift mode field", e);
		} catch (IllegalAccessException e) {
			Log.e("BNVHelper", "Unable to change value of shift mode", e);
		}
	}
	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			resetToDefaultIcon();
			switch (item.getItemId()) {
				case R.id.navigation_index:
					item.setIcon(R.drawable.ic_ondemand_video_black_24dp);
					switchFragment(curFragment, indexFragment);
					return true;
				case R.id.navigation_my:
					item.setIcon(R.drawable.ic_person_outline_black_24dp);
					switchFragment(curFragment, myFragment);
					return true;
			}
			return false;
		}
	};

	private void resetToDefaultIcon() {
		MenuItem index = navigation.getMenu().findItem(R.id.navigation_index);
		MenuItem my = navigation.getMenu().findItem(R.id.navigation_my);
		index.setIcon(R.drawable.ic_ondemand_video_black_24dp);
		my.setIcon(R.drawable.ic_person_outline_black_24dp);
	}

	private void switchFragment(Fragment from, Fragment to) {
		if (null == from) {
			curFragment = to;
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.add(R.id.frameLayout, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
			}
		} else if (curFragment != to) {
			curFragment = to;
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (!to.isAdded()) {
				transaction.hide(from).add(R.id.frameLayout, to).commitAllowingStateLoss();
			} else {
				transaction.hide(from).show(to).commitAllowingStateLoss();
			}
		}
//        curFragment = fragment;
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

	}
}
