package com.yby.sys.utils;

import com.yby.sys.domain.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * 用户角色 和 权限  管理  防止一直访问授权
 * @author TeouBle
 */
public class ActiveUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SysUser user;
	
	private List<String> roles;
	
	private List<String> permissions;
	
	public ActiveUser() {
		// TODO Auto-generated constructor stub
	}

	public ActiveUser(SysUser user, List<String> roles, List<String> permissions) {
		super();
		this.user = user;
		this.roles = roles;
		this.permissions = permissions;
	}



	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
}
