-- 基金基本表
create table IF NOT EXISTS financing.fund_basic
(
    id             bigint      not null primary key comment '主键',
    name           varchar(20) not null comment '基金名称',
    code           varchar(6)  not null comment '基金代码',
    buy_rate       double      not null comment '购买费率',
    operating_rate double      not null comment '运作费率',
    manager        varchar(20) not null comment '基金经理',
    type           varchar(20) comment '基金类型',
    fund_scale     varchar(20) comment '基金规模'
) comment ='基金基本表';

-- 基金单位净值表
create table if not exists financing.fund_net_worth
(
    id          bigint     not null primary key comment '主键',
    code        varchar(6) not null comment '基金代码',
    date        date       not null comment '日期',
    net_worth   double     not null comment '单位净值',
    total_worth double     not null comment '累计净值',
    dividends   double comment '分红'
) comment ='基金当前净值表';

-- 交易记录表
create table if not exists financing.fund_trade_record
(
    id            bigint     not null primary key comment '主键',
    code          varchar(6) not null comment '基金代码',
    amount        double     not null comment '金额',
    type          int        not null comment '交易类型，1 - 申购，2 - 赎回，3 - 分红',
    confirm_date  date       not null comment '确认日期',
    confirm_share double comment '确认份额'
) comment ='交易记录表';

-- 持仓表
create table if not exists financing.fund_position
(
    id                        bigint     not null primary key comment '主键',
    code                      varchar(6) not null comment '基金代码',
    present_value             double     not null comment '现值',
    redeemed                  double     not null comment '已赎回',
    investment                double     not null comment '总投入',
    revenue                   double     not null comment '总收益',
    holding_income            double     not null comment '持有收益',
    annualized_rate_of_return double     not null comment '年化收益率'
) comment = '持仓表';
