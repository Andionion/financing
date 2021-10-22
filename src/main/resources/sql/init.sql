-- 基金持仓表
create table IF NOT EXISTS 'fund_position'
(
    'id'             int         not null primary key comment '主键',
    'name'           varchar(20) not null comment '基金名称',
    'code'           varchar(6)  not null comment '基金代码',
    'buy_rate'       double      not null comment '购买费率',
    'operating_rate' double      not null comment '运作费率',
    'manager'        varchar(20) not null comment '基金经理',
    'net_worth'      double      not null comment '单位净值',
    'total_worth'    double      not null comment '累计净值',
    'type'           varchar(20) comment '基金类型 ',
    'fund_scale'     varchar(20) comment '基金规模',
    'net_worth_id'   int comment '当前净值表id'
) comment ='基金持仓表';


-- 基金单位净值表
create table if not exists 'fund_net_worth'
(
    'id'        int    not null primary key comment '主键',
    'date'      date   not null comment '日期',
    'net_worth' double not null comment '单位净值',
    'increase'  double not null comment '净值涨幅',
    'dividends' double comment '分红'
) comment ='基金当前净值表';

-- 累计净值表
create table if not exists 'fund_total_worth'
(
    'id'          int    not null primary key comment '主键',
    'date'        date   not null comment '日期',
    'total_worth' double not null comment '累计净值'
) comment ='累计净值表';


