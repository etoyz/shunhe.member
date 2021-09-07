package com.sh.cloud.config;

//import com.lwk.user.dao.UserInfoDao;

import com.dubbo.user.bean.Permission;
import com.dubbo.user.bean.Role;
import com.dubbo.user.service.PermissionService;
import com.dubbo.user.service.RoleService;
import com.dubbo.user.service.UserService;
import com.dubbo.user.util.GsonUtil;
import com.sft.member.bean.PlatUser;
import com.sft.member.obtain.log.LogService;
import com.sft.member.obtain.user.PlatUserService;
import com.sh.cloud.entity.MongoUser;
import com.sh.cloud.utils.LogUtils;
import com.sh.cloud.utils.PlatUserUtils;
import com.sh.cloud.utils.UtilValidate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import com.lwk.user.entity.SysRole;
//import com.lwk.user.entity.UserInfo;

/**
 * Created by jxh on 2019/2/13.
 */
public class MyShiroCasRealm extends CasRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyShiroCasRealm.class);

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    LogService logService;
    @Resource
    PlatUserService platUserService;
//    @Autowired
//    private MongoUserService service;

    //    @Autowired
//    private UserInfoDao userDao;
//public MyShiroCasRealm() {
//    this.setAuthenticationTokenClass(UsernamePasswordToken.class);
//}
    @PostConstruct
    public void initProperty() {
//      setDefaultRoles("ROLE_USER");
        setCasServerUrlPrefix(ShiroCasConfiguration.casServerUrlPrefix);
        // 客户端回调地址
        setCasService(ShiroCasConfiguration.shiroServerUrlPrefix + ShiroCasConfiguration.casFilterUrlPattern);
    }


    /**
     * 1、CAS认证 ,验证用户身份
     * 2、将用户基本信息设置到会话中(不用了，随时可以获取的)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {

//        CasToken casToken = (CasToken)token;
//        if(token == null) {
//            return null;
//        } else {
//            String ticket = (String) casToken.getCredentials();
//            if (!StringUtils.hasText(ticket)) {
//                return null;
//            } else {
//                TicketValidator ticketValidator = this.ensureTicketValidator();
//
//                try {
//                    Assertion casAssertion = ticketValidator.validate(ticket, this.getCasService());
//                    AttributePrincipal casPrincipal = casAssertion.getPrincipal();
//                    String userId = casPrincipal.getName();
//                    System.out.println("Validate ticket : {} in CAS server : {} to retrieve user : {}" + new Object[]{ticket, this.getCasServerUrlPrefix(), userId});
//                    Map<String, Object> attributes = casPrincipal.getAttributes();
//                    for (String a : attributes.keySet()
//                            ) {
//                        System.out.println(a + "======" + attributes.get(a));
//                    }
//                    casToken.setUserId(userId);
//                    String rememberMeAttributeName = this.getRememberMeAttributeName();
//                    String rememberMeStringValue = (String) attributes.get(rememberMeAttributeName);
//                    boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
//                    if (isRemembered) {
//                        casToken.setRememberMe(true);
//                    }
//
//                    List<Object> principals = CollectionUtils.asList(new Object[]{userId, attributes});
//                    PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, this.getName());
//                    return new SimpleAuthenticationInfo(principalCollection, ticket);
//                } catch (TicketValidationException var14) {
//                    throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", var14);
//                }
//            }
//        }

        //        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        UserInfo userInfo  = (UserInfo)principals.getPrimaryPrincipal();
//        for(SysRole role:userInfo.getRoleList()){
//            authorizationInfo.addRole(role.getRole());
//            for(SysPermission p:role.getPermissions()){
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
//        return authorizationInfo;

        AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
        String account = (String) authc.getPrincipals().getPrimaryPrincipal();
        String userStr = userService.getUser(account);
        MongoUser user = GsonUtil.gson.fromJson(userStr, MongoUser.class);//service.findByName(account);
        //将用户信息存入session中
        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        return authc;
    }

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     *
     * @see :本例中该方法的调用时机为需授权资源被访问时
     * @see :并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @see :如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        //到数据库查是否有此对象
//        UserInfo user=userDao.findByUsername(loginName);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        String userStr = userService.getUser(loginName);
        MongoUser mongoUser = GsonUtil.gson.fromJson(userStr, MongoUser.class);
//        MongoUser mongoUser = service.findByName(loginName);
        if (mongoUser != null) {
            System.out.println(mongoUser);
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Role> roles = roleService.getUserHasRoles(mongoUser.get_id());
            Set<String> roleset = new HashSet<>();
            //用户的角色集合\
            for (Role role : roles) {
                roleset.add(role.getRole());
            }
            info.setRoles(roleset);
//            用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
//            info.addRoles(mongoUser.getRoleset());
            List<Permission> permissions = permissionService.getUserHasPermissions(mongoUser.get_id());
            if (UtilValidate.isNotEmpty(permissions)) {
                for (Permission permission : permissions) {
                    if (UtilValidate.isEmpty(permission)) {
                        continue;
                    }
                    info.addStringPermission(permission.getPermission());
                }
            }

//             或者按下面这样添加
//            添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            添加权限
            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            //用户的角色集合
//            info.setRoles(user.());
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
//            for(SysRole role:user.getRoleList()){
//                info.addRole(role.getRole());
//                for(SysPermission p:role.getPermissions()){
//                    info.addStringPermission(p.getPermission());
//                }
//            }
//            List<Role> roleList=user.getRoleList();
//            for (Role role : roleList) {
//                info.addStringPermissions(role.getPermissionsName());
//            }
            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
//            simpleAuthorInfo.addRole("admin");
            //添加权限
//            simpleAuthorInfo.addStringPermission("admin:manage");
//            logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
            PlatUser user = platUserService.getPlatUser(PlatUserUtils.getCurrentLoginPlatUser());
            logService.addLog(PlatUserUtils.getCurrentLoginPlatUser(),
                    LogUtils.newLogInstance("用户登入 用户名:" + user.name));
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**
     * 授权用户信息
     */
//    public static class Principal implements Serializable {
//
//        private static final long serialVersionUID = 1L;
//
//        private String id; // 编号
//        private String loginName; // 登录名
//        private String name; // 姓名
//        private boolean mobileLogin; // 是否手机登录
//
////		private Map<String, Object> cacheMap;
//
//        public Principal(MongoUser user, boolean mobileLogin) {
//            this.id = user.get_id();
//            this.loginName = user.getLoginname();
//            this.name = user.getUsername();
//            this.mobileLogin = mobileLogin;
//        }
//        public Principal(MongoUser user) {
//            this.id = user.get_id();
//            this.loginName = user.getLoginname();
//            this.name = user.getUsername();
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public String getLoginName() {
//            return loginName;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public boolean isMobileLogin() {
//            return mobileLogin;
//        }
//
////		@JsonIgnore
////		public Map<String, Object> getCacheMap() {
////			if (cacheMap==null){
////				cacheMap = new HashMap<String, Object>();
////			}
////			return cacheMap;
////		}
//
//        @Override
//        public String toString() {
//            return id;
//        }
//
//    }
}
