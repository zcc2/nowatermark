package com.pbrx.mylib.reflection;

import android.util.Log;

import com.pbrx.mylib.constant.LibConstant;
import com.pbrx.mylib.util.LogUtil;
import com.pbrx.mylib.util.SPUtils;
import com.pbrx.mylib.util.UserInfoBean;
import java.util.List;
import java.util.TreeMap;

public class TOUtils {
	public static TreeMap<String, String> toParamMap(final Object obj) throws NullOauthTokenException {
		try {
			final RefObject refObj = RefObject.wrap(obj);
			// 自动注入当前的token
			try {
				if (refObj.contains("empId")) {
					final RefObject token = refObj.get("empId");
					final String tokenValue = (String) token.unwrap();

					if (tokenValue == null) {
//						final UserInfoBean userInfoBean = SPUtils.getInstance().getUserInfoBean();
////						final String curToken = (userInfoBean == null) ? null : userInfoBean.getOauth_token();
//						final Integer user_id = (userInfoBean == null) ? null : userInfoBean.getEmployee().getId();
						String user_id= (String) SPUtils.get(LibConstant.user_id,"");
						if (user_id == null) {
							// throw new NullOauthTokenException();
						} else {
							refObj.set("empId", user_id+"");
//							LogUtil.e("user_id====",user_id);
						}
					}
				}
			} catch (RefException e) {
				e.printStackTrace();
			}
			final List<RefObject> list = refObj.getAll();

			final TreeMap<String, String> map = new TreeMap<String, String>();
			for (RefObject refChild : list) {
				final Object value = (Object) refChild.unwrap();
				if(value instanceof String){
					if (value != null) {
						map.put(refChild.getName(), (String) value);
					}
				}

			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static final class NullOauthTokenException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -428424627255664689L;

		public NullOauthTokenException() {
			super();
		}

		public NullOauthTokenException(String msg) {
			super(msg);
		}

		public NullOauthTokenException(Throwable throwable) {
			super(throwable);
		}

		public NullOauthTokenException(String msg, Throwable throwable) {
			super(msg, throwable);
		}
	}

}
