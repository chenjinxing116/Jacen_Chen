package com.goldmsg.gmhomepage.jentity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tb_rel_user_shortcut database table.
 * 
 */
@Embeddable
public class TbRelUserShortcutPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="account_id")
	private int accountId;

	@Column(name="item_id")
	private int itemId;

	public TbRelUserShortcutPK() {
	}
	public int getAccountId() {
		return this.accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public int getItemId() {
		return this.itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TbRelUserShortcutPK)) {
			return false;
		}
		TbRelUserShortcutPK castOther = (TbRelUserShortcutPK)other;
		return 
			(this.accountId == castOther.accountId)
			&& (this.itemId == castOther.itemId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.accountId;
		hash = hash * prime + this.itemId;
		
		return hash;
	}
}