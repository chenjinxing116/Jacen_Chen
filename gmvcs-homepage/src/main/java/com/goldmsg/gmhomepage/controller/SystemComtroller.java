package com.goldmsg.gmhomepage.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldmsg.core.utils.HTTPParam;
import com.goldmsg.core.utils.JsonUtil;
import com.goldmsg.core.utils.ReturnInfo;
import com.goldmsg.data.service.UserOrgPrivilegesService;
import com.goldmsg.gmhomepage.controller.request.OrgCountRequest;
import com.goldmsg.gmhomepage.controller.response.BaseResponse;
import com.goldmsg.gmhomepage.controller.response.system.BaseUserInfoResponse;
import com.goldmsg.gmhomepage.controller.response.system.OrgInfoResponse;
import com.goldmsg.gmhomepage.controller.response.system.OrgOnlineResponse;
import com.goldmsg.gmhomepage.controller.response.system.OrgTreeResponse;
import com.goldmsg.gmhomepage.controller.response.system.OrgUSerStateResponse;
import com.goldmsg.gmhomepage.controller.response.system.OrgUserInfoResponse;
import com.goldmsg.gmhomepage.controller.response.system.UserInfoResponse;
import com.goldmsg.gmhomepage.controller.response.system.UserStatusResponse;
import com.goldmsg.gmhomepage.service.LicenseService;
import com.goldmsg.gmhomepage.service.LoginService;
import com.goldmsg.gmhomepage.service.RTCUserService;
import com.goldmsg.gmhomepage.service.RemoteCallService;
import com.goldmsg.gmhomepage.system.ApplicationProperties;
import com.goldmsg.gmhomepage.utils.OrgUtils;
import com.goldmsg.gmhomepage.utils.SysLogUtils;
import com.goldmsg.res.entity.vo.DSJ;
import com.goldmsg.res.entity.vo.DSJStatus;
import com.goldmsg.res.params.DSJServiceParams.GetDSJParams;
import com.goldmsg.res.service.DSJService;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.gosun.service.entity.OrgRsp;
import com.gosun.service.entity.OrgUserNodeRsp;
import com.gosun.service.entity.UserRsp;
import com.gosun.service.org.IOrgService;
import com.gosun.service.user.IUserService;

import net.sf.json.JSONArray;

/***
 * 全局通用操作controller
 * 
 * @author QH Email: qhs_dream@163.com 2016年10月8日 : 上午11:41:13
 */
@Controller
@RequestMapping("/system")
public class SystemComtroller {

	private static final Logger LOG = LoggerFactory.getLogger(SystemComtroller.class);

	@Autowired
	ApplicationProperties qpApplicationProperties;

	@Autowired
	RemoteCallService remoteCallService;

	@Autowired
	IUserService iUserService;

	@Autowired
	HttpSession session;

	@Autowired
	RTCUserService userService;

	@Autowired
	UserOrgPrivilegesService userPrivService;

	@Autowired
	IOrgService iOrgService;

	@Autowired
	LoginService loginService;
	/**
	 * @return
	 */
	@Autowired
	DSJService dsjService;

	@Autowired
	LicenseService licenseService;

	/**
	 * 导航页面
	 * 
	 * @return 返回homepage页面，如果失败，返回404页面
	 */
	@RequestMapping(value = "/index.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String index() {
		return "homePage/homepage";
	}

