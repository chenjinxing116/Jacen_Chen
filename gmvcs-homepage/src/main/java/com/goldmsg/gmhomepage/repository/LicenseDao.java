package com.goldmsg.gmhomepage.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.goldmsg.gmhomepage.jentity.TbBaseLicense;

/**
 * @Description:TODO
 * @author xiangrandy E-mail:351615708@qq.com
 * @Time:2017年3月27日下午3:35:56
 */
public interface LicenseDao extends PagingAndSortingRepository<TbBaseLicense, Integer> {

}
