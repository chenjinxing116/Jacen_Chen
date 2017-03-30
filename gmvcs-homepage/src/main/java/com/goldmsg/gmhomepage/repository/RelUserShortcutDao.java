package com.goldmsg.gmhomepage.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goldmsg.gmhomepage.jentity.TbRelUserShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcutPK;

/***
 * 用户快捷标签dao类
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午4:37:07
 */
public interface RelUserShortcutDao extends PagingAndSortingRepository<TbRelUserShortcut, TbRelUserShortcutPK>, JpaSpecificationExecutor<TbRelUserShortcut> {

}
