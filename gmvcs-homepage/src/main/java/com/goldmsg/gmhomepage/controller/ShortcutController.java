package com.goldmsg.gmhomepage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.goldmsg.core.utils.ReturnInfo;
import com.goldmsg.gmhomepage.constants.ShortcutModel;
import com.goldmsg.gmhomepage.controller.response.BaseResponse;
import com.goldmsg.gmhomepage.controller.response.shortcut.UserSetShortcutResponse;
import com.goldmsg.gmhomepage.jentity.ItemShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcut;
import com.goldmsg.gmhomepage.jentity.TbRelUserShortcutPK;
import com.goldmsg.gmhomepage.service.RelUserShortcutService;
import com.goldmsg.gmhomepage.service.ShortcutService;
import com.goldmsg.gmhomepage.utils.PrivUtils;
import com.gosun.service.entity.UserRsp;
import com.gosun.service.user.IUserService;

/***
 * 快捷导航图标相关controller
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午2:39:17
 */
@Controller
@RequestMapping("/shortcut")
public class ShortcutController {

	@Autowired
	ShortcutService shortcutService;
	
	@Autowired
	RelUserShortcutService relUserShortcutService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	JpaTransactionManager transactionManager;
	
	/***
	 * 获取用户设置的快捷标签列表
	 * @param servlet
	 * @param set 是否返回设置的标签，true--是，false--否
	 * @return	返回应用和管理类别的快捷标签列表
	 */
	@RequestMapping(value = "/set/list.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse<UserSetShortcutResponse> getUserSetShortcut(HttpServletRequest servlet, @Valid @NotNull @RequestParam("set") Boolean set) {
		/*
		 * 获取用户id先
		 */
		String accountName = servlet.getRemoteUser();
		UserRsp user = userService.getUserInfoByAccountName(accountName);

		List<String> privList = PrivUtils.getPrivilegeListByAccountName(accountName);
		
		List<ItemShortcut> setList = null;
		
		if (set) {
			setList = shortcutService.findSetByAccountId(user.getAccountId());
		} else {
			setList = shortcutService.findUnSetByAccountId(user.getAccountId());
		}
		
		UserSetShortcutResponse response = new UserSetShortcutResponse();
		response.setAppShortcut(new ArrayList<ItemShortcut>());
		response.setMgrShortcut(new ArrayList<ItemShortcut>());
		
		for (ItemShortcut item : setList) {
			for(String str: privList){
				if(item.getCode()!=null&&!"".equals(item.getCode())){
				if(item.getCode().equals(str)){
					
					if (item.getModel() == ShortcutModel.MODEL_APP) {
						response.getAppShortcut().add(item);
						} else if (item.getModel() == ShortcutModel.MODEL_MGR) {
							response.getMgrShortcut().add(item);
						}
				    }	
				}
			}	
			}	
			
		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS, response);
	}
	
	
	/***
	 * 批量修改用户的快捷标签设置
	 * @param servlet
	 * @param itemIds	快捷标签id列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/set/batchModify.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse batchModify(HttpServletRequest servlet, 
			@Valid @NotEmpty @RequestParam("itemIds") List<Integer> itemIds) {
		/*
		 * 获取用户id先
		 */
		String accountName = servlet.getRemoteUser();
		UserRsp user = userService.getUserInfoByAccountName(accountName);
		
		/*
		 * 获取用户已经设置设置的快捷标签
		 */
		List<ItemShortcut> l = shortcutService.findSetByAccountId(user.getAccountId());
		
		/*
		 * 查找不存在itemIds中的快捷标签
		 */
		List<TbRelUserShortcutPK> deleteIds = new ArrayList<TbRelUserShortcutPK>();
		List<Integer> simpleIds = new ArrayList<Integer>();
		for (ItemShortcut item : l) {
			simpleIds.add(item.getId());
			if (!itemIds.contains(item.getId())) {
				TbRelUserShortcutPK pk = new TbRelUserShortcutPK();
				pk.setAccountId(user.getAccountId());
				pk.setItemId(item.getId());
				
				deleteIds.add(pk);
			}
		}
		
		/*
		 * 计算需要插入的新快捷标签
		 */
		itemIds.removeAll(simpleIds);
		
		
		/*
		 * 启动事务，保证原子性
		 */
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);  
		TransactionStatus sta = transactionManager.getTransaction(def);
		
		if (deleteIds.size() > 0) {
			/*
			 * 删除快捷标签关联关系
			 */
			relUserShortcutService.batchDelete(deleteIds);
		}
		
		/*
		 * 添加快捷标签关联关系
		 */
		if (itemIds.size() > 0) {
			List<TbRelUserShortcut> rels = new ArrayList<TbRelUserShortcut>();
			
			for (Integer id : itemIds) {
				TbRelUserShortcutPK pk = new TbRelUserShortcutPK();
				pk.setAccountId(user.getAccountId());
				pk.setItemId(id);
				
				TbRelUserShortcut rel = new TbRelUserShortcut();
				rel.setId(pk);
				rel.setUpdateTime(new Date());
				
				rels.add(rel);
			}
			
			relUserShortcutService.batchSave(rels);
		}
		
		transactionManager.commit(sta);
		
		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS);
	}
	
	
	/***
	 * 修改用户的快捷标签设置
	 * @param servlet
	 * @param itemId	标签id
	 * @param delete	是否删除
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/set/modify.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public BaseResponse modify(HttpServletRequest servlet, 
			@Valid @NotNull @RequestParam("itemId") Integer itemId,
			@Valid @NotNull @RequestParam("delete") Boolean delete) {
		/*
		 * 获取用户id先
		 */
		String accountName = servlet.getRemoteUser();
		UserRsp user = userService.getUserInfoByAccountName(accountName);
		
		TbRelUserShortcut s = new TbRelUserShortcut();
		
		TbRelUserShortcutPK pk = new TbRelUserShortcutPK();
		pk.setAccountId(user.getAccountId());
		pk.setItemId(itemId);
		s.setId(pk);
		s.setUpdateTime(new Date());
		
		if (!delete) {
			relUserShortcutService.save(s);
		} else {
			relUserShortcutService.delete(pk);
		}
		
		return ReturnInfo.genResponseEntity(ReturnInfo.SUCCESS);
	}
}
