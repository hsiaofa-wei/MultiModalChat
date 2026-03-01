# AI应用中心项目 - Web开发实训

## 项目简介

本项目是一个基于Spring Boot和Vue 3的AI应用集成平台，其主要功能包括：

- AI聊天（多模态对话机器人，支持图片、音频等）
- 面试官模拟（技术/项目/HR面试模拟，一问一答，无限对话）
- 职场导师（简历优化指导 + 职业发展建议 + 职场经验分享）
- ChatPDF（打造个人知识库，与知识库自由对话）
- 音乐播放器
- 天气小部件

项目采用前后端分离架构，前端使用Vue 3 + TypeScript + Vite构建，后端使用Spring Boot 3.5.9 + MyBatis Plus + Spring AI技术栈。

## 技术栈

### 后端技术

- **Spring Boot 3.5.9**：基础框架
- **Java 17**：开发语言
- **MySQL**：关系型数据库
- **MyBatis Plus**：ORM框架
- **Spring AI**：AI集成框架
- **OpenAI API**（阿里百炼）：AI模型
- **Hutool**：工具类库
- **Lombok**：简化实体类开发
- **Maven**：项目构建与依赖管理

### 前端技术

- **Vue 3**：前端框架
- **TypeScript**：类型系统
- **Vite**：构建工具
- **Vue Router**：路由管理
- **Pinia**：状态管理
- **Naive UI**：UI组件库
- **@heroicons/vue**：图标库
- **@vueuse/core**：Vue组合式API工具集
- **@pdftron/webviewer**：PDF文档查看与处理
- **dompurify**：HTML净化
- **highlight.js**：代码高亮
- **marked**：Markdown解析
- **Sass**：CSS预处理器

## 运行环境

### 后端环境

- **JDK**：17
- **操作系统**：Windows 11
- **IDE**：IntelliJ IDEA
- **数据库**：MySQL（端口3306）
- **Maven**：3.8+

### 前端环境

- **Node.js**：18.0+
- **操作系统**：Windows 11
- **IDE**：Visual Studio Code
- **浏览器**：Chrome、Firefox、Safari、Edge

## 项目启动

### 后端启动

1. 确保MySQL数据库已创建并运行。

2. 在 `LVChat/LVChat/src/main/resources/application.yml` 中配置数据库连接信息和AI API密钥：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/aichat?serverTimezone=Asia/Shanghai&useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&useServerPrepStmts=false
    username: root
    password: 你的密码
  ai:
    openai:
      base-url: https://dashscope.aliyuncs.com/compatible-mode
      api-key: 你的阿里百炼API密钥
```

3. 使用Maven构建并启动项目：

```bash
# 进入后端项目目录
cd LVChat/LVChat

# 构建项目
mvn clean package

