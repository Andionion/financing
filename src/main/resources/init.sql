-- 创建基金净值数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS fund_net_value
(
    -- 主键，用于唯一标识每条基金净值记录
    id                    BIGINT PRIMARY KEY COMMENT '主键',
    -- 基金代码，长度为6个字符的字符串类型，用于唯一标识一只基金
    fund_code             VARCHAR(6) COMMENT '基金代码',
    -- 基金名称
    fund_name             VARCHAR(50) COMMENT '基金名称',
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
    -- 主键，用于唯一标识每条交易日历史记录
    id         BIGINT PRIMARY KEY COMMENT '主键',
    -- 交易日，长度为50个字符的字符串类型，用于记录具体的交易日日期
    trade_date VARCHAR(50) COMMENT '交易日'
);

-- 创建基金交易数据表，如果表不存在则创建
CREATE TABLE IF NOT EXISTS fund_trade
(
    -- 主键，用于唯一标识每条基金交易记录
    id         BIGINT PRIMARY KEY COMMENT '主键',
    -- 基金代码，长度为6个字符的字符串类型
    fund_code  VARCHAR(6) COMMENT '基金代码',
    -- 基金名称
    fund_name  VARCHAR(50) COMMENT '基金名称',
    -- 交易日期，长度为50个字符的字符串类型，用于记录基金交易的具体日期
    trade_date VARCHAR(50) COMMENT '交易日期',
    -- 交易类型，长度为50个字符的字符串类型，用于记录交易的类型
    trade_type VARCHAR(50) COMMENT '交易类型，purchase-申购，redeem-赎回',
    -- 交易金额，双精度浮点数类型，用于记录基金交易所花费的金额
    amount     DOUBLE COMMENT '购买金额',
    -- 购买份额，双精度浮点数类型，用于记录购买基金所获得的份额
    share      DOUBLE COMMENT '购买份额',
    -- 交易所属方，用于记录交易所属方
    belong     VARCHAR(50) COMMENT '交易所属方'
);

-- 创建黄金交易数据表，如果表不存在则创建
CREATE TABLE if not exists gold_trade
(
    id           BIGINT PRIMARY KEY COMMENT '唯一交易标识',
    trade_time   DATETIME                    NOT NULL COMMENT '交易时间（由业务侧显式填写）',
    amount       DECIMAL(12, 2)              NOT NULL COMMENT '交易金额（单位：人民币元，精确到分）',
    unit_price   DECIMAL(10, 4)              NOT NULL COMMENT '单价（单位：人民币元/单位，最多保留四位小数）',
    handling_fee DECIMAL(6, 2)               NOT NULL COMMENT '手续费金额（单位：人民币元，精确到分）',
    weight       DECIMAL(6, 4)               NOT NULL COMMENT '重量（单位：g，保留四位小数）',
    trade_type   ENUM ('PURCHASE', 'REDEEM') NOT NULL COMMENT '交易类型（purchase-申购，redeem-赎回）',
    gold_type    ENUM ('PAPER', 'PHYSICAL')  NOT NULL COMMENT '黄金类型（paper-纸面金，physical-实体金）'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='黄金交易记录表';

INSERT INTO gold_trade (id, trade_time, amount, unit_price, handling_fee, weight, trade_type, gold_type)
VALUES (1, '2021-05-10', 424.60, 424.60, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (2, '2021-06-10', 430.00, 430.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (3, '2021-07-10', 419.00, 419.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (4, '2021-08-10', 406.30, 406.30, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (5, '2021-09-10', 417.00, 417.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (6, '2021-10-10', 425.00, 425.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (7, '2021-11-10', 426.60, 426.60, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (8, '2021-12-10', 412.00, 412.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (9, '2022-01-10', 414.30, 414.30, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (10, '2022-02-10', 426.10, 426.10, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (11, '2022-03-10', 448.00, 448.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (12, '2022-04-10', 400.00, 400.00, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (13, '2022-05-10', 445.60, 445.60, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (14, '2022-06-10', 434.80, 434.80, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (15, '2022-07-10', 420.90, 420.90, 0.00, 1.0000, 'PURCHASE', 'PHYSICAL'),
       (16, '2023-06-12', 500.00, 451.37, 2.50, 1.1021, 'PURCHASE', 'PAPER'),
       (17, '2023-07-12', 500.00, 453.76, 2.50, 1.0963, 'PURCHASE', 'PAPER'),
       (18, '2023-08-10', 500.00, 454.96, 2.50, 1.0935, 'PURCHASE', 'PAPER'),
       (19, '2023-09-10', 500.00, 468.40, 2.50, 1.0621, 'PURCHASE', 'PAPER'),
       (20, '2023-10-10', 500.00, 452.47, 2.50, 1.0995, 'PURCHASE', 'PAPER'),
       (21, '2023-10-17', 4661.99, 463.88, 23.19, 10.0000, 'PURCHASE', 'PAPER'),
       (22, '2023-11-10', 500.00, 467.29, 2.50, 1.0646, 'PURCHASE', 'PAPER'),
       (23, '2023-12-10', 500.00, 467.73, 2.50, 1.0636, 'PURCHASE', 'PAPER'),
       (24, '2024-01-10', 500.00, 482.54, 2.50, 1.0310, 'PURCHASE', 'PAPER'),
       (25, '2024-02-12', 500.00, 479.25, 2.50, 1.0380, 'PURCHASE', 'PAPER'),
       (26, '2024-03-12', 500.00, 504.32, 2.50, 0.9864, 'PURCHASE', 'PAPER'),
       (27, '2024-04-12', 500.00, 562.40, 2.50, 0.8846, 'PURCHASE', 'PAPER'),
       (28, '2024-04-15', 12028.94, 561.73, 60.44, 21.5217, 'REDEEM', 'PAPER'),
       (29, '2024-04-19', 11531.60, 576.58, 0.00, 20.0000, 'PURCHASE', 'PHYSICAL'),
       (30, '2024-04-22', 565.82, 557.80, 2.82, 1.0093, 'PURCHASE', 'PAPER'),
       (31, '2024-05-12', 600.00, 554.40, 3.00, 1.0768, 'PURCHASE', 'PAPER'),
       (32, '2024-06-12', 600.00, 545.32, 3.00, 1.0947, 'PURCHASE', 'PAPER'),
       (33, '2024-07-12', 600.00, 565.42, 3.00, 1.0558, 'PURCHASE', 'PAPER'),
       (34, '2024-08-12', 600.00, 569.74, 3.00, 1.0478, 'PURCHASE', 'PAPER'),
       (35, '2024-09-12', 600.00, 583.36, 3.00, 1.0233, 'PURCHASE', 'PAPER'),
       (36, '2024-10-10', 3336.00, 667.20, 0.00, 5.0000, 'PURCHASE', 'PHYSICAL'),
       (37, '2024-10-12', 600.00, 596.80, 3.00, 1.0003, 'PURCHASE', 'PAPER'),
       (38, '2024-11-12', 600.00, 603.36, 3.00, 0.9894, 'PURCHASE', 'PAPER'),
       (39, '2024-12-12', 600.00, 624.70, 3.00, 0.9556, 'PURCHASE', 'PAPER'),
       (40, '2025-01-12', 600.00, 635.65, 3.00, 0.9391, 'PURCHASE', 'PAPER'),
       (41, '2025-02-12', 600.00, 683.04, 3.00, 0.8740, 'PURCHASE', 'PAPER');