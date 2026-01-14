-- 1. 组件类型表（field_component）- 表单组件基础库
DROP TABLE IF EXISTS `field_component`;
CREATE TABLE `field_component`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '组件唯一标识',
    `component_code` varchar(64)     NOT NULL COMMENT '组件编码（如input/textarea/radio）',
    `component_name` varchar(64)     NOT NULL COMMENT '组件名称（如单行文本/多行文本/单选框）',
    `component_type` varchar(32)     NOT NULL COMMENT '组件分类（base:基础字段/advanced:高级字段）',
    `sort`           int             NOT NULL DEFAULT 0 COMMENT '排序权重（数值越小越靠前）',
    `icon`           varchar(64)              DEFAULT '' COMMENT '组件图标',
    `description`    varchar(255)             DEFAULT '' COMMENT '组件描述',
    `status`         tinyint         NOT NULL DEFAULT 1 COMMENT '状态（1:启用/0:禁用）',
    `create_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_component_code` (`component_code`),
    KEY `idx_component_type` (`component_type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组件类型表';

-- 插入组件数据
INSERT INTO `field_component` (`component_code`, `component_name`, `component_type`,
                               `sort`, `icon`, `description`, `status`)
VALUES
-- 单行文本组件
('input', '单行文本', 'base', 1, 'TextT24Filled', '普通单行文本输入框，适用于姓名、手机号等短文本输入', 1),
-- 多行文本组件
('textarea', '多行文本', 'base', 2, 'TextSmallCaps', '多行文本输入框，适用于备注、描述等长文本输入', 1),
('number', '数字输入框', 'base', 3, 'el-icon-number', '仅允许输入数字的输入框，支持整数/小数配置', 1),
('date', '日期选择器', 'base', 4, 'el-icon-date', '日期选择控件，支持年月日/年月日时分秒格式', 1),
('radio', '单选框', 'base', 5, 'el-icon-radio', '单选选项组，仅可选择一个选项', 1),
('checkbox', '多选框', 'base', 6, 'el-icon-checkbox', '多选选项组，可选择多个选项', 1),
('select', '下拉单选', 'base', 7, 'el-icon-select', '下拉选择框，仅可选择一个选项', 1),
('select-multiple', '下拉多选', 'base', 8, 'el-icon-select', '下拉选择框，可选择多个选项', 1),
('divider', '分割线', 'base', 9, 'el-icon-divider', '表单分区分割线，无数据输入功能', 1),
('label', '标签', 'base', 10, 'el-icon-label', '纯文本标签，用于表单说明，无输入功能', 1),
('cascader', '级联选择器', 'base', 11, 'el-icon-cascader', '级联下拉选择，适用于省市区等层级选择', 1),

-- 高级字段（component_type = advanced）
('password', '密码输入框', 'advanced', 1, 'el-icon-lock', '密码输入框，输入内容加密显示', 1),
('upload-image', '图片上传', 'advanced', 2, 'el-icon-picture', '单张/多张图片上传控件', 1),
('upload-file', '附件上传', 'advanced', 3, 'el-icon-upload', '文件上传控件，支持多种格式', 1),
('mobile', '手机号输入框', 'advanced', 4, 'el-icon-phone', '手机号专用输入框，自带格式校验', 1),
('id-card', '身份证输入框', 'advanced', 5, 'el-icon-idcard', '身份证号专用输入框，自带格式校验', 1),
('address', '地址选择器', 'advanced', 6, 'el-icon-location', '省市区+详细地址组合输入控件', 1),
('link', '链接输入框', 'advanced', 7, 'el-icon-link', 'URL链接输入框，自带格式校验', 1),
('calculator', '计算字段', 'advanced', 8, 'el-icon-calculator', '基于其他字段自动计算的字段，如总价=单价*数量', 1);

-- 2. 字段配置元数据表（field_config_schema）- 右侧配置面板渲染依据
DROP TABLE IF EXISTS `field_config_schema`;
CREATE TABLE `field_config_schema`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '配置项Schema唯一标识',
    `component_code` varchar(64)     NOT NULL COMMENT '关联组件编码',
    `config_key`     varchar(64)     NOT NULL COMMENT '配置项key（如showLabel/placeholder/rules）',
    `config_label`   varchar(64)     NOT NULL COMMENT '配置项名称（如显示标签/占位提示/校验规则）',
    `config_type`    varchar(32)     NOT NULL COMMENT '配置项编辑控件类型（switch/input/select/textarea）',
    `config_options` json                     DEFAULT NULL COMMENT '配置项可选值（下拉框等需枚举值时使用）',
    `default_value`  json                     DEFAULT NULL COMMENT '配置项默认值',
    `is_required`    tinyint         NOT NULL DEFAULT 0 COMMENT '是否必填配置项（1:是/0:否）',
    `sort`           int             NOT NULL DEFAULT 0 COMMENT '配置项排序',
    `description`    varchar(4096)            DEFAULT '' COMMENT '配置项说明',
    `create_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_component_config` (`component_code`, `config_key`),
    KEY `idx_component_code` (`component_code`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字段配置元数据表';

-- 插入input组件的核心配置项
INSERT INTO `field_config_schema` (`component_code`, `config_key`, `config_label`, `config_type`,
                                   `config_options`, `default_value`, `is_required`, `sort`, `description`)
VALUES
    -- 单行文本
    ('input', 'title', '字段标题', 'input', NULL, '""', 1, 1, '表单中显示的字段名称，如"姓名"'),
    ('input', 'showLabel', '显示标签', 'switch', NULL, 'true', 1, 2, '是否显示字段标题标签'),
    ('input', 'placeholder', '占位提示语', 'input', NULL, '""', 0, 3, '输入框为空时显示的提示文字'),
    ('input', 'tips', '显示说明提示', 'switch', NULL, 'true', 0, 4, '是否显示字段的描述说明'),
    ('input', 'description', '字段描述', 'textarea', NULL, '""', 0, 5, '字段的详细说明，会显示在提示中'),
    ('input', 'readable', '是否只读', 'switch', NULL, 'true', 0, 6, '字段是否仅可读取，不可编辑'),
    ('input', 'editable', '是否可编辑', 'switch', NULL, 'true', 0, 7, '字段是否允许编辑'),
    ('input', 'fieldWidth', '字段宽度', 'segment',
     '[
       {
         "label": "1/4",
         "value": 0.25
       },
       {
         "label": "1/3",
         "value": 0.33
       },
       {
         "label": "1/2",
         "value": 0.5
       },
       {
         "label": "2/3",
         "value": 0.67
       },
       {
         "label": "3/4",
         "value": 0.75
       },
       {
         "label": "整行",
         "value": 1.0
       }
     ]',
     '"1.0"', 0, 99, '字段占一行的比例（0-1）'),
    ('input', 'prefixIcon', '前缀图标', 'select',
     '[
       {
         "value": "User"
       },
       {
         "value": "IosPhonePortrait"
       }
     ]',
     NULL, 0, 9, ''),
    ('input', 'suffixIcon', '后缀图标', 'select',
     '[
       {
         "value": "Search"
       }
     ]',
     NULL, 0, 10, ''),
    ('input', 'rules', '校验规则', 'json', NULL,
     NULL,
     0, 11, '字段的校验规则，包含必填、长度、正则等，示例：{
        "required": false,
        "message": "请输入姓名",
        "trigger": "blur",
        "max": 100,
        "maxMessage": "姓名长度不能超过100个字符",
        "min": 1,
        "minMessage": "姓名长度不能少于1个字符",
        "pattern": "^[\\u4e00-\\u9fa5a-zA-Z·\\s]+$",
        "patternMessage": "姓名仅支持中文、字母、点和空格，请勿输入特殊字符"
    }'),
    ('input', 'defaultValue', '默认值', 'input', NULL, '""', 0, 12, '字段初始化时的默认值');

INSERT INTO `field_config_schema` (`component_code`,
                                   `config_key`,
                                   `config_label`,
                                   `config_type`,
                                   `config_options`,
                                   `default_value`,
                                   `is_required`,
                                   `sort`,
                                   `description`)
VALUES ('textarea', 'title', '字段标题', 'input', NULL, '""', 1, 1, '表单中显示的字段名称，如"备注"'),
       ('textarea', 'showLabel', '显示标签', 'switch', NULL, 'true', 1, 2, '是否显示字段标题标签'),
       ('textarea', 'placeholder', '占位提示语', 'input', NULL, '""', 0, 3, '输入框为空时显示的提示文字'),
       ('textarea', 'tips', '显示说明提示', 'switch', NULL, 'true', 0, 4, '是否显示字段的描述说明'),
       ('textarea', 'description', '字段描述', 'textarea', NULL, '""', 0, 5, '字段的详细说明，会显示在提示中'),
       ('textarea', 'readable', '是否只读', 'switch', NULL, 'true', 0, 6, '字段是否仅可读取，不可编辑'),
       ('textarea', 'editable', '是否可编辑', 'switch', NULL, 'true', 0, 7, '字段是否允许编辑'),

       ('textarea', 'fieldWidth', '字段宽度', 'segment',
        '[
          {
            "label": "1/4",
            "value": 0.25
          },
          {
            "label": "1/3",
            "value": 0.33
          },
          {
            "label": "1/2",
            "value": 0.5
          },
          {
            "label": "2/3",
            "value": 0.67
          },
          {
            "label": "3/4",
            "value": 0.75
          },
          {
            "label": "整行",
            "value": 1.0
          }
        ]',
        '"1.0"', 0, 99, '字段占一行的比例（0-1）'),

       ('textarea', 'rows', '默认行数', 'input', NULL, '"4"', 0, 9, '文本域默认显示的行数'),
       ('textarea', 'autosize', '自适应高度', 'json', NULL,
        '{
          "minRows": 4,
          "maxRows": 8
        }', 0, 10, '文本域自适应高度配置，minRows为最小行数，maxRows为最大行数，示例：{
   "minRows": 4,
   "maxRows": 8
 }'),

-- ========== 校验规则 ==========
       ('textarea', 'rules', '校验规则', 'json', NULL,
        NULL, 0, 11, '字段的校验规则，包含必填、长度、正则等，示例：{
   "required": false,
   "trigger": "blur",
   "max": 200,
   "maxMessage": "备注内容不能超过200个字符",
   "min": 10,
   "minMessage": "备注内容不能少于10个字符",
   "pattern": "^[\\u4e00-\\u9fa5a-zA-Z0-9，。！？；：、\\s\\r\\n]+$",
   "patternMessage": "备注仅支持中文、字母、数字、常用标点和换行，请勿输入特殊字符"
 }'),

       ('textarea', 'defaultValue', '默认值', 'input', NULL, '""', 0, 12, '字段初始化时的默认值');

-- 3. 表单元数据表（form_schema）- 表单基础信息+字段配置
DROP TABLE IF EXISTS `form_schema`;
CREATE TABLE `form_schema`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表单Schema唯一标识',
    `form_id`        varchar(64)     NOT NULL COMMENT '表单业务标识（如employee_onboard_001）',
    `form_name`      varchar(128)    NOT NULL COMMENT '表单名称',
    `schema_version` varchar(32)     NOT NULL COMMENT 'Schema版本（如v1/v2）',
    `form_desc`      varchar(512)             DEFAULT '' COMMENT '表单描述',
    `fields`         json            NOT NULL COMMENT '表单字段配置集合（核心Schema）',
    `status`         tinyint         NOT NULL DEFAULT 1 COMMENT '表单状态（1:启用/0:禁用）',
    `create_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_form_version` (`form_id`, `schema_version`),
    KEY `idx_form_id` (`form_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='表单元数据表';

-- 4. 表单业务数据表（form_data）- 用户提交的表单数据
DROP TABLE IF EXISTS `form_data`;
CREATE TABLE `form_data`
(
    `id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '表单数据唯一标识',
    `form_id`        varchar(64)     NOT NULL COMMENT '关联表单业务标识',
    `schema_version` varchar(32)     NOT NULL COMMENT '提交时的Schema版本',
    `form_data_json` json            NOT NULL COMMENT '表单业务数据JSON',
    `submit_time`    datetime        NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
    `status`         tinyint         NOT NULL DEFAULT 1 COMMENT '数据状态（1:有效/0:作废）',
    `ext_info`       json                     DEFAULT NULL COMMENT '扩展信息（如审批状态/备注等）',
    PRIMARY KEY (`id`),
    KEY `idx_form_id_version` (`form_id`, `schema_version`),
    KEY `idx_submit_time` (`submit_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='表单业务数据表';