<template>
  <div class="home" :class="{ 'dark': isDark }">
    <div class="container">
      <h1 class="title">AI 应用中心</h1>
      <div class="cards-grid">
        <router-link 
          v-for="app in aiApps" 
          :key="app.id"
          :to="app.route"
          class="card-wrap"
        >
          <div 
            class="card" 
            :style="{ background: app.bgGradient }"
          >
            <div class="card-info">
              <component :is="app.icon" class="icon" />
              <h2>{{ app.title }}</h2>
              <p>{{ app.description }}</p>
            </div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useDark } from '@vueuse/core'
import { 
  ChatBubbleLeftRightIcon,
  UserCircleIcon,
  UserGroupIcon,
  DocumentTextIcon
} from '@heroicons/vue/24/outline'

const isDark = useDark()

const aiApps = ref([
  {
    id: 1,
    title: 'AI 聊天',
    description: '多模态对话机器人，支持图片、音频等',
    route: '/ai-chat',
    icon: ChatBubbleLeftRightIcon,
    bgGradient: 'linear-gradient(135deg, #FEFCFF 0%, #E0F2FE 100%)'
  },
  {
    id: 2,
    title: '面试官',
    description: '技术/项目/HR面试模拟，一问一答，无限对话',
    route: '/game',
    icon: UserCircleIcon,
    bgGradient: 'linear-gradient(135deg, #FFFBF0 0%, #E0F2FE 100%)'
  },
  {
    id: 3,
    title: '职场导师',
    description: '简历优化指导 + 职业发展建议 + 职场经验分享',
    route: '/customer-service',
    icon: UserGroupIcon,
    bgGradient: 'linear-gradient(135deg, #F0FDF5 0%, #CCFBF1 100%)'
  },
  {
    id: 4,
    title: 'ChatPDF',
    description: '打造你的个人知识库，与知识库自由对话',
    route: '/chat-pdf',
    icon: DocumentTextIcon,
    bgGradient: 'linear-gradient(135deg, #FFFEF0 0%, #FEF9C3 100%)'
  }
])
</script>

<style scoped lang="scss">
$hoverEasing: cubic-bezier(0.23, 1, 0.32, 1);
$returnEasing: cubic-bezier(0.445, 0.05, 0.55, 0.95);

.home {
  min-height: 100vh;
  padding: 40px 0;
  background: var(--bg-color);
  transition: background-color 0.3s;

  .container {
    max-width: 1600px;
    margin: 0 auto;
    padding: 0 80px;
  }

  .title {
    font-family: "Playfair Display", serif;
    font-size: 3rem;
    font-weight: 700;
    text-align: center;
    margin: 0 0 60px 0;
    background: linear-gradient(45deg, #007CF0, #00DFD8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
  }

  .cards-grid {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 40px;
    padding: 0 20px;
  }

  .card-wrap {
    cursor: pointer;
    display: block;
    text-decoration: none;
    color: inherit;
    
    &:hover {
      .card-info {
        transform: translateY(0);
        
        p {
          opacity: 1;
        }
      }
      
      .card-info, .card-info p {
        transition: 0.6s $hoverEasing;
      }
      
      .card {
        transition:
          0.6s $hoverEasing,
          box-shadow 2s $hoverEasing;
        box-shadow:
          rgba(white, 0.3) 0 0 40px 5px,
          rgba(white, 1) 0 0 0 1px,
          rgba(black, 0.2) 0 30px 60px 0,
          inset rgba(0, 0, 0, 0.05) 0 0 0 5px,
          inset rgba(255, 255, 255, 0.5) 0 0 0 6px;
      }
    }
  }

  .card {
    position: relative;
    width: 240px;
    height: 320px;
    overflow: hidden;
    border-radius: 10px;
    box-shadow:
      rgba(black, 0.1) 0 10px 20px 0,
      inset rgba(0, 0, 0, 0.05) 0 0 0 5px,
      inset rgba(255, 255, 255, 0.5) 0 0 0 6px;
    transition: 1s $returnEasing;
  }

  .card-info {
    padding: 20px;
    padding-bottom: 80px;
    position: absolute;
    bottom: 0;
    color: #333;
    transform: translateY(40%);
    transition: 0.6s 1.6s cubic-bezier(0.215, 0.61, 0.355, 1);
    
    p {
      opacity: 0;
      color: #666;
      transition: 0.6s 1.6s cubic-bezier(0.215, 0.61, 0.355, 1);
      line-height: 1.5em;
      margin-top: 10px;
    }
    
    * {
      position: relative;
      z-index: 1;
    }
  }

  .icon {
    width: 48px;
    height: 48px;
    margin-bottom: 1rem;
    color: #666;
  }

  h2 {
    font-family: "Playfair Display", serif;
    font-size: 2rem;
    font-weight: 700;
    color: #333;
    margin: 0;
  }

  p {
    font-size: 0.875rem;
    color: #666;
  }
}

.dark {
  .card {
    box-shadow:
      rgba(black, 0.3) 0 10px 20px 0,
      inset rgba(255, 255, 255, 0.05) 0 0 0 5px,
      inset rgba(255, 255, 255, 0.1) 0 0 0 6px;
  }
  
  .card-info p {
    color: #999;
  }
  
  .icon {
    color: #999;
  }
  
  h2 {
    color: #ccc;
  }
  
  p {
    color: #aaa;
  }
}

@media (max-width: 768px) {
  .home {
    padding: 20px 0;
    
    .container {
      padding: 0 20px;
    }
    
    .title {
      font-size: 2rem;
      margin-bottom: 40px;
    }
    
    .cards-grid {
      gap: 20px;
      padding: 0;
    }
    
    .card {
      width: 100%;
      max-width: 280px;
      height: 280px;
    }
    
    h2 {
      font-size: 1.5rem;
    }
    
    .icon {
      width: 40px;
      height: 40px;
    }
  }
}
</style>
