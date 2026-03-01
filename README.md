
## 部分截图展示 
<img width="425"  alt="屏幕截图 2026-03-01 151833" src="https://github.com/user-attachments/assets/0863d7aa-a19a-4c17-aaa6-d3ab35d064b4" />
<img width="425"  alt="屏幕截图 2026-03-01 151615" src="https://github.com/user-attachments/assets/4423006c-de16-41df-95ef-27e1f961eaae" />
<img width="425"   alt="屏幕截图 2026-03-01 151556" src="https://github.com/user-attachments/assets/d6304ad1-51e9-4603-bd19-3f2e7ba03c36" />
<img width="425"   alt="屏幕截图 2026-03-01 151640" src="https://github.com/user-attachments/assets/de209e25-ad76-4782-b1db-7ae23d40b3cc" />
<img width="425"   alt="屏幕截图 2026-03-01 150617" src="https://github.com/user-attachments/assets/6dd31a56-ffaa-4df7-b5f0-c4458156e97f" />
<img width="425"   alt="屏幕截图 2026-03-01 151646" src="https://github.com/user-attachments/assets/20a0d046-c3a0-423d-aa43-b636ab026e66" />

>更多内容请您下载观看

# MultiModalChat · 多模态 AI 应用中心

全栈 AI 应用集成平台：基于 **Spring Boot 3 + Spring AI + Vue 3** 构建，支持多模态对话、面试模拟、ChatPDF 知识库、职场导师等实用 AI 场景。核心亮点：2 周快速上手 Spring AI，实现图片/音频多模态输入 + 通义千问/阿里百炼模型集成。

## 项目简介
MultiModalChat 是一个个人 AI 工具集合，集成了多种实际落地 AI 功能：

- **多模态 AI 聊天**：支持文本 + 图片 + 音频输入，智能回复 + 上下文记忆
- **面试官模拟**：技术/项目/HR 三种模式，一问一答、无限轮对话
- **职场导师**：简历优化建议 + 职业规划 + 职场经验分享
- **ChatPDF**：上传 PDF 构建个人知识库，与文档自由对话
- **实用小部件**：音乐播放器、天气组件、深色模式切换

前后端分离架构，前端 Vue 3 + TypeScript + Vite，后端 Spring Boot + MyBatis Plus + Spring AI，深度集成阿里百炼大模型。

## 核心功能亮点
- 多模态输入处理（图片识别、音频转文字）
- 角色扮演式对话（面试官/导师模式）
- PDF 文档智能解析 & RAG 式问答
- 响应式 UI + 深色模式 + 流畅交互
- 聊天历史持久化 + 实时天气/音乐集成

## 技术栈

### 后端
- Spring Boot 3.5.9 + Java 17
- Spring AI（官方集成阿里百炼/通义千问）
- MyBatis Plus（ORM）
- MySQL（数据持久化）
- Hutool / Lombok / Maven

### 前端
- Vue 3 + TypeScript + Vite
- Vue Router + Pinia
- Naive UI（UI 组件）
- @pdftron/webviewer（PDF 查看/处理）
- marked + highlight.js（Markdown & 代码高亮）
- dompurify（HTML 净化）
- @vueuse/core + Sass

## 快速启动

### 后端
 ```
 配置
application.yml：
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/aichat?...
       username: root
       password: your_password
     ai:
       openai:
         base-url: https://dashscope.aliyuncs.com/compatible-mode
         api-key: sk-你的阿里百炼密钥   # 替换为你的 key
 ```

 ### 前端
 ```
 cd LVChat-protal
 npm install
 npm run dev
 访问：http://localhost:5173（Vite 默认端口）
```

## 项目亮点 & 个人收获

- 快速上手 Spring AI 多模态框架（2 周从 0 到 1）
- 实现真实多模态场景落地（图片/音频 + 面试模拟）
- 全栈独立开发 + 模块化设计，代码可维护性高
- 探索 RAG、角色扮演、PDF 解析等前沿 AI 应用
- 响应式 UI + 深色模式，提升用户体验

欢迎 Star & Fork！如果对 Spring AI、多模态应用或 Vue 3 全栈开发感兴趣，欢迎 Issues 交流～
