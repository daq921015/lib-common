DROP TABLE IF EXISTS logback_configuration;
CREATE TABLE logback_configuration
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    deployment_environment VARCHAR(20) NOT NULL COMMENT '部署环境',
    partition_code VARCHAR(20) NOT NULL COMMENT '分区码',
    service_name VARCHAR(20) NOT NULL COMMENT '服务名称',
    content TEXT NOT NULL COMMENT '内容',
    created_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    created_user_id BIGINT NOT NULL COMMENT '创建人id',
    updated_time DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '最后更新时间',
    updated_user_id BIGINT NOT NULL COMMENT '最后更新人id',
    updated_remark VARCHAR(255) COMMENT '最后更新备注',
    deleted_time DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '删除时间，只有当 deleted = 1 时有意义，默认值为1970-01-01 00:00:00',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);

DROP TABLE IF EXISTS application_configuration;
CREATE TABLE application_configuration (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    deployment_environment VARCHAR(20) NOT NULL COMMENT '部署环境',
    partition_code VARCHAR(20) NOT NULL COMMENT '分区码',
    service_name VARCHAR(20) NOT NULL COMMENT '服务名称',
    configuration_key VARCHAR(200) DEFAULT NULL COMMENT '配置key',
    configuration_value VARCHAR(200) DEFAULT NULL COMMENT '配置value',
    created_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    created_user_id BIGINT NOT NULL COMMENT '创建人id',
    updated_time DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '最后更新时间',
    updated_user_id BIGINT NOT NULL COMMENT '最后更新人id',
    updated_remark VARCHAR(255) DEFAULT NULL COMMENT '最后更新备注',
    deleted_time DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '删除时间，只有当 deleted = 1 时有意义，默认值为1970-01-01 00:00:00',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);

DROP TABLE IF EXISTS production_configuration;
CREATE TABLE production_configuration (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    deployment_environment VARCHAR(20) NOT NULL COMMENT '部署环境',
    partition_code VARCHAR(20) NOT NULL COMMENT '分区码',
    service_name VARCHAR(20) NOT NULL COMMENT '服务名称',
    configuration_key VARCHAR(200) DEFAULT NULL COMMENT '配置key',
    configuration_value VARCHAR(200) DEFAULT NULL COMMENT '配置value',
    created_time DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    created_user_id BIGINT NOT NULL COMMENT '创建人id',
    updated_time DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW() COMMENT '最后更新时间',
    updated_user_id BIGINT NOT NULL COMMENT '最后更新人id',
    updated_remark VARCHAR(255) DEFAULT NULL COMMENT '最后更新备注',
    deleted_time DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '删除时间，只有当 deleted = 1 时有意义，默认值为1970-01-01 00:00:00',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除，0-未删除，1-已删除'
);