package com.goldmsg.gmhomepage.controller.response.shortcut;

import java.util.List;

import com.goldmsg.gmhomepage.jentity.ItemShortcut;

/***
 * 用户设置的快捷标签response
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午4:46:00
 */
public class UserSetShortcutResponse {

	private List<ItemShortcut> appShortcut;	//应用快捷标签
	
	private List<ItemShortcut> mgrShortcut;	//管理标签

	public List<ItemShortcut> getAppShortcut() {
		return appShortcut;
	}

	public void setAppShortcut(List<ItemShortcut> appShortcut) {
		this.appShortcut = appShortcut;
	}

	public List<ItemShortcut> getMgrShortcut() {
		return mgrShortcut;
	}

	public void setMgrShortcut(List<ItemShortcut> mgrShortcut) {
		this.mgrShortcut = mgrShortcut;
	}
}
