package com.goldmsg.gmhomepage.service;

import java.util.List;

import com.goldmsg.gmhomepage.jentity.TbRelUserShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcutPK;

/***
 * 用户快捷标签关联关系service
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月14日: 上午9:13:31
 */
public interface RelUserShortcutService {

	/***
	 * 添加关联关系
	 * @param rel	关联关系
	 * @return
	 */
	boolean save(TbRelUserShortcut rel);
	
	/***
	 * 删除关联关系
	 * @param pk	主键
	 */
	void delete(TbRelUserShortcutPK pk);
	
	/***
	 * 批量添加关联关系
	 * @param rels	关联关系列表
	 * @return 
	 */
	boolean batchSave(List<TbRelUserShortcut> rels);
	
	/***
	 * 批量删除关联关系
	 * @param pks	主键列表
	 */
	void batchDelete(List<TbRelUserShortcutPK> pks);
}
