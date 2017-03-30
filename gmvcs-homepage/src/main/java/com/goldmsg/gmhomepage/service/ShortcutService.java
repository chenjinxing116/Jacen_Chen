package com.goldmsg.gmhomepage.service;

import java.util.List;

import com.goldmsg.gmhomepage.jentity.ItemShortcut;

/***
 * 快捷标签service
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午2:42:37
 */
public interface ShortcutService {

	/***
	 * 根据用户id获取用户设置的快捷标签列表
	 * @param accountId	用户id
	 * @return	标签列表
	 */
	List<ItemShortcut> findSetByAccountId(Integer accountId);
	
	/***
	 * 根据用户id获取用户没有设置的快捷标签列表
	 * @return	标签列表
	 */
	List<ItemShortcut> findUnSetByAccountId(Integer accountId);
	
	/***
	 * 移除用户设置的快捷标签
	 * @param accountId	用户id
	 * @param id		标签id
	 */
	void removeShortcut(Integer accountId, Integer id);
	
	/***
	 * 设置一个快捷标签
	 * @param accountId	用户id
	 * @param id		标签id
	 */
	void addShortcut(Integer accountId, Integer id);
}
