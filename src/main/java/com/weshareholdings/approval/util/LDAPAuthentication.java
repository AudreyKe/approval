package com.weshareholdings.approval.util;

import com.weshareholdings.approval.config.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LDAPAuthentication {
    private Logger logger = LoggerFactory.getLogger(LDAPAuthentication.class);

    private Props props;
    private LdapContext ctx = null;
    private final Control[] connCtls = null;

    private void LDAP_connect() {
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, props.getFactory()); //指定到目录服务的连接工厂
        env.put(Context.PROVIDER_URL, props.getUrl() + props.getBaseDN());
        env.put(Context.SECURITY_AUTHENTICATION, "simple"); // 采用用户名/密码方式验证

        env.put(Context.SECURITY_PRINCIPAL, props.getRoot());   // 管理员
        env.put(Context.SECURITY_CREDENTIALS, props.getPassword());  // 管理员密码

        try {
            ctx = new InitialLdapContext(env, connCtls);
            logger.info("认证成功");

        } catch (javax.naming.AuthenticationException e) {
            logger.error("认证失败");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("认证出错");
            e.printStackTrace();
        }


    }

    private String getUserDN(String uid) {
        String userDN = "";
        LDAP_connect();
        try {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration<SearchResult> en = ctx.search("", "uid=" + uid, constraints);
            if (en == null || !en.hasMoreElements()) {
                logger.error("未找到该用户");
            }
            // maybe more than one element
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                    userDN += "," + props.getBaseDN();
                } else {
                    System.out.println(obj);
                }
            }
        } catch (Exception e) {
            logger.error("查找用户时产生异常");
            e.printStackTrace();
        }

        return userDN;
    }

    public boolean authenricate(String UID, String password, Props props) {
        this.props = props;
        boolean valide = false;
        String userDN = getUserDN(UID);

        try {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            logger.info(userDN + "验证通过");
            valide = true;
        } catch (AuthenticationException e) {
            logger.error(userDN + " 验证失败");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            valide = false;
        } catch (NamingException e) {
            logger.error(userDN + " 验证失败");
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            valide = false;
        }
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }

        }
        return valide;
    }

}
