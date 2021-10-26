-- 基金基本表
create table IF NOT EXISTS fund_basic
(
    id             int         not null primary key comment '主键',
    name           varchar(20) not null comment '基金名称',
    code           varchar(6)  not null comment '基金代码',
    buy_rate       double      not null comment '购买费率',
    operating_rate double      not null comment '运作费率',
    manager        varchar(20) not null comment '基金经理',
    type           varchar(20) comment '基金类型',
    fund_scale     varchar(20) comment '基金规模'
) comment ='基金基本表';

-- 基金单位净值表
create table if not exists fund_net_worth
(
    id          int        not null primary key comment '主键',
    code        varchar(6) not null comment '基金代码',
    date        date       not null comment '日期',
    net_worth   double     not null comment '单位净值',
    total_worth double     not null comment '累计净值',
    dividends   double comment '分红'
) comment ='基金当前净值表';

-- 交易记录表
create table if not exists fund_transaction_record
(
    id                  int        not null primary key comment '主键',
    code                varchar(6) not null comment '基金代码',
    subscription_amount double     not null comment '申购金额',
    confirm_share       double     not null comment '确认份额',
    date                date       not null comment '日期'
) comment ='交易记录表';

-- 持仓表
create table if not exists fund_position
(
    id                        int        not null primary key comment '主键',
    code                      varchar(6) not null comment '基金代码',
    present_value             double     not null comment '现值',
    redeemed                  double     not null comment '已赎回',
    investment                double     not null comment '总投入',
    revenue                   double     not null comment '总收益',
    holding_income            double     not null comment '持有收益',
    annualized_rate_of_return double     not null comment '年化收益率'
) comment = '持仓表';
