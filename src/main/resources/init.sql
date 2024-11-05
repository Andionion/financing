CREATE TABLE fund_net_value
(
    id                    INT AUTO_INCREMENT PRIMARY KEY, -- 主键，自增
    fund_code             VARCHAR(6),                     -- 基金代码
    net_value_date        VARCHAR(50),                    -- 净值日期
    unit_net_value        DOUBLE,                         -- 单位净值
    accumulated_net_value DOUBLE,                         -- 累计净值
    daily_growth_rate     DOUBLE,                         -- 日增长率
    subscription_status   VARCHAR(50),                    -- 申购状态
    redemption_status     VARCHAR(50)                     -- 赎回状态
);