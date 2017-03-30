package com.goldmsg.gmhomepage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldmsg.gmhomepage.jentity.ItemShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcutPK;
import com.goldmsg.gmhomepage.repository.RelUserShortcutDao;
import com.goldmsg.gmhomepage.repository.ShortcutDao;
import com.goldmsg.gmhomepage.service.ShortcutService;

/***
 * 快捷标签操作service
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午3:46:14
 */
@Service("shortcutService")
public class ShortcutServiceImpl implements ShortcutService {

	@Autowired
	ShortcutDao shortcutDao;
	
	@Autowired
	RelUserShortcutDao relUserShortcutDao;
	
	@Override
	public List<ItemShortcut> findSetByAccountId(Integer accountId) {
		return shortcutDao.findUserSetShortcut(accountId);
	}

	@Override
	public List<ItemShortcut> findUnSetByAccountId(Integer accountId) {
		return shortcutDao.findUserNotSetShortcut(accountId);
	}

	@Override
	public void removeShortcut(Integer accountId, Integer id) {
		TbRelUserShortcutPK pk = new TbRelUserShortcutPK();
		pk.setAccountId(accountId);
		pk.setItemId(id);
		
		//relUserShortcutDao.delete(pk);
	}

	@Override
	public void addShortcut(Integer accountId, Integer id) {
		TbRelUserShortcutPK pk = new TbRelUserShortcutPK();
		pk.setAccountId(accountId);
		pk.setItemId(id);
		
		TbRelUserShortcut shortcut = new TbRelUserShortcut();
		shortcut.setId(pk);
		shortcut.setUpdateTime(new Date());
		
//		relUserShortcutDao.save(shortcut);
	}

}
