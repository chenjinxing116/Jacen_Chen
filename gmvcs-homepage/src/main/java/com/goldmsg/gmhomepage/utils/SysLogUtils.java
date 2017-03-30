package com.goldmsg.gmhomepage.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.goldmsg.data.dentity.MSystemLog;
import com.goldmsg.data.service.log.MgrSysLogService;
import com.gosun.service.entity.OrgRsp;
import com.gosun.service.entity.UserRsp;
import com.gosun.service.org.IOrgService;
import com.gosun.service.user.IUserService;

/**
 *
 */
@Repository
public class SysLogUtils {
	
	@Autowired
	protected static MgrSysLogService mgrSysLogService;
	
	protected static IUserService userService;
	protected static IOrgService orgService;
	
	/**
	 * @param type    日志类型
	 * @param operaType  操作类型
	 * @return
	 */
	public static boolean  addMsysLog(int type,int operaType,String fileInfo,String desc){
	    MSystemLog mSystemLog=new MSystemLog();
	    
		mSystemLog.setOrgCode(UserSessions.getCurrentUser().getOrg().getOrgCode());
		mSystemLog.setOrgName(UserSessions.getCurrentUser().getOrg().getName());
		mSystemLog.setUserCode(UserSessions.getCurrentUser().getUser().getUserCode());
		mSystemLog.setUserName(UserSessions.getCurrentUser().getUser().getUserName());
		mSystemLog.setType(type);
		mSystemLog.setOperaType(operaType);
		mSystemLog.setFileInfo(fileInfo);
		mSystemLog.setOperaTime(new Date());
		mSystemLog.setDesc(desc);
		
		boolean msls=mgrSysLogService.addLog(mSystemLog);
	
	    return msls;
	}
	
	/***
	 * 保存用户操作日志
	 * @param accountName	登录的用户账号名
	 * @param type	日志类型
	 * @param operaType	操作类型
	 * @param fileInfo
	 * @param desc
	 * @return
	 */
	public static boolean  addMsysLog(String accountName, int type,int operaType,String fileInfo,String desc){
	    MSystemLog mSystemLog=new MSystemLog();
	    
	    UserRsp user = userService.getUserInfoByAccountName(accountName);
	    OrgRsp org = orgService.getOrgInfoByOrgId(user.getOrgId());
	    
		mSystemLog.setOrgCode(org.getOrgCode());
		mSystemLog.setOrgName(org.getName());
		mSystemLog.setUserCode(user.getUserCode());
		mSystemLog.setUserName(org.getName());
		mSystemLog.setType(type);
		mSystemLog.setOperaType(operaType);
		mSystemLog.setFileInfo(fileInfo);
		mSystemLog.setOperaTime(new Date());
		mSystemLog.setDesc(desc);
		
		boolean msls=mgrSysLogService.addLog(mSystemLog);
	
	    return msls;
	}
	
	
	public static MgrSysLogService getMgrSysLogService() {
		return mgrSysLogService;
	}



	public static void setMgrSysLogService(MgrSysLogService mgrSysLogService) {
		SysLogUtils.mgrSysLogService = mgrSysLogService;
	}


	public static IUserService getUserService() {
		return userService;
	}


	public static void setUserService(IUserService userService) {
		SysLogUtils.userService = userService;
	}


	public static IOrgService getOrgService() {
		return orgService;
	}


	public static void setOrgService(IOrgService orgService) {
		SysLogUtils.orgService = orgService;
	}
}
