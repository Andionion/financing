-- 创建基金净值数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS fund_net_value
(
    -- 主键，自增，用于唯一标识每条基金净值记录
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    -- 基金代码，长度为6个字符的字符串类型，用于唯一标识一只基金
    fund_code             VARCHAR(6) COMMENT '基金代码',
    -- 净值日期，长度为50个字符的字符串类型，记录该基金净值数据对应的日期
    net_value_date        VARCHAR(50) COMMENT '净值日期',
    -- 单位净值，双精度浮点数类型，反映每份基金的实际价值
    unit_net_value        DOUBLE COMMENT '单位净值',
    -- 累计净值，双精度浮点数类型，在单位净值基础上累加了基金成立以来的累计收益等情况
    accumulated_net_value DOUBLE COMMENT '累计净值',
    -- 日增长率，双精度浮点数类型，体现基金在当日相对于前一日净值的增长比例
    daily_growth_rate     DOUBLE COMMENT '日增长率',
    -- 申购状态，长度为50个字符的字符串类型，用于表示当前基金是否允许申购等情况
    subscription_status   VARCHAR(50) COMMENT '申购状态',
    -- 赎回状态，长度为50个字符的字符串类型，用于表示当前基金是否允许赎回等情况
    redemption_status     VARCHAR(50) COMMENT '赎回状态'
);

-- 创建交易日历史数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS trade_date_hist
(
    -- 主键，自增，用于唯一标识每条交易日历史记录
    id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    -- 交易日，长度为50个字符的字符串类型，用于记录具体的交易日日期
    trade_date VARCHAR(50) COMMENT '交易日'
);

-- 创建基金交易数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS fund_investment
(
    -- 主键，自增，用于唯一标识每条基金交易记录
    id            BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键，自增',
    -- 基金代码，长度为6个字符的字符串类型
    fund_code     VARCHAR(6) COMMENT '基金代码',
    -- 投资日期，长度为50个字符的字符串类型，用于记录购买基金的具体日期
    purchase_date VARCHAR(50) COMMENT '投资日期',
    -- 购买金额，双精度浮点数类型，用于记录购买基金所花费的金额
    amount        DOUBLE COMMENT '购买金额',
    -- 购买份额，双精度浮点数类型，用于记录购买基金所获得的份额
    share         DOUBLE COMMENT '购买份额'
);