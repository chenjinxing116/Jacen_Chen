package com.goldmsg.gmhomepage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldmsg.data.constant.DeviceType;
import com.goldmsg.gmhomepage.controller.response.system.UserInfoResponse;
import com.goldmsg.res.entity.vo.DSJStatus;
import com.goldmsg.res.service.DSJService;
import com.gosun.service.entity.UserRsp;
import com.gosun.service.user.IUserService;

@Service
public class RTCUserService {

	@Autowired
	IUserService iUserService;

	@Autowired
	DSJService dsjService;

	public List<UserInfoResponse> getUserListByOrgId(int org_id, boolean notShowStatus) {
		List<UserInfoResponse> userList = new ArrayList<UserInfoResponse>();
		List<UserRsp> userRspList = iUserService.getUserInfosByOrgId(org_id);

		if (userRspList == null || userRspList.isEmpty()) {
			return userList;
		}

		List<Integer> accountIds = new ArrayList<Integer>();
		for (UserRsp user : userRspList) {
			accountIds.add(user.getAccountId());
		}
		// 根据用户id查询设备在线情况
		Map<Integer, List<DSJStatus>> dsjstatusMap = null;
		if (!notShowStatus) {
			dsjstatusMap = dsjService.getDSJStatusByAccountId(accountIds, -1, 0);
		}

		for (UserRsp userRsp : userRspList) {
			UserInfoResponse userInfo = new UserInfoResponse();
			int accountId = userRsp.getAccountId();
			String userCode = userRsp.getUserCode();
			String userName = userRsp.getUserName();
			String accountName = userRsp.getAccountName();
			userInfo.setAccountId(accountId);
			userInfo.setUserCode(userCode);
			userInfo.setUserName(userName);
			userInfo.setDisplayName(userName);
			userInfo.setAccountName(accountName);
			// 用户在线状态默认为false
			userInfo.setOnline(false);
			// 执法仪类型
			userInfo.setDsjType(DeviceType.FOUR_G_TYPE);
			if (dsjstatusMap != null) {
				List<DSJStatus> st = dsjstatusMap.get(userRsp.getAccountId());
				if (st != null && st.size() > 0) {
					DSJStatus s = st.get(0);
					if (s.getStatus() == 1) {
						userInfo.setOnline(true);
					}
				}
			}
			userList.add(userInfo);
		}
		return userList;
	}
}
