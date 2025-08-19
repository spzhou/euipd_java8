# EUIPD - 企业级研发管理系统

[![License: Apache 2.0](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-Hoxton-blue.svg)](https://spring.io/projects/spring-cloud)

## 📋 项目简介

EUIPD 是一个基于 Spring Cloud 微服务架构的企业级研发管理系统，提供完整的企业级研发管理解决方案。系统采用前后端分离设计，支持高并发、高可用的研发管理平台。

### ✨ 主要特性

- 🏗️ **微服务架构**：基于 Spring Cloud 构建，支持分布式部署
- 🎥 **视频管理**：集成阿里云 VOD，支持视频上传、转码、播放
- 💾 **文件存储**：支持阿里云 OSS 对象存储
- 🔐 **权限管理**：完整的 RBAC 权限控制系统
- 💳 **支付集成**：支持微信支付、支付宝支付
- 🔄 **消息队列**：基于 RabbitMQ 的异步消息处理
- 📊 **数据分析**：课程学习进度跟踪和数据统计
- 🌐 **API 网关**：统一的服务入口和路由管理

## 🏗️ 系统架构

```
┌─────────────────────────────────────────────────┐
│                   前端项目                        │
│              EuipdVue2 (Vue.js)                │
└─────────────────────┬───────────────────────────┘
                      │
┌─────────────────────┴───────────────────────────┐
│                Gateway 网关                      │
│           (统一入口、路由、鉴权)                    │
└─────────────────────┬───────────────────────────┘
                      │
        ┌─────────────┼─────────────┐
        │             │             │
┌───────▼───┐ ┌──────▼──┐ ┌────────▼────┐
│  System   │ │Customer │ │  Warehouse  │
│  服务     │ │ 服务    │ │   服务      │
└───────────┘ └─────────┘ └─────────────┘
        │             │             │
        └─────────────┼─────────────┘
                      │
        ┌─────────────┼─────────────┐
        │             │             │
┌───────▼───┐ ┌──────▼──┐ ┌────────▼────┐
│   File    │ │   FTP   │ │    Pay      │
│  服务     │ │  服务   │ │   服务      │
└───────────┘ └─────────┘ └─────────────┘
```

## 🚀 技术栈

### 后端技术
- **核心框架**：Spring Boot 2.2.2, Spring Cloud Hoxton
- **数据库**：MySQL 8.0
- **缓存**：Redis
- **消息队列**：RabbitMQ
- **服务注册**：Nacos
- **API 网关**：Spring Cloud Gateway
- **ORM 框架**：MyBatis
- **构建工具**：Maven
- **Java 版本**：JDK 8

### 第三方服务
- **阿里云 VOD**：视频点播服务
- **阿里云 OSS**：对象存储服务
- **支付平台**：微信支付、支付宝
- **短信服务**：阿里云短信服务

## 📦 模块说明

| 模块 | 说明 | 端口 |
|------|------|------|
| `gateway` | API 网关服务 | 9000 |
| `system` | 系统管理服务 | 9001 |
| `customer` | 客户管理服务 | 9002 |
| `server` | 核心业务服务 | 9003 |
| `file2` | 文件管理服务 | 9004 |
| `ftp` | FTP 文件服务 | 9005 |
| `warehouse` | 数据仓库服务 | 9006 |
| `payProject` | 支付服务 | 9007 |

## 🛠️ 快速开始

### 环境要求

- JDK 8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- Node.js 14+ (前端项目)

### 1. 克隆项目

```bash
git clone https://github.com/yourusername/euipd.git
cd euipd
```

### 2. 数据库准备

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE mycourse CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 导入初始数据
mysql -u root -p mycourse < doc/db/all.sql
```

### 3. 配置文件

复制配置文件并修改数据库连接信息：

```bash
# 复制配置模板
cp server/src/main/resources/config/application.properties.example server/src/main/resources/config/application.properties

# 修改数据库连接
vim server/src/main/resources/config/application.properties
```

**重要配置项：**
```properties
# 数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/mycourse
spring.datasource.username=root
spring.datasource.password=your_password

# Redis 配置
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=your_redis_password

# 第三方服务配置（需要自行申请）
# 阿里云 OSS
oss.accessKeyId=your_access_key_id
oss.accessKeySecret=your_access_key_secret

# 阿里云 VOD
vod.accessKeyId=your_vod_access_key_id
vod.accessKeySecret=your_vod_access_key_secret
```

### 4. 启动服务

```bash
# 构建项目
mvn clean compile

# 按顺序启动服务
# 1. 启动 Nacos (服务注册中心)
# 2. 启动核心服务
mvn spring-boot:run -pl server

# 3. 启动网关
mvn spring-boot:run -pl gateway

# 4. 启动其他服务
mvn spring-boot:run -pl system
mvn spring-boot:run -pl customer
# ... 其他服务
```

### 5. 前端项目

前端项目地址：[EuipdVue2](https://github.com/yourusername/EuipdVue2)

```bash
git clone https://github.com/yourusername/EuipdVue2.git
cd EuipdVue2
npm install
npm run serve
```

## 📚 API 文档

启动服务后访问：
- Swagger UI：http://localhost:9000/swagger-ui.html
- API 文档：http://localhost:9000/v2/api-docs

## 🔧 开发指南

### 代码规范
- 遵循阿里巴巴 Java 开发手册
- 使用 IDE 格式化工具统一代码风格
- 提交前运行 `mvn clean test` 确保测试通过

### 分支管理
- `master`：主分支，用于生产环境
- `develop`：开发分支，用于功能开发
- `feature/*`：功能分支
- `hotfix/*`：热修复分支

## 📄 许可证

本项目采用 [Apache License 2.0](LICENSE) 开源许可证。

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

详细贡献指南请查看 [CONTRIBUTING.md](CONTRIBUTING.md)

## 📞 联系方式

- 作者：周朔鹏
- 邮箱：391902958@qq.com
- 项目官网：https://www.euipd.com

## ⭐ 支持项目

如果这个项目对您有帮助，请给我们一个 ⭐️！

## 📝 更新日志

### v1.0.0 (2025-01-XX)
- 🎉 初始版本发布
- ✨ 支持完整的在线课程管理功能
- 🔐 实现 RBAC 权限管理系统
- 💳 集成微信、支付宝支付功能

---

**注意**：本项目仅供学习和研究使用，如用于商业用途请遵循相关法律法规。
