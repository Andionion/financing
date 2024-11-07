-- 基金净值数据
CREATE TABLE IF NOT EXISTS fund_net_value
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY, -- 主键，自增
    fund_code             VARCHAR(6),                        -- 基金代码
    net_value_date        VARCHAR(50),                       -- 净值日期
    unit_net_value        DOUBLE,                            -- 单位净值
    accumulated_net_value DOUBLE,                            -- 累计净值
    daily_growth_rate     DOUBLE,                            -- 日增长率
    subscription_status   VARCHAR(50),                       -- 申购状态
    redemption_status     VARCHAR(50)                        -- 赎回状态
);

-- 交易日历史数据
CREATE TABLE IF NOT EXISTS trade_date_hist
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY, -- 主键，自增
    trade_date VARCHAR(50)                        -- 交易日
);