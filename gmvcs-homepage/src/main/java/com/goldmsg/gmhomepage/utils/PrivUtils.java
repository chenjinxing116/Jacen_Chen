package com.goldmsg.gmhomepage.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.goldmsg.gmhomepage.system.ProjectProperties;
import com.gosun.service.entity.UserRsp;
import com.gosun.service.privilege.IPrivilegeService;
import com.gosun.service.user.IUserService;

/***
 * 权限信息相关查询工具类
 * 
 * @author QH Email: qhs_dream@163.com 2016年10月8日 : 下午3:35:13
 */
@Repository
public class PrivUtils {

	protected static ProjectProperties props;

	protected static IUserService userService;

	protected static IPrivilegeService privilegeService;

	public static List<String> getPrivilegeListByAccountName(String accountName) {
		UserRsp userRsp = userService.getUserInfoByAccountName(accountName);
		if (userRsp == null) {
			// 用户不存在，返回空的权限信息列表
			return new ArrayList<>(0);
		}
		Map<String, String> privMap = privilegeService.getPrivilegesByAccountId(userRsp.getAccountId());

		List<String> privStr = new ArrayList<String>() {
		};

		for (String str : privMap.values()) {
			for (String prs : str.split(",")) {

				if (!privStr.contains(prs)) {
					privStr.add(prs);
				}
			}
		}

		return privStr;
	}

	public static ProjectProperties getProps() {
		return props;
	}

	public static void setProps(ProjectProperties props) {
		PrivUtils.props = props;
	}

	public static IUserService getUserService() {
		return userService;
	}

	public static void setUserService(IUserService userService) {
		PrivUtils.userService = userService;
	}

	public static IPrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public static void setPrivilegeService(IPrivilegeService privilegeService) {
		PrivUtils.privilegeService = privilegeService;
	}
}