	/***
	 * 获取当前登录用户的基本信息
	 * 
	 * @param servletRequest
	 * @return 用户基本信息
	 */
	@RequestMapping(value = "/user/current.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<BaseUserInfoResponse> currentUser(HttpServletRequest servletRequest) {
		BaseUserInfoResponse response = new BaseUserInfoResponse();

		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		response.setAccountId(user.getAccountId());
		response.setAccountName(user.getAccountName());
		response.setDisplayName(user.getUserName());
		response.setOrgId(user.getOrgId());
		response.setUserCode(user.getUserCode());
		response.setUserName(user.getUserName());

		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, response);
	}

	/***
	 * 用户名称或者警号的精确查找
	 * 
	 * @param kw
	 *            关键词
	 * @return
	 */
	@RequestMapping(value = "/user/userFuzzySearch.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<UserInfoResponse> userFuzzySearch(@Valid @NotBlank @RequestParam("kw") String kw) {

		UserInfoResponse response = null;

		// 先按名称查询
		UserRsp user = iUserService.getUserInfoByUserName(kw);

		if (user == null) {
			// 名称查询不到用户再按照警员编号查询
			user = iUserService.getUserInfoByUserCode(kw);
		}

		if (user != null) {
			final String userCode = user.getUserCode();

			response = new UserInfoResponse();
			response.setAccountId(user.getAccountId());
			response.setAccountName(user.getAccountName());
			response.setDisplayName(user.getUserName());

			GetDSJParams params = new GetDSJParams();
			params.setUserCodes(new ArrayList<String>() {
				{
					add(userCode);
				}
			});
			List<DSJ> dsjs = dsjService.getDSJs(params, -1, 0);

			if (dsjs != null && dsjs.size() > 0) {
				DSJ d = dsjs.get(0);
				response.setDsjType(d.getDsjType());

				final String deviceId = d.getDeviceId();
				List<DSJStatus> status = dsjService.getDSJStatus("01", new ArrayList<String>() {
					{
						add(deviceId);
					}
				});
				if (status != null && status.size() > 0) {
					DSJStatus s = status.get(0);

					if (s.getStatus() == 1) {
						response.setOnline(true);
					}
				}
			}

			response.setOrgId(user.getOrgId());
			response.setUserCode(user.getUserCode());
			response.setUserName(user.getUserName());
		}

		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, response);
	}

	/***
	 * 根据用户id列表获取用户在线状态信息列表
	 * 
	 * @param ids
	 *            用户id列表
	 * @return 用户在线状态列表
	 */
	@RequestMapping(value = "/user/onlineStates.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<UserStatusResponse>> getUserOnlineStates(
			@Valid @NotEmpty @RequestParam("ids") List<Integer> ids) {
		List<UserStatusResponse> response = new ArrayList<UserStatusResponse>();

		Map<Integer, List<DSJStatus>> m = dsjService.getDSJStatusByAccountId(ids, 0, 10);

		/*
		 * 设置部门人员在线状态
		 */
		for (Entry<Integer, List<DSJStatus>> entry : m.entrySet()) {
			UserStatusResponse r = new UserStatusResponse();
			r.setAccountId(entry.getKey());
			List<DSJStatus> status = entry.getValue();
			if (status == null || status.size() == 0) {
				r.setOnline(false);
			} else {
				DSJStatus s = status.get(0);
				if (s.getStatus() == 1) {
					r.setOnline(true);
				}
			}
			response.add(r);
		}

		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, response);
	}

	/**
	 * 根据警员名查询警员信息
	 * 
	 * @param servletRequest
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/user/userName.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<UserRsp> getUserInfoByUserName(HttpServletRequest servletRequest,
			@RequestParam("userName") String userName) {

		// 获取当前登录用户信息
		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);

		// 根据警员名称查询警员信息
		UserRsp userR = iUserService.getUserInfoByUserName(userName);

		if (userR != null) {
			if (hs.contains(userR.getOrgId()))
				return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, userR);

		}
		return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
	}

	/**
	 * 当前登录用户管理范围部门树
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/user/orgTree.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<OrgRsp>> getAllOrgTree(HttpServletRequest servletRequest) throws ParseException {
		// 获取当前登录用户信息
		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);
		if (user == null) {
			return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
		}
		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);
		if (hs.isEmpty()) {
			return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
		}

		// 获取部门列表
		List<OrgRsp> orgs = iOrgService.getAllOrgRsp();

		List<OrgRsp> privOrgs = new ArrayList<>(hs.size());
		for (OrgRsp org : orgs) {
			int orgId = org.getId();
			if (hs.contains(orgId)) {
				privOrgs.add(org);
			}
		}
		Map<Integer, OrgRsp> privOrgRspMap = new LinkedHashMap<>(privOrgs.size() * 2);
		for (OrgRsp orgRsp : privOrgs) {
			int orgId = orgRsp.getId();
			privOrgRspMap.put(orgId, orgRsp);
		}

		List<OrgRsp> headers = new LinkedList<>();
		for (Entry<Integer, OrgRsp> entry : privOrgRspMap.entrySet()) {
			OrgRsp node = entry.getValue();
			int parentId = node.getParentId();
			if (privOrgRspMap.get(parentId) != null) {
				List<OrgRsp> children = privOrgRspMap.get(parentId).getChildren();
				if (children == null) {
					children = new LinkedList<>();
				}
				children.add(node);
			} else {
				headers.add(node);
			}
		}
		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, headers);
	}

	/**
	 * 当前部门的在线人数/总人数
	 * 
	 * @param org_ids
	 * @return
	 */
	@RequestMapping(value = "/user/orgOline.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<OrgOnlineResponse>> getOrgOnlineCount(HttpServletRequest servletRequest,
			@Valid @RequestBody OrgCountRequest request) {

		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);

		List<OrgOnlineResponse> orgOnlineList = new ArrayList<OrgOnlineResponse>();
		List<Integer> org_ids = request.getOrg_ids();
		for (Integer orgId : org_ids) {
			if (hs.contains(orgId)) {
				OrgOnlineResponse orgOnline = new OrgOnlineResponse();
				OrgRsp orgRsp = iOrgService.getOrgAndChildByOrgId(orgId);
				List<String> orgCodes = OrgUtils.getOrgCodes(orgRsp.getId());
				// 当前部门的在线人数
				int orgCount = dsjService.getDSJOnlineCountByOrgCodes(orgCodes);
				// 当前部门的总人数
				int total = genSubOrgCount(orgRsp);
				orgOnline.setOrgId(orgId);
				orgOnline.setOrgCount(orgCount);
				orgOnline.setTotalCount(total);
				orgOnlineList.add(orgOnline);
			}
		}
		if (orgOnlineList == null || orgOnlineList.isEmpty()) {
			return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
		} else {

			return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, orgOnlineList);
		}
	}

	/**
	 * 所有部门的在线人数/总人数
	 * 
	 * @param org_ids
	 * @return
	 */
	@RequestMapping(value = "/user/orgOlineAll.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<OrgOnlineResponse>> getOrgOnlineAll(HttpServletRequest servletRequest) {

		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);

		List<OrgUserNodeRsp> orgUser = iUserService.getOrgUserTree();
		List<Integer> orgId = getOrgIdList(orgUser);
		orgId.retainAll(orgIds);

		Map<Integer, Integer> map = dsjService.getOnlineDSJByOrgIds(orgId);

		List<OrgOnlineResponse> orgOnlineList = genOrgAll(orgUser, hs, null, map);

		if (orgOnlineList == null || orgOnlineList.isEmpty()) {
			return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
		} else {

			return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, orgOnlineList);
		}
	}

	/**
	 * 当前点开部门的用户在线状态
	 * 
	 * @param org_ids
	 * @return
	 */
	@RequestMapping(value = "/user/orgUserState.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<OrgUSerStateResponse>> getOrgUserByOrgIds(HttpServletRequest servletRequest,
			@Valid @RequestBody OrgCountRequest request) {

		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);

		List<OrgUSerStateResponse> OrgUSerStateResponse = new ArrayList<OrgUSerStateResponse>();
		List<Integer> org_ids = request.getOrg_ids();
		for (Integer orgId : org_ids) {
			List<UserInfoResponse> userInfoList = new ArrayList<UserInfoResponse>();
			OrgUSerStateResponse orgUserState = new OrgUSerStateResponse();

			List<UserRsp> userRsp = iUserService.getUserInfosByOrgId(orgId);
			List<Integer> accountid = new ArrayList<Integer>();

			if (userRsp != null && !userRsp.isEmpty()) {
				for (UserRsp userR : userRsp) {
					accountid.add(userR.getAccountId());
				}

				// 根据用户id查询设备在线情况
				Map<Integer, List<DSJStatus>> dsjstatus = null;
				if (accountid != null) {
					dsjstatus = dsjService.getDSJStatusByAccountId(accountid, -1, 0);
				}

				if (hs.contains(orgId)) {
					orgUserState.setOrgId(orgId);
				}

				for (UserRsp userR : userRsp) {

					if (hs.contains(userR.getOrgId())) {

						UserInfoResponse userInfo = new UserInfoResponse();
						// accountid.add(userR.getAccountId());
						userInfo.setAccountId(userR.getAccountId());
						userInfo.setAccountName(userR.getAccountName());
						userInfo.setUserName(userR.getUserName());
						userInfo.setUserCode(userR.getUserCode());
						userInfo.setDisplayName(userR.getUserName());
						userInfo.setOrgId(userR.getOrgId());
						if (dsjstatus != null) {
							List<DSJStatus> st = dsjstatus.get(userR.getAccountId());
							if (st != null && st.size() > 0) {
								DSJStatus s = st.get(0);
								if (s.getStatus() == 1) {
									userInfo.setOnline(true);
								}
							}
						}
						userInfoList.add(userInfo);
					}
				}
				orgUserState.setUserInfo(userInfoList);
			}
			OrgUSerStateResponse.add(orgUserState);
		}
		if (OrgUSerStateResponse == null || OrgUSerStateResponse.isEmpty()) {
			return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
		} else {

			return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, OrgUSerStateResponse);
		}
	}

	/**
	 * 根据用户id,获取部门组织架构树
	 * 
	 * @return 返回部门用户组织架构树
	 * @throws ParseException
	 */
	@RequestMapping(value = "/orgUser.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<OrgUserInfoResponse>> getOrgUserTree(HttpServletRequest servletRequest,
			boolean notShowStatus) throws ParseException {
		// 获取当前登录用户信息
		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);

		// 获取用户部门树
		List<OrgUserNodeRsp> orgUser = iUserService.getOrgUserTree();

		List<OrgUserInfoResponse> response = genSubOrgUserInfoResponse(servletRequest, orgUser, null, hs,
				notShowStatus);
		if (response == null || response.isEmpty()) {
			return ReturnInfo.genResponseEntity(ReturnInfo.FAILED);
		} else {
			return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, response);
		}
	}

	/**
	 * 根据用户id,获取部门组织架构树
	 * 
	 * @return 返回部门组织架构树
	 */
	@RequestMapping(value = "/org.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getOrgTree(HttpServletRequest servletRequest) {
		JSONArray jsonArray = iUserService.getOrgTree();

		// 获取当前登录用户信息
		String accountName = servletRequest.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		// 获取用户的部门权限列表
		List<Integer> orgIds = userPrivService.listPrivileges(user.getUserCode());
		Set<Integer> hs = new HashSet<Integer>();
		hs.addAll(orgIds);

		/*
		 * 转换成实体列表，用于检查权限
		 */
		Gson gson = new Gson();
		List<OrgTreeEntity> orgTree = gson.fromJson(jsonArray.toString(),
				new TypeToken<List<SystemComtroller.OrgTreeEntity>>() {

					/**
					 * 
					 */
					private static final long serialVersionUID = -2132733540297525119L;
				}.getType());
		grantNewtree(orgTree, hs);

		return gson.toJson(orgTree);
	}

	/**
	 * 提供用户登出web的功能
	 * 
	 * @return 登出后重定向至登录界面
	 */
	@RequestMapping(value = "/logout.action")
	public String webLogout() {
		SysLogUtils.addMsysLog(0, 2120, "", "");
		session.invalidate();
		return "redirect:/system/index.action";
	}

	@RequestMapping(value = "/machineCode.do", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getMachineCode() {
		return ReturnInfo.genResultJson(ReturnInfo.SUCCESS, licenseService.getMachineCode());
	}

	/**
	 * 用户验证
	 * 
	 * @param requestBody
	 *            JSON格式，请求体中包括字符串userName用户名和字符串password密码两个参数
	 * @return 以JSON格式返回验证成功或失败信息
	 */
	@RequestMapping(value = "/check.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String checkUserCodeAndPassword(@RequestBody String requestBody, HttpServletRequest request) {
		if (!licenseService.check()) {
			return ReturnInfo.genResultJson(ReturnInfo.FAILED, "未授权");
		}
		Map<String, Object> map = JsonUtil.parseMap(requestBody);
		if (!map.containsKey("userName") || !map.containsKey("password")) {
			return ReturnInfo.genResultJson(ReturnInfo.PARAMERROR);
		}
		String security_url = qpApplicationProperties.getSecurityUrl();
		String URL = security_url + "login/checkUserNameAndPassword.noCons";
		List<HTTPParam> params = new ArrayList<HTTPParam>();
		params.add(new HTTPParam("userName", map.get("userName").toString()));
		params.add(new HTTPParam("password", map.get("password").toString()));
		String result = "";
		try {
			result = remoteCallService.sendGet(URL, params);
		} catch (Exception e) {
			LOG.error("登录失败", e);
			return result;
		}
		String ip = getRemoteHost(request);
		UserRsp userRsp = iUserService.getUserInfoByAccountName(map.get("userName").toString());
		if (userRsp != null) {
			loginService.saveLoginInfo(userRsp.getAccountId(), userRsp.getUserCode(), userRsp.getUserName(), ip);
		}
		Ret r = JsonUtil.parseObject(result, Ret.class);
		if (r.getCode().equals("200")) {
			// 登录成功
			SysLogUtils.addMsysLog(map.get("userName").toString(), 0, 2119, "", "");
		}
		return result;
	}

	public String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}

	@RequestMapping(value = "/login/users.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<Map<String, Date>> getUserLastLoginInfosByUserCodes(@RequestBody List<String> userCodes) {
		if (userCodes == null || userCodes.isEmpty()) {
			return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS);
		}
		Map<String, Date> response = loginService.getLastLoginInfosByUserCodes(userCodes);
		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, response);
	}

	/**
	 * 根据部门id获取该部门下所有用户信息
	 * 
	 * @param org_id
	 *            部门id，org_id与org_code是有区别的，org_id是系统定义的主键，org_code是通用的部门编号
	 * @return 返回该部门下所有用户信息
	 * @throws ParseException
	 */
	@RequestMapping(value = "/org/user.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<List<UserInfoResponse>> getUserListByOrgId(HttpServletRequest servlet,
			@RequestParam("org_id") int org_id) throws ParseException {
		String accountName = servlet.getRemoteUser();
		UserRsp user = iUserService.getUserInfoByAccountName(accountName);

		if (!userPrivService.hasPrivilege(user.getUserCode(), org_id)) {
			List<UserInfoResponse> results = new ArrayList<UserInfoResponse>();
			return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, results);
		}

		List<UserInfoResponse> userList = userService.getUserListByOrgId(org_id, false);
		UserListComparator c = new UserListComparator();
		Collections.sort(userList, c);
		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, userList);
	}

	/***
	 * 根据完整的部门树结构构造有管理范围控制的部门树。 当遇到无权限的部门范围时候，将id和orgCode设置为一个非法的值-1
	 * 
	 * @param orgTree
	 *            原始部门树
	 * @return
	 */
	private void grantNewtree(List<OrgTreeEntity> orgTree, Set<Integer> hs) {
		for (OrgTreeEntity org : orgTree) {
			if (org.getChildren() != null && org.getChildren().size() > 0) {
				grantNewtree(org.getChildren(), hs);
			}

			if (!hs.contains(org.getId())) {
				org.setEnable(false);
			}
		}
	}

	/**
	 * 迭代当前部门的所有人数
	 * 
	 * @param org_id
	 * @return
	 */
	private int genSubOrgCount(OrgRsp orgRsp) {
		int orgIds = 0;
		List<UserRsp> orgUser = iUserService.getUserInfosByOrgId(orgRsp.getId());

		int orgs = genSubChildOrg(orgRsp, orgIds);

		if (orgUser != null) {
			orgs += orgUser.size();
		}
		return orgs;
	}

	private int genSubChildOrg(OrgRsp orgRsp, int orgIds) {

		if (orgRsp.getChildren() != null) {
			List<OrgRsp> childrenNode = orgRsp.getChildren();
			for (OrgRsp org : childrenNode) {
				List<UserRsp> orgUsersub = iUserService.getUserInfosByOrgId(org.getId());

				if (orgUsersub != null) {
					orgIds += orgUsersub.size();
				}

				orgIds = genSubChildOrg(org, orgIds);

			}
		}
		return orgIds;
	}

	private List<OrgUserInfoResponse> genSubOrg(OrgRsp org, OrgInfoResponse pOrgInfos, Set<Integer> hs)
			throws ParseException {
		List<OrgUserInfoResponse> resLists = new ArrayList<OrgUserInfoResponse>();

		if (org != null) {
			OrgTreeResponse orgInfo = new OrgTreeResponse();
			orgInfo.setOrgId(org.getId());
			orgInfo.setOrgName(org.getName());
			orgInfo.setDisplayName(org.getName());
			orgInfo.setParentId(org.getParentId());
			List<OrgUserInfoResponse> children = genSubOrgResponse(org.getChildren(), pOrgInfos, hs);
			orgInfo.setChildren(children);
			resLists.add(orgInfo);

		}

		return resLists;
	}

	/**
	 * 部门树
	 * 
	 * @param servletRequest
	 * @param orgUser
	 * @param pOrgInfo
	 * @return
	 * @throws ParseException
	 */
	private List<OrgUserInfoResponse> genSubOrgResponse(List<OrgRsp> orgUser, OrgInfoResponse pOrgInfo, Set<Integer> hs)
			throws ParseException {
		List<OrgUserInfoResponse> resList = new ArrayList<OrgUserInfoResponse>();

		for (OrgRsp orgRsp : orgUser) {
			if (hs.contains(orgRsp.getId())) {

				int id = orgRsp.getId();
				String name = orgRsp.getName();
				List<OrgRsp> childrenNode = orgRsp.getChildren();
				int parentId = orgRsp.getParentId();
				if (childrenNode != null) {
					OrgTreeResponse orgInfo = new OrgTreeResponse();
					orgInfo.setOrgId(id);
					orgInfo.setOrgName(name);
					orgInfo.setDisplayName(name);
					orgInfo.setParentId(parentId);
					List<OrgUserInfoResponse> children = genSubOrgResponse(childrenNode, orgInfo, hs);
					orgInfo.setChildren(children);

					resList.add(orgInfo);
				}
			}
		}

		return resList;
	}

	private List<OrgOnlineResponse> genOrgAll(List<OrgUserNodeRsp> orgUser, Set<Integer> hs,
			OrgOnlineResponse orgOnlines, Map<Integer, Integer> map) {

		List<OrgOnlineResponse> resList = new ArrayList<OrgOnlineResponse>();
		int totalCount = 0;
		for (OrgUserNodeRsp org : orgUser) {

			if (hs.contains(org.getId()) && org.getChildren() != null) {
				if (org.getChildren() != null) {
					OrgOnlineResponse orgOnline = new OrgOnlineResponse();
					orgOnline.setOrgId(org.getId());
					orgOnline.setOrgName(org.getName());
					orgOnline.setDisplayName(org.getName());

					List<OrgOnlineResponse> children = genOrgAll(org.getChildren(), hs, orgOnline, map);
					orgOnline.setChildren(children);

					if (map != null && map.containsKey(org.getId())) {

						orgOnline.setOrgCount(map.get(org.getId()));

					}
					resList.add(orgOnline);
				}
			} else if (org.getChildren() == null) {

				totalCount += 1;

			}
		}

		if (orgOnlines != null) {

			orgOnlines.setTotalCount(totalCount);
		}

		return resList;
	}

	private List<Integer> getOrgIdList(List<OrgUserNodeRsp> orgUser) {

		List<Integer> orgIds = new ArrayList<Integer>();

		for (OrgUserNodeRsp org : orgUser) {
			if (org.getChildren() != null) {

				orgIds.add(org.getId());
				List<Integer> orgId = getOrgIdList(org.getChildren());
				orgIds.addAll(orgId);
			}
		}

		return orgIds;
	}

	private List<OrgUserInfoResponse> genSubOrgUserInfoResponse(HttpServletRequest servletRequest,
			List<OrgUserNodeRsp> orgUser, OrgInfoResponse pOrgInfo, Set<Integer> hs, boolean notShowStatus)
			throws ParseException {
		List<OrgUserInfoResponse> resList = new ArrayList<OrgUserInfoResponse>();

		for (OrgUserNodeRsp orgRsp : orgUser) {

			int id = orgRsp.getId();
			String name = orgRsp.getName();
			List<OrgUserNodeRsp> childrenNode = orgRsp.getChildren();

			if (childrenNode != null) {
				OrgInfoResponse orgInfo = new OrgInfoResponse();
				// 设置web端显示内容
				orgInfo.setDisplayName(name);
				// 部门名称
				orgInfo.setOrgName(name);
				// 部门id
				orgInfo.setOrgId(id);

				List<OrgUserInfoResponse> children = genSubOrgUserInfoResponse(servletRequest, childrenNode, orgInfo,
						hs, notShowStatus);
				orgInfo.setChildren(children);

				resList.add(orgInfo);
			}
		}
		if (pOrgInfo != null) {
			if (hs.contains(pOrgInfo.getOrgId())) {

				/* 用权限的部门才去获取部门下的人员信息 */
				List<UserInfoResponse> userList = userService.getUserListByOrgId(pOrgInfo.getOrgId(), notShowStatus);
				UserListComparator c = new UserListComparator();
				Collections.sort(userList, c);
				resList.addAll(userList);
			}
		}
		return resList;
	}

	static class UserListComparator implements Comparator<UserInfoResponse> {
		@Override
		public int compare(UserInfoResponse user1, UserInfoResponse user2) {
			int b1 = (boolean) user1.isOnline() ? 1 : 0;
			int b2 = (boolean) user2.isOnline() ? 1 : 0;
			if (b1 > b2) {
				return -1;
			} else if (b1 == b2) {
				return 0;
			} else {
				return 1;
			}
		}
	}

	/***
	 * 高云平台部门树接口返回的格式
	 * 
	 * @author QH
	 *
	 */
	public static class OrgTreeEntity {
		private Integer id;
		private String text;
		private Date updateTime;
		private String orgCode;
		private Integer orgIsDelete;
		private List<OrgTreeEntity> children;
		private Integer orderNum;
		private String iconCls;

		// 管理范围控制字段，无权限false，有权限true
		private Boolean enable = true;

		public Boolean getEnable() {
			return enable;
		}

		public void setEnable(Boolean enable) {
			this.enable = enable;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Date getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Date updateTime) {
			this.updateTime = updateTime;
		}

		public String getOrgCode() {
			return orgCode;
		}

		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		public Integer getOrgIsDelete() {
			return orgIsDelete;
		}

		public void setOrgIsDelete(Integer orgIsDelete) {
			this.orgIsDelete = orgIsDelete;
		}

		public List<OrgTreeEntity> getChildren() {
			return children;
		}

		public void setChildren(List<OrgTreeEntity> children) {
			this.children = children;
		}

		public Integer getOrderNum() {
			return orderNum;
		}

		public void setOrderNum(Integer orderNum) {
			this.orderNum = orderNum;
		}

		public String getIconCls() {
			return iconCls;
		}

		public void setIconCls(String iconCls) {
			this.iconCls = iconCls;
		}
	}

	/***
	 * 登录成功返回的json字符串类 {"code":"200","msg":"用户名和密码正确！"}
	 * 
	 * @author QH
	 *
	 */
	public static class Ret {
		private String code;
		private String msg;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
