package com.goldmsg.gmhomepage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.goldmsg.gmhomepage.jentity.TbUserLoginInfo;
import com.goldmsg.gmhomepage.jentity.UserLoginInfo;

/**
 * @Description:登录信息DAO
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年2月15日下午5:58:58
 */
public interface LoginDao
		extends PagingAndSortingRepository<TbUserLoginInfo, Integer>, JpaSpecificationExecutor<TbUserLoginInfo> {

	/**
	 * @param accountIds
	 * @return
	 */
	@Query("select new com.goldmsg.gmhomepage.jentity.UserLoginInfo(t.userCode,max(t.lastLoginTime)) from TbUserLoginInfo t "
			+ "where t.userCode in ?1 group by t.userCode")
	List<UserLoginInfo> getLastLoginInfosByUserCodes(List<String> userCodes);

}
