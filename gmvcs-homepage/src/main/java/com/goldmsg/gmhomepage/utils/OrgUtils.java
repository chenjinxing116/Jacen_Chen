package com.goldmsg.gmhomepage.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gosun.service.entity.OrgRsp;
import com.gosun.service.org.IOrgService;

/***
 * 部门操作相关处理工具类
 * 
 * @author QH Email: qhs_dream@163.com 2016年9月29日 : 下午3:31:39
 */
@Repository
public class OrgUtils {

	@Autowired
	protected static IOrgService orgService;

	/***
	 * 递归获取只有指定级别的所有部门编号列表（不包括指定级别下的部门id）
	 * 
	 * @param orgId
	 *            当前部门id
	 * @param level
	 *            当前部门下指定级别
	 * @return 只是指定级别的部门id列表
	 */
	public static List<Integer> getOnlyLevelOrgIds(Integer orgId, int level) {
		List<Integer> ids = null;

		OrgRsp orgRsp = orgService.getOrgAndChildByOrgId(orgId.intValue());

		if (orgRsp != null) {
			ids = new ArrayList<Integer>();

			if (level == 0) {
				ids.add(orgId);
			} else {
				List<Integer> results = getLevelOrgIdsInternal(orgRsp, level);
				ids = results;
			}
		}

		return ids;
	}

	/***
	 * 递归获取指定级别的所有部门编号列表
	 * 
	 * @param orgId
	 *            根部门id
	 * @param level
	 *            级别，本级使用0，1级使用1，如此往下
	 * @return 部门编号列表
	 */
	public static List<String> getLevelOrgCodes(Integer orgId, int level) {
		List<String> codes = null;

		OrgRsp orgRsp = orgService.getOrgAndChildByOrgId(orgId.intValue());
			
		if (orgRsp != null) {
			codes = new ArrayList<String>();

			if (level == 0) {
				codes.add(orgRsp.getOrgCode());
			} else {
				List<String> results = getLevelOrgCodesInternal(orgRsp, level);
				codes = results;
			}
		}

		return codes;
	}

	/***
	 * 获取部门下所有级别的部门编号列表
	 * 
	 * @param orgId
	 *            根部门id
	 * @return 部门编号列表
	 */
	public static List<String> getOrgCodes(Integer orgId) {
		List<String> codes = null;
		OrgRsp orgRsp = orgService.getOrgAndChildByOrgId(orgId.intValue());

		if (orgRsp != null) {
			codes = getOrgCodesInternal(orgRsp);
		}

		return codes;
	}

	/***
	 * 递归获取指定级别的所有部门id列表
	 * 
	 * @param orgId
	 *            根部门id
	 * @param level
	 *            级别，本级使用0，1级使用1，如此往下
	 * @return 部门编号列表
	 */
	public static List<Integer> getLevelOrgIds(Integer orgId, int level) {
		List<Integer> ids = null;

		OrgRsp orgRsp = orgService.getOrgAndChildByOrgId(orgId.intValue());

		if (orgRsp != null) {
			ids = new ArrayList<Integer>();

			if (level == 0) {
				ids.add(orgRsp.getId());
			} else {
				List<Integer> results = getLevelOrgIdsInternal(orgRsp, level);
				ids = results;
			}
		}

		return ids;
	}

	/***
	 * 获取部门下所有级别的部门id列表
	 * 
	 * @param orgId
	 *            根部门id
	 * @return 部门编号列表
	 */
	public static List<Integer> getOrgIds(Integer orgId) {
		List<Integer> ids = null;
		OrgRsp orgRsp = orgService.getOrgAndChildByOrgId(orgId.intValue());

		if (orgRsp != null) {
			ids = getOrgIdsInternal(orgRsp);
		}

		return ids;
	}

	public static List<Integer> getOrgIdsInternal(OrgRsp orgRsp) {
		List<Integer> ids = new ArrayList<Integer>();

		if (orgRsp.getChildren() != null && orgRsp.getChildren().size() > 0) {
			for (OrgRsp e : orgRsp.getChildren()) {
				List<Integer> results = getOrgIdsInternal(e);
				ids.addAll(results);
			}
		}
		ids.add(orgRsp.getId());

		return ids;
	}

	private static List<Integer> getOnlyLevelOrgIdsInternal(OrgRsp orgRsp, int level) {
		List<Integer> ids = new ArrayList<Integer>();

		if (level == 0) {
			ids.add(orgRsp.getId());
		} else {
			if (orgRsp.getChildren() != null && orgRsp.getChildren().size() > 0) {
				// 添加子级部门编号
				for (OrgRsp e : orgRsp.getChildren()) {
					List<Integer> results = getLevelOrgIdsInternal(e, level - 1);
					ids.addAll(results);
				}
			}
			// 添加本级部门编号
			ids.add(orgRsp.getId());
		}

		return ids;
	}

	private static List<Integer> getLevelOrgIdsInternal(OrgRsp orgRsp, int level) {
		List<Integer> ids = new ArrayList<Integer>();

		if (level == 0) {
			ids.add(orgRsp.getId());
		} else {
			if (orgRsp.getChildren() != null && orgRsp.getChildren().size() > 0) {
				// 添加子级部门编号
				for (OrgRsp e : orgRsp.getChildren()) {
					ids.addAll(getLevelOrgIdsInternal(e, level - 1));
				}
			}
		}

		return ids;
	}

	public static List<String> getOrgCodesInternal(OrgRsp orgRsp) {
		List<String> codes = new ArrayList<String>();

		if (orgRsp.getChildren() != null && orgRsp.getChildren().size() > 0) {
			for (OrgRsp e : orgRsp.getChildren()) {
				List<String> results = getOrgCodesInternal(e);
				codes.addAll(results);
			}
		}
		codes.add(orgRsp.getOrgCode());

		return codes;
	}

	private static List<String> getLevelOrgCodesInternal(OrgRsp orgRsp, int level) {
		List<String> codes = new ArrayList<String>();

		if (level == 0) {
			codes.add(orgRsp.getOrgCode());
		} else {
			if (orgRsp.getChildren() != null && orgRsp.getChildren().size() > 0) {
				// 添加子级部门编号
				for (OrgRsp e : orgRsp.getChildren()) {
					List<String> results = getLevelOrgCodesInternal(e, level - 1);
					codes.addAll(results);
				}
			}
			// 添加本级部门编号
			codes.add(orgRsp.getOrgCode());
		}

		return codes;
	}

	public static IOrgService getOrgService() {
		return orgService;
	}

	public static void setOrgService(IOrgService orgService) {
		OrgUtils.orgService = orgService;
	}

}
