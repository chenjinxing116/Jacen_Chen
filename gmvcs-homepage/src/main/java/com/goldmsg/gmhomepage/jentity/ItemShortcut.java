package com.goldmsg.gmhomepage.jentity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/***
 * 首页快捷标签实体类
 * @author QH
 * @Email: qhs_dream@163.com
 * @Date:  2016年12月9日: 下午2:05:21
 */
@Entity
@Table(name = "tb_item_shortcut")
@NamedQuery(name = "ItemShortcut.findAll", query = "SELECT t FROM ItemShortcut t")
public class ItemShortcut implements Serializable {
	private static final long serialVersionUID = 3200965172435790600L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String title;	//标题，unicode编码
	private String icon;	//图标文件名
	private String color;	//颜色
	private String type;	//快捷类型，快捷url
	private String url;		//主框架url
	private String table_;	//排序规则，数值
	private Boolean show_;	//是否显示
	private Integer model;	//标签类别
	private String code;   //菜单编码
	
	
	public Integer getModel() {
		return model;
	}
	public void setModel(Integer model) {
		this.model = model;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTable_() {
		return table_;
	}
	public void setTable_(String table_) {
		this.table_ = table_;
	}
	public Boolean getShow_() {
		return show_;
	}
	public void setShow_(Boolean show_) {
		this.show_ = show_;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}
