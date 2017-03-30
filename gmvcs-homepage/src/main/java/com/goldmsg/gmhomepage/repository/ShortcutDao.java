package com.goldmsg.gmhomepage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goldmsg.gmhomepage.jentity.ItemShortcut;

/***
 * 快捷标签dao
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午2:40:57
 */
public interface ShortcutDao extends PagingAndSortingRepository<ItemShortcut, Integer>, JpaSpecificationExecutor<ItemShortcut> {

	@Query("select t from ItemShortcut t, TbRelUserShortcut t2 where t.id = t2.id.itemId and t2.id.accountId = ?1")
	List<ItemShortcut> findUserSetShortcut(Integer accountId);
	
	@Query("select t from ItemShortcut t where t.id not in (select t2.id.itemId from TbRelUserShortcut t2 where t2.id.accountId = ?1)")
	List<ItemShortcut> findUserNotSetShortcut(Integer accountId);
}
