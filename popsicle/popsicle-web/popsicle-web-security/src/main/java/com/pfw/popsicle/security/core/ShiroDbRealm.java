package com.pfw.popsicle.security.core;

import java.util.List;

//import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pfw.popsicle.common.Encodes;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.enums.UserStatus;
import com.pfw.popsicle.security.service.RoleService;
import com.pfw.popsicle.security.service.UserService;

public class ShiroDbRealm extends AuthorizingRealm {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	/**
	 * 设定自定义验证
	 */ 
//	@PostConstruct
//	public void initConfig() {
//		setCredentialsMatcher(new AllowAllCredentialsMatcher());//设置无需认证，因为sso有认证
//		setCredentialsMatcher(new SsoCredentialsMatcher());
//		setAuthenticationTokenClass(SsoAuthenticationToken.class);//设置自定义token
//	}
	
	/**
	 * 认证信息 :认证回调函数,登录时调用.
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		log.debug("登录认证回调函数");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String userName = token.getUsername();
		if (userName != null && !"".equals(userName)) {
			User user = userService.getUserByLoginName(userName);
			if (user != null) {
				if (UserStatus.natural.getType()!=user.getStatus()) {
					throw new DisabledAccountException();
				}
				
				List<Role> rList = null;
				if(user.getIsAdmin()!=null&&user.getIsAdmin().booleanValue()){
					rList = roleService.loadAll();
				}else{
					rList = roleService.findRolesByUserId(user.getId());
				}
				
				byte[] salt = Encodes.decodeHex(user.getSalt());
				AuthenticationInfo info = new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
						user.getLoginName(), user.getName(),user.getIsAdmin(),rList),
						user.getPassword(), ByteSource.Util.bytes(salt),
						getName());
				return info; 
			}
		}
		return null;
	}

	/**
	 * 授权:授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		log.debug("授权查询回调函数");
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
		if (shiroUser != null) {
			List<Role> rList = shiroUser.getRoles();
			if( rList!= null&&rList.size()>0){
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				for (Role r : rList) {
					info.addRole(r.getName());
//					info.addStringPermissions(each.getPermissionList());
				}
				return info;
			}
		}
		return null;
	}

	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
//	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				UserService.HASH_ALGORITHM);
		matcher.setHashIterations(UserService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/** * 更新用户授权信息缓存. */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
	public void clearAllCachedAuthorizationInfo(){
		Cache<Object,AuthenticationInfo> cache = this.getAuthenticationCache();
		if(cache!=null){
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}
