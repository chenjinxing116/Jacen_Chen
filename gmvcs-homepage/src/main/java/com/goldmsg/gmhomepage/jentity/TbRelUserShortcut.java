package com.goldmsg.gmhomepage.jentity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the tb_rel_user_shortcut database table.
 * 
 */
@Entity
@Table(name="tb_rel_user_shortcut")
@NamedQuery(name="TbRelUserShortcut.findAll", query="SELECT t FROM TbRelUserShortcut t")
public class TbRelUserShortcut implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TbRelUserShortcutPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;

	public TbRelUserShortcut() {
	}

	public TbRelUserShortcutPK getId() {
		return this.id;
	}

	public void setId(TbRelUserShortcutPK id) {
		this.id = id;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}