# 启动项目
java -jar target/heima-ai-0.0.1-SNAPSHOT.jar
```

### 前端启动

1. 进入前端项目目录：

```bash
cd LVChat-protal
```

2. 安装依赖：

```bash
npm install
```

3. 启动开发服务器：

```bash
npm run dev
```

4. 构建生产版本：

```bash
npm run build
```

## 项目结构

### 后端结构

```
LVChat/
├── LVChat/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/LVChat/ai/
│   │   │   │   ├── config/ # 配置类
│   │   │   │   │   ├── AiProperties.java
│   │   │   │   │   ├── MvcConfiguration.java
│   │   │   │   │   └── SpringAIConfig.java
│   │   │   │   ├── constants/ # 常量
│   │   │   │   │   └── Constant.java
│   │   │   │   ├── controller/ # 控制器
│   │   │   │   │   ├── ChatController.java
│   │   │   │   │   ├── ChatHistoryController.java
│   │   │   │   │   ├── CustomerServiceController.java
│   │   │   │   │   ├── GameController.java
│   │   │   │   │   ├── MyController.java
│   │   │   │   │   ├── PdfController.java
│   │   │   │   │   └── ServiceController.java
│   │   │   │   ├── entity/ # 实体类
│   │   │   │   │   ├── dto/ # 数据传输对象
│   │   │   │   │   │   └── WeatherDTO.java
│   │   │   │   │   ├── po/ # 持久化对象
│   │   │   │   │   │   ├── Course.java
│   │   │   │   │   │   ├── CourseReservation.java
│   │   │   │   │   │   └── School.java
│   │   │   │   │   ├── query/ # 查询对象
│   │   │   │   │   │   └── CourseQuery.java
│   │   │   │   │   └── vo/ # 视图对象
│   │   │   │   │       ├── MessageVO.java
│   │   │   │   │       └── Result.java
│   │   │   │   ├── mapper/ # MyBatis映射
│   │   │   │   │   ├── CourseMapper.java
│   │   │   │   │   ├── CourseReservationMapper.java
│   │   │   │   │   └── SchoolMapper.java
│   │   │   │   ├── repository/ # 仓库
│   │   │   │   │   ├── ChatHistoryRepository.java
│   │   │   │   │   ├── FileRepository.java
│   │   │   │   │   ├── InMemoryChatHistoryRepository.java
│   │   │   │   │   └── LocalPdfFileRepository.java
│   │   │   │   ├── service/ # 服务
│   │   │   │   │   ├── impl/ # 实现
│   │   │   │   │   │   ├── CourseReservationServiceImpl.java
│   │   │   │   │   │   ├── CourseServiceImpl.java
│   │   │   │   │   │   └── SchoolServiceImpl.java
│   │   │   │   │   ├── ICourseReservationService.java
│   │   │   │   │   ├── ICourseService.java
│   │   │   │   │   └── ISchoolService.java
│   │   │   │   ├── tools/ # 工具
│   │   │   │   │   ├── embedding/ # 嵌入
│   │   │   │   │   │   └── CityEmbedding.java
│   │   │   │   │   ├── CourseTools.java
│   │   │   │   │   └── WeatherTools.java
│   │   │   │   └── MultiModalChat.java
│   │   │   └── resources/ # 资源
│   │   │       ├── application.yml # 配置文件
│   │   │       ├── ci.txt
│   │   │       └── citys.txt
│   │   └── test/ # 测试
│   ├── target/ # 构建输出
│   ├── .gitignore
│   ├── HELP.md
│   ├── mvnw
│   ├── mvnw.cmd
│   └── pom.xml # Maven配置
```

### 前端结构

```
LVChat-protal/
├── public/ # 静态资源
│   ├── webviewer/ # PDF查看器
│   ├── favicon.ico
│   └── pdf.worker.js
├── src/ # 源代码
│   ├── assets/ # 资源文件
│   │   ├── base.css
│   │   ├── logo.svg
│   │   └── main.css
│   ├── components/ # 组件
│   │   ├── icons/ # 图标组件
│   │   ├── ChatMessage.vue
│   │   ├── HelloWorld.vue
│   │   ├── MusicPlayer.vue
│   │   ├── PDFViewer.vue
│   │   ├── TheWelcome.vue
│   │   ├── WeatherWidget.vue
│   │   └── WelcomeItem.vue
│   ├── router/ # 路由
│   │   ├── index.js
│   │   └── index.ts
│   ├── services/ # API服务
│   │   └── api.js
│   ├── stores/ # 状态管理
│   │   └── counter.ts
│   ├── utils/ # 工具函数
│   │   └── pdfStorage.js
│   ├── views/ # 页面
│   │   ├── AIChat.vue
│   │   ├── AboutView.vue
│   │   ├── ChatPDF.vue
│   │   ├── ComfortSimulator.vue
│   │   ├── CustomerService.vue
│   │   ├── GameChat.vue
│   │   ├── Home.vue
│   │   └── HomeView.vue
│   ├── App.vue
│   ├── main.js
│   └── main.ts
├── .gitignore
├── README.md
├── env.d.ts
├── index.html
├── music.mp3
├── package-lock.json
└── package.json # npm配置
```

## 核心功能

### AI聊天

- 多模态对话支持
- 图片、音频处理
- 智能回复
- 聊天历史记录

### 面试官模拟

- 技术面试模拟
- 项目面试模拟
- HR面试模拟
- 无限对话能力

### 职场导师

- 简历优化指导
- 职业发展建议
- 职场经验分享

### ChatPDF

- PDF文档上传
- 文档内容分析
- 智能问答
- 个人知识库构建

### 其他功能

- 音乐播放器
- 天气小部件
- 深色模式支持
- 响应式设计

## 技术实现

### 后端实现

1. **Spring Boot框架**：提供RESTful API接口
2. **Spring AI**：集成OpenAI API（阿里百炼），实现AI对话功能
3. **MyBatis Plus**：操作数据库，实现数据持久化
4. **多模态支持**：处理文本、图片、音频等多种输入类型
5. **PDF处理**：使用Spring AI的PDF文档阅读器，实现文档内容分析

### 前端实现

1. **Vue 3 + TypeScript**：构建现代化前端应用
2. **Vue Router**：实现页面导航和路由管理
3. **Pinia**：实现状态管理
4. **Naive UI**：提供美观的UI组件
5. **@pdftron/webviewer**：实现PDF文档的查看和处理
6. **响应式设计**：适配不同设备屏幕
7. **深色模式**：支持明暗主题切换

## 项目亮点

- **完整的前后端分离架构**
- **现代化技术栈**：使用Spring Boot 3、Vue 3、TypeScript等最新技术
- **丰富的AI应用场景**：集成多种AI功能
- **多模态支持**：支持文本、图片、音频等多种输入类型
- **良好的用户体验**：响应式设计、深色模式、流畅的交互
- **清晰的代码结构**：模块化设计，易于维护和扩展
- **完整的TypeScript类型定义**：提高代码质量和开发效率

## 不足之处

- **AI API依赖**：依赖外部AI服务，可能产生费用
- **数据库设计**：部分表结构设计可优化
- **缓存机制**：未实现缓存，可能影响性能
- **测试覆盖度**：测试用例不够完善
- **部署配置**：缺少自动化部署配置

## 未来规划

- **增加用户认证系统**：实现用户注册、登录功能
- **完善数据持久化**：优化数据库设计，增加缓存机制
- **扩展AI功能**：增加更多AI应用场景
- **优化性能**：提升系统响应速度和稳定性
- **实现国际化支持**：支持多语言
- **完善测试覆盖度**：增加单元测试和集成测试
- **自动化部署**：配置CI/CD流程

## 部分截图展示


更多内容请您下载观看