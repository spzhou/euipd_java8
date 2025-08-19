# 贡献指南

感谢您对 EUIPD 项目的关注！我们欢迎所有形式的贡献，包括但不限于：

- 🐛 Bug 报告
- ✨ 新功能建议  
- 📝 文档改进
- 🔧 代码优化
- 🧪 测试用例

## 🚀 开始贡献

### 1. 准备工作

在开始贡献之前，请确保您已经：

- 安装了 Git
- 有 GitHub 账号
- 熟悉 Java、Spring Boot 开发
- 阅读了项目的 [README.md](README.md)

### 2. 环境搭建

```bash
# 1. Fork 项目到您的 GitHub 账号
# 2. 克隆您 fork 的仓库
git clone https://github.com/your-username/euipd.git
cd euipd

# 3. 添加上游仓库
git remote add upstream https://github.com/original-username/euipd.git

# 4. 安装依赖
mvn clean install
```

## 📋 贡献类型

### 🐛 Bug 报告

如果您发现了 bug，请：

1. 检查 [Issues](https://github.com/your-username/euipd/issues) 确认是否已报告
2. 如果没有，请创建新 Issue，包含：
   - 清晰的标题和描述
   - 重现步骤
   - 期望行为 vs 实际行为
   - 环境信息（操作系统、Java 版本等）
   - 相关错误日志

**Bug 报告模板：**
```markdown
**Bug 描述**
简明扼要地描述 bug

**重现步骤**
1. 前往 '...'
2. 点击 '....'
3. 滚动到 '....'
4. 看到错误

**期望行为**
描述您期望发生的行为

**实际行为**
描述实际发生的行为

**环境信息**
- 操作系统: [例如 macOS 12.0]
- Java 版本: [例如 OpenJDK 8]
- 项目版本: [例如 v1.0.0]

**附加信息**
添加任何其他有助于解释问题的信息
```

### ✨ 功能建议

1. 在 Issues 中创建新的功能请求
2. 详细描述功能和使用场景
3. 等待维护者反馈和讨论

### 💻 代码贡献

#### 分支命名规范

- `feature/功能名称` - 新功能
- `bugfix/问题描述` - Bug 修复
- `hotfix/紧急修复` - 紧急修复
- `docs/文档更新` - 文档更新

#### 提交信息规范

使用 [约定式提交](https://www.conventionalcommits.org/zh-hans/) 规范：

```
<类型>[可选的作用域]: <描述>

[可选的正文]

[可选的脚注]
```

**类型：**
- `feat`: 新功能
- `fix`: Bug 修复
- `docs`: 文档更新
- `style`: 代码格式修改
- `refactor`: 重构
- `test`: 测试用例
- `chore`: 构建过程或辅助工具的变动

**示例：**
```
feat(customer): 添加客户批量导入功能

- 支持 Excel 文件导入
- 添加数据验证
- 支持导入结果预览

closes #123
```

#### 代码规范

1. **Java 代码规范**
   - 遵循 [阿里巴巴 Java 开发手册](https://github.com/alibaba/p3c)
   - 使用 4 个空格缩进
   - 类名使用 PascalCase
   - 方法名和变量名使用 camelCase
   - 常量使用 UPPER_SNAKE_CASE

2. **注释规范**
   ```java
   /*
    * Copyright 2025 您的姓名
    *
    * Licensed under the Apache License, Version 2.0 (the "License");
    * you may not use this file except in compliance with the License.
    * You may obtain a copy of the License at
    *
    *     http://www.apache.org/licenses/LICENSE-2.0
    *
    * Unless required by applicable law or agreed to in writing, software
    * distributed under the License is distributed on an "AS IS" BASIS,
    * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    * See the License for the specific language governing permissions and
    * limitations under the License.
    */
   
   /**
    * 客户信息管理服务
    * 
    * @author 您的姓名
    * @since 1.0.0
    */
   public class CustomerService {
       
       /**
        * 根据客户ID获取客户信息
        * 
        * @param customerId 客户ID
        * @return 客户信息
        * @throws CustomerNotFoundException 当客户不存在时抛出
        */
       public Customer getCustomerById(String customerId) {
           // 实现逻辑
       }
   }
   ```

3. **单元测试**
   - 新功能必须包含测试用例
   - 测试覆盖率不低于 80%
   - 使用 JUnit 5 + Mockito

#### Pull Request 流程

1. **创建分支**
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **开发和测试**
   ```bash
   # 开发您的功能
   
   # 运行测试
   mvn test
   
   # 检查代码格式
   mvn checkstyle:check
   ```

3. **提交代码**
   ```bash
   git add .
   git commit -m "feat: 添加新功能"
   git push origin feature/your-feature-name
   ```

4. **创建 Pull Request**
   - 访问 GitHub 仓库
   - 点击 "New Pull Request"
   - 选择您的分支
   - 填写 PR 描述

**PR 模板：**
```markdown
## 🎯 变更类型
- [ ] Bug 修复
- [ ] 新功能
- [ ] 文档更新
- [ ] 代码重构
- [ ] 测试用例

## 📝 变更描述
简要描述这次变更的内容

## 🔗 相关 Issue
Closes #issue-number

## 🧪 测试
- [ ] 已添加测试用例
- [ ] 所有测试通过
- [ ] 已手动测试

## 📋 检查清单
- [ ] 代码遵循项目规范
- [ ] 已更新相关文档
- [ ] 提交信息符合规范
- [ ] 没有引入新的警告
```

## 🔍 代码审查

所有的 Pull Request 都需要经过代码审查：

1. **自动检查**
   - CI/CD 流水线检查
   - 代码格式检查
   - 单元测试执行

2. **人工审查**
   - 代码质量
   - 功能完整性
   - 文档完整性

3. **审查标准**
   - 代码可读性和可维护性
   - 性能影响
   - 安全性考虑
   - 向后兼容性

## 📚 文档贡献

### 类型
- API 文档
- 用户手册
- 开发指南
- 示例代码

### 写作规范
- 使用 Markdown 格式
- 语言简洁明了
- 提供代码示例
- 包含截图（如需要）

## 🎯 最佳实践

### 1. 提交前检查
```bash
# 运行完整测试套件
mvn clean test

# 检查代码格式
mvn checkstyle:check

# 检查依赖漏洞
mvn dependency-check:check
```

### 2. 保持分支更新
```bash
# 定期同步上游分支
git fetch upstream
git checkout master
git merge upstream/master
git push origin master
```

### 3. 小而频繁的提交
- 每个提交只包含一个逻辑变更
- 避免大量文件的单次提交
- 及时推送避免冲突

## 🚫 不接受的贡献

以下类型的贡献可能不会被接受：

- 破坏性变更（除非有充分理由）
- 未经讨论的大型重构
- 不符合项目目标的功能
- 缺少测试的代码
- 违反编码规范的代码

## 🆘 获取帮助

如果您在贡献过程中遇到问题：

1. 查看 [FAQ](docs/FAQ.md)
2. 搜索已有的 [Issues](https://github.com/your-username/euipd/issues)
3. 在 [Discussions](https://github.com/your-username/euipd/discussions) 中提问
4. 联系维护者：391902958@qq.com

## 🙏 致谢

感谢所有为项目做出贡献的开发者！您的贡献让这个项目变得更好。

---

**记住：质量比数量更重要。一个经过深思熟虑的小改进胜过匆忙的大变更。**
