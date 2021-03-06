package build.dream.common.saas.domains;

import build.dream.common.basic.BasicDomain;
import build.dream.common.constants.Constants;

import java.math.BigInteger;
import java.util.Date;

public class SystemUser extends BasicDomain {
    public static final String TABLE_NAME = "system_user";
    /**
     * 员工姓名
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 邮箱
     */
    private String email = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 登录名
     */
    private String loginName = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 员工类型，1-商户主账号，2-商户员工，3-代理商
     */
    private Integer userType;
    /**
     * 密码
     */
    private String password;
    /**
     * 微信公众平台open id
     */
    private String weiXinPublicPlatformOpenId = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 微信开放平台open id
     */
    private String weiXinOpenPlatformOpenId = Constants.VARCHAR_DEFAULT_VALUE;
    /**
     * 商户ID
     */
    private BigInteger tenantId = Constants.BIGINT_DEFAULT_VALUE;
    /**
     * 代理商ID
     */
    private BigInteger agentId = Constants.BIGINT_DEFAULT_VALUE;
    /**
     * 账户是否没有过期，1-没有过期，0-已经过期
     */
    private boolean accountNonExpired;
    /**
     * 账户是否没有锁定，1-没有锁定，0-已经锁定
     */
    private boolean accountNonLocked;
    /**
     * 账户凭证是否没有过期，1-没有过期，0-已经过期
     */
    private boolean credentialsNonExpired;
    /**
     * 账户是否启用，1-启用，0-禁用
     */
    private boolean enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeiXinPublicPlatformOpenId() {
        return weiXinPublicPlatformOpenId;
    }

    public void setWeiXinPublicPlatformOpenId(String weiXinPublicPlatformOpenId) {
        this.weiXinPublicPlatformOpenId = weiXinPublicPlatformOpenId;
    }

    public String getWeiXinOpenPlatformOpenId() {
        return weiXinOpenPlatformOpenId;
    }

    public void setWeiXinOpenPlatformOpenId(String weiXinOpenPlatformOpenId) {
        this.weiXinOpenPlatformOpenId = weiXinOpenPlatformOpenId;
    }

    public BigInteger getTenantId() {
        return tenantId;
    }

    public void setTenantId(BigInteger tenantId) {
        this.tenantId = tenantId;
    }

    public BigInteger getAgentId() {
        return agentId;
    }

    public void setAgentId(BigInteger agentId) {
        this.agentId = agentId;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static class Builder {
        private final SystemUser instance = new SystemUser();

        public Builder name(String name) {
            instance.setName(name);
            return this;
        }

        public Builder mobile(String mobile) {
            instance.setMobile(mobile);
            return this;
        }

        public Builder email(String email) {
            instance.setEmail(email);
            return this;
        }

        public Builder loginName(String loginName) {
            instance.setLoginName(loginName);
            return this;
        }

        public Builder userType(Integer userType) {
            instance.setUserType(userType);
            return this;
        }

        public Builder password(String password) {
            instance.setPassword(password);
            return this;
        }

        public Builder weiXinPublicPlatformOpenId(String weiXinPublicPlatformOpenId) {
            instance.setWeiXinPublicPlatformOpenId(weiXinPublicPlatformOpenId);
            return this;
        }

        public Builder weiXinOpenPlatformOpenId(String weiXinOpenPlatformOpenId) {
            instance.setWeiXinOpenPlatformOpenId(weiXinOpenPlatformOpenId);
            return this;
        }

        public Builder tenantId(BigInteger tenantId) {
            instance.setTenantId(tenantId);
            return this;
        }

        public Builder agentId(BigInteger agentId) {
            instance.setAgentId(agentId);
            return this;
        }

        public Builder accountNonExpired(boolean accountNonExpired) {
            instance.setAccountNonExpired(accountNonExpired);
            return this;
        }

        public Builder accountNonLocked(boolean accountNonLocked) {
            instance.setAccountNonLocked(accountNonLocked);
            return this;
        }

        public Builder credentialsNonExpired(boolean credentialsNonExpired) {
            instance.setCredentialsNonExpired(credentialsNonExpired);
            return this;
        }

        public Builder enabled(boolean enabled) {
            instance.setEnabled(enabled);
            return this;
        }

        public Builder id(BigInteger id) {
            instance.setId(id);
            return this;
        }

        public Builder createdTime(Date createdTime) {
            instance.setCreatedTime(createdTime);
            return this;
        }

        public Builder createdUserId(BigInteger createdUserId) {
            instance.setCreatedUserId(createdUserId);
            return this;
        }

        public Builder updatedTime(Date updatedTime) {
            instance.setUpdatedTime(updatedTime);
            return this;
        }

        public Builder updatedUserId(BigInteger updatedUserId) {
            instance.setUpdatedUserId(updatedUserId);
            return this;
        }

        public Builder updatedRemark(String updatedRemark) {
            instance.setUpdatedRemark(updatedRemark);
            return this;
        }

        public Builder deletedTime(Date deletedTime) {
            instance.setDeletedTime(deletedTime);
            return this;
        }

        public Builder deleted(boolean deleted) {
            instance.setDeleted(deleted);
            return this;
        }

        public SystemUser build() {
            return instance;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class ColumnName extends BasicDomain.ColumnName {
        public static final String NAME = "name";
        public static final String MOBILE = "mobile";
        public static final String EMAIL = "email";
        public static final String LOGIN_NAME = "login_name";
        public static final String USER_TYPE = "user_type";
        public static final String PASSWORD = "password";
        public static final String WEI_XIN_PUBLIC_PLATFORM_OPEN_ID = "wei_xin_public_platform_open_id";
        public static final String WEI_XIN_OPEN_PLATFORM_OPEN_ID = "wei_xin_open_platform_open_id";
        public static final String TENANT_ID = "tenant_id";
        public static final String AGENT_ID = "agent_id";
        public static final String ACCOUNT_NON_EXPIRED = "account_non_expired";
        public static final String ACCOUNT_NON_LOCKED = "account_non_locked";
        public static final String CREDENTIALS_NON_EXPIRED = "credentials_non_expired";
        public static final String ENABLED = "enabled";
    }

    public static final class FieldName extends BasicDomain.FieldName {
        public static final String NAME = "name";
        public static final String MOBILE = "mobile";
        public static final String EMAIL = "email";
        public static final String LOGIN_NAME = "loginName";
        public static final String USER_TYPE = "userType";
        public static final String PASSWORD = "password";
        public static final String WEI_XIN_PUBLIC_PLATFORM_OPEN_ID = "weiXinPublicPlatformOpenId";
        public static final String WEI_XIN_OPEN_PLATFORM_OPEN_ID = "weiXinOpenPlatformOpenId";
        public static final String TENANT_ID = "tenantId";
        public static final String AGENT_ID = "agentId";
        public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
        public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";
        public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
        public static final String ENABLED = "enabled";
    }
}
