DROP TABLE IF EXISTS system_partition;
CREATE TABLE system_partition
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    deployment_environment VARCHAR(20) NOT NULL COMMENT '部署环境',
    partition_code VARCHAR(20) NOT NULL COMMENT '分区码',
    `type` TINYINT NOT NULL COMMENT '服务类型，1-公共服务，2-分区服务',
    service_name VARCHAR(20) NOT NULL COMMENT '服务名称',
    service_domain VARCHAR(200) NOT NULL COMMENT '服务地址',
    outside_service_domain VARCHAR(200) NOT NULL COMMENT '外网服务地址',
    create_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    create_user_id BIGINT NOT NULL COMMENT '创建用户ID',
    last_update_time DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '最后更新时间',
    last_update_user_id BIGINT NOT NULL COMMENT '最后更新用户id',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);

DROP TABLE IF EXISTS configuration;
CREATE TABLE configuration (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    deployment_environment VARCHAR(20) NOT NULL COMMENT '部署环境',
    partition_code VARCHAR(20) NOT NULL COMMENT '分区码',
    service_name VARCHAR(20) NOT NULL COMMENT '服务名称',
    configuration_key VARCHAR(200) COMMENT '配置key',
    configuration_value VARCHAR(200) COMMENT '配置value',
    create_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    create_user_id BIGINT NOT NULL COMMENT '创建人id',
    last_update_time DATETIME DEFAULT NOW() ON UPDATE NOW() COMMENT '最后更新时间',
    last_update_user_id BIGINT NOT NULL COMMENT '最后更新人id',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);

DROP TABLE IF EXISTS tenant;
CREATE TABLE tenant
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `code` VARCHAR(20) NOT NULL COMMENT '商户编码',
    `name` VARCHAR(20) NOT NULL COMMENT '商户名称',
    mobile VARCHAR(20) NOT NULL COMMENT '联系电话',
    email VARCHAR(20) NOT NULL COMMENT '邮箱',
    linkman VARCHAR(20) NOT NULL COMMENT '联系人',
    business VARCHAR(10) NOT NULL COMMENT '业态',
    province_code VARCHAR(10) NOT NULL COMMENT '省编码',
    city_code VARCHAR(10) NOT NULL COMMENT '市编码',
    district_code VARCHAR(10) NOT NULL COMMENT '区编码',
    partition_code VARCHAR(20) NOT NULL COMMENT '分区码',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    create_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    create_user_id BIGINT NOT NULL COMMENT '创建人id',
    last_update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE NOW() COMMENT '最后更新时间',
    last_update_user_id BIGINT NOT NULL COMMENT '最后更新人id',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`
(
    id BIGINT NOT NULL  AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `name` VARCHAR(20) NOT NULL COMMENT '员工姓名',
    mobile VARCHAR(20) NOT NULL COMMENT '手机号码',
    email VARCHAR(20) NOT NULL COMMENT '邮箱',
    `login_name` VARCHAR(20) NOT NULL COMMENT '登录名',
    user_type TINYINT NOT NULL COMMENT '员工类型，1-商户主账号，2-商户员工',
    `password` VARCHAR(50) NOT NULL COMMENT '登录密码',
    tenant_id BIGINT COMMENT '商户ID',
    account_non_expired TINYINT NOT NULL DEFAULT 1 COMMENT '账户是否没有过期，1-没有过期，0-已经过期',
    account_non_locked TINYINT NOT NULL DEFAULT 1 COMMENT '账户是否没有锁定，1-没有锁定，0-已经锁定',
    credentials_non_expired TINYINT NOT NULL DEFAULT 1 COMMENT '账户凭证是否没有过期，1-没有过期，0-已经过期',
    enabled TINYINT NOT NULL DEFAULT 1 COMMENT '账户是否启用，1-启用，0-禁用',
    create_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    create_user_id BIGINT NOT NULL COMMENT '创建人id',
    last_update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE NOW() COMMENT '最后更新时间',
    last_update_user_id BIGINT NOT NULL COMMENT '最后更新人id',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);

DROP TABLE IF EXISTS sequence;
CREATE TABLE sequence
(
    name VARCHAR(50) PRIMARY KEY NOT NULL COMMENT '序列名称',
    current_value INT(11) unsigned NOT NULL COMMENT '当前值',
    increment INT(11) unsigned DEFAULT '1' NOT NULL COMMENT '每次增长的值'
);

DROP FUNCTION IF EXISTS current_value;
CREATE FUNCTION current_value(seq_name VARCHAR(50)) RETURNS INT(11)
BEGIN
    DECLARE value int;
    SET value = 0;
    SELECT current_value INTO value FROM sequence WHERE name = seq_name;
    IF value = 0 THEN
        SET value = 1;
        INSERT INTO sequence(name, current_value) VALUES(seq_name, value);
    END if;
    return value;
END;

DROP FUNCTION IF EXISTS next_value;
CREATE FUNCTION next_value(seq_name VARCHAR(50)) RETURNS INT(11)
BEGIN
    UPDATE sequence SET current_value = current_value + increment where name = seq_name;
    return current_value(seq_name);
END ;

#微信支付配置表
CREATE TABLE wei_xin_pay_configuration
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    tenant_id BIGINT NOT NULL COMMENT '商户id',
    branch_id BIGINT NOT NULL COMMENT '门店id',
    wei_xin_pay_app_id VARCHAR(50) NOT NULL COMMENT 'app id',
    wei_xin_pay_mch_id VARCHAR(50) NOT NULL,
    wei_xin_pay_key VARCHAR(50) NOT NULL,
    wei_xin_pay_sub_app_id VARCHAR(50),
    wei_xin_pay_sub_mch_id VARCHAR(50),
    create_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    create_user_id BIGINT NOT NULL COMMENT '创建用户id',
    last_update_time DATETIME NOT NULL DEFAULT NOW() COMMENT '最后更新时间',
    last_update_user_id BIGINT NOT NULL COMMENT '最后更新user id',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-为删除，1-已删除'
);