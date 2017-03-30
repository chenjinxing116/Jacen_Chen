package com.goldmsg.gmhomepage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldmsg.gmhomepage.jentity.TbRelUserShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcutPK;
import com.goldmsg.gmhomepage.repository.RelUserShortcutDao;
import com.goldmsg.gmhomepage.service.RelUserShortcutService;

/***
 * 用户快捷标签关联关系service
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月14日: 上午9:17:06
 */
@Service("relUserShortcutService")
public class RelUserShortcutServiceImpl implements RelUserShortcutService {

	@Autowired
	RelUserShortcutDao relUserShortcutDao;
	
	@Override
	public boolean save(TbRelUserShortcut rel) {
		TbRelUserShortcut ret = relUserShortcutDao.save(rel);
		
		return ret == null ? false : true;
	}

	@Override
	public void delete(TbRelUserShortcutPK pk) {
		relUserShortcutDao.delete(pk);
	}

	@Override
	public boolean batchSave(List<TbRelUserShortcut> rels) {
		Iterable<TbRelUserShortcut> ret = relUserShortcutDao.save(rels);
		
		return ret == null ? false : true;
	}

	@Transactional
	@Override
	public void batchDelete(List<TbRelUserShortcutPK> pks) {
		for (TbRelUserShortcutPK pk : pks) {
			relUserShortcutDao.delete(pk);
		}
	}

}
