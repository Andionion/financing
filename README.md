# 智能财务管理系统

![License](https://img.shields.io/badge/License-MulanPSL2-green)

## 🚀 项目简介
基于Spring Boot构建的财务数据管理平台，集成基金、黄金、公积金三大核心模块，提供实时数据展示、交易记录管理和自动净值更新功能。

## 🌐 在线体验
[生产环境访问](https://financing.brodynas.top:2443)

## 📊 核心功能
### 基金交易模块
- 实时基金净值监控
- 交易记录CRUD操作
- 自动净值更新（每日15:00定时任务）
- 收益率动态计算与可视化

### 黄金交易模块
- 实时金价追踪
- 持仓分类统计（纸黄金/实体黄金）
- 收益趋势图表展示
- 投资组合分析

### 公积金管理
- 月度入账/提取记录管理
- 贷款额度智能计算
- 利息累计统计
- 数据可视化报表

## 🛠️ 技术栈
### 后端
- Spring Boot 2.7.18
- MyBatis-Plus 3.5.9
- Redis 数据缓存
- Hutool 工具库
- 多数据源配置

### 前端
- Thymeleaf 模板引擎
- Bootstrap 5.3
- ECharts 数据可视化
- Flatpickr 日期选择器

### 基础设施
- MySQL 8.0
- Docker 容器化部署
- Nginx 反向代理

## 🚄 快速启动
```bash
# 克隆仓库
git clone https://github.com/yourusername/financing.git

# 编译打包
mvn clean package -DskipTests

# Docker部署
docker build -t financing .
docker run -p 9528:9528 -d financing
```
## ⚙️ 配置指南
1. 数据库配置 src/main/resources/application.properties :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/financing
spring.datasource.username=root
spring.datasource.password=your_password
 ```

2. Redis配置: 
```properties
spring.redis.host=localhost
spring.redis.port=6379
 ```

## 🤝 贡献指南
1. Fork本仓库
2. 创建特性分支 ( git checkout -b feature/新功能 )
3. 提交修改 ( git commit -m '添加新功能' )
4. 推送分支 ( git push origin feature/新功能 )
5. 创建Pull Request
## 📜 许可证
本项目采用 木兰宽松许可证 第2版

```plaintext
主要改进点：
1. 添加了技术栈细节<mcsymbol name="RedisConfig" filename="RedisConfig.java" path="src/main/java/cn/brody/financing/config/RedisConfig.java" startline="19" type="class"/></mcsymbol>
2. 完善了定时任务说明<mcsymbol name="FinancingApplication" filename="FinancingApplication.java" path="src/main/java/cn/brody/financing/FinancingApplication.java" startline="10" type="class"/></mcsymbol>
3. 增加了可视化图表说明<mcfolder name="templates" path="src/main/resources/templates"/></mcfolder>
4. 补充Docker部署细节<mcfolder name="Dockerfile" path="Dockerfile"/></mcfolder>
 ```