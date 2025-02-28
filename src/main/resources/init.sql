-- 创建基金净值数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS fund_net_value
(
    id                    BIGINT PRIMARY KEY COMMENT '主键',
    fund_code             VARCHAR(6) COMMENT '基金代码',
    fund_name             VARCHAR(50) COMMENT '基金名称',
    net_value_date        DATE COMMENT '净值日期',
    unit_net_value        DOUBLE COMMENT '单位净值',
    accumulated_net_value DOUBLE COMMENT '累计净值',
    daily_growth_rate     DOUBLE COMMENT '日增长率',
    subscription_status   VARCHAR(50) COMMENT '申购状态',
    redemption_status     VARCHAR(50) COMMENT '赎回状态',
    create_time           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time           timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

-- 创建交易日历史数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS trade_date_hist
(
    id          BIGINT PRIMARY KEY COMMENT '主键',
    trade_day   DATE COMMENT '交易日',
    create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

-- 创建基金交易数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS fund_trade
(
    id          BIGINT PRIMARY KEY COMMENT '主键',
    fund_code   VARCHAR(6) COMMENT '基金代码',
    fund_name   VARCHAR(50) COMMENT '基金名称',
    trade_date  DATE COMMENT '交易日期',
    trade_type  VARCHAR(50) COMMENT '交易类型，purchase-申购，redeem-赎回',
    amount      DOUBLE COMMENT '购买金额',
    share       DOUBLE COMMENT '购买份额',
    belong      VARCHAR(50) COMMENT '交易所属方',
    create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);

-- 创建黄金交易数据表，如果表不存在则创建
CREATE TABLE if not exists gold_trade
(
    id           BIGINT PRIMARY KEY COMMENT '唯一交易标识',
    trade_date   DATE                        NOT NULL COMMENT '交易日期',
    amount       DECIMAL(12, 2)              NOT NULL COMMENT '交易金额（单位：人民币元，精确到分）',
    unit_price   DECIMAL(10, 4)              NOT NULL COMMENT '单价（单位：人民币元/单位，最多保留四位小数）',
    handling_fee DECIMAL(6, 2)               NOT NULL COMMENT '手续费金额（单位：人民币元，精确到分）',
    weight       DECIMAL(6, 4)               NOT NULL COMMENT '重量（单位：g，保留四位小数）',
    trade_type   ENUM ('PURCHASE', 'REDEEM') NOT NULL COMMENT '交易类型（purchase-申购，redeem-赎回）',
    gold_type    ENUM ('PAPER', 'PHYSICAL')  NOT NULL COMMENT '黄金类型（paper-纸面金，physical-实体金）',
    create_time  timestamp                   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time  timestamp                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='黄金交易记录表';