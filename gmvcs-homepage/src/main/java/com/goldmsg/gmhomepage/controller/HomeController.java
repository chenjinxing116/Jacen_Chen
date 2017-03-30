package com.goldmsg.gmhomepage.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * 首页controller，跳转各子模块
 * 
 * @author QH Email: qhs_dream@163.com 2016年10月8日 : 上午11:40:16
 */
@Controller
@RequestMapping("/home")
public class HomeController {

	/**
	 * 首页
	 * 
	 * @return 返回首页页面，如果失败，返回404页面
	 */
	@RequestMapping(value = "/index.action", method = RequestMethod.GET)
	public String index() {
		// TODO:需要根据用户访问权限加载可访问界面
		return "welcome";
	}

	/**
	 * 地图指挥模块主界面
	 * 
	 * @return 返回地图指挥主界面JSP View
	 */
	@RequestMapping(value = "/map/command/index.action", method = RequestMethod.GET)
	public String mapCommandIndex() {
		return "realytimeConduct/mapConduct/index";
	}

	/**
	 * sos告警管理模块主界面
	 * 
	 * @return 返回告警管理界面JSP View
	 */
	@RequestMapping(value = "/sos/manage/index.action", method = RequestMethod.GET)
	public String sosManagerIndex() {
		return "realytimeConduct/sosManage/index";
	}

	/**
	 * 轨迹查询展示主界面
	 * 
	 * @return 返回轨迹查询主界面JSP View
	 */
	@RequestMapping(value = "/track/query/index.action", method = RequestMethod.GET)
	public String trackIndex() {
		return "realytimeConduct/trackQuery/index";
	}

	/***
	 * 视频监控主界面
	 * 
	 * @return 返回视频监控主界面JSP View
	 */
	@RequestMapping(value = "/video/monitor/index.action", method = RequestMethod.GET)
	public String monitorIndex() {
		return "realytimeConduct/videoMonitor/index";
	}

	/***
	 * 在线录制视频回放主界面
	 * 
	 * @return 返回视频回放主界面JSP View
	 */
	@RequestMapping(value = "/video/replay/index.action", method = RequestMethod.GET)
	public String replayIndex() {
		return "realytimeConduct/videoReplay/index";
	}

	/**
	 * 资源配置管理界面
	 */
	@RequestMapping(value = "/resources.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String resources() {
		// TODO:需要根据用户访问权限加载可访问界面
		return "omm/home/index";
	}

	/**
	 * 跳转到业务分析界面
	 */
	@RequestMapping(value = "/businessAnalysis.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String businessAnalysis() {
		// TODO:需要根据用户访问权限加载可访问界面
		return "businessAnalysis/home/index";
	}

	/**
	 * 跳转到执法音视频数据管理平台
	 */
	@RequestMapping(value = "/audioAndVideoData.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String audioAndVideoData() {
		// TODO:需要根据用户访问权限加载可访问界面
		return "audioAndVideoData/home/index";
	}

	/**
	 * 跳转到视音频库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/audioAndVideo.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String audioAndVideo() {
		return "audioAndVideoData/audioAndVideo/index";
	}

	/**
	 * 跳转到执法档案
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lawEnforcementRecord.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String lawEnforcementRecord() {
		return "audioAndVideoData/lawEnforcementRecord/index";
	}

	/**
	 * 跳转到统计分析
	 * 
	 * @return
	 */
	@RequestMapping(value = "/countAnalysis.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String countAnalysis() {
		return "audioAndVideoData/countAnalysis/index";
	}

	/**
	 * 跳转到日志管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logManage.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String logManage() {
		return "audioAndVideoData/logManage/index";
	}
}
