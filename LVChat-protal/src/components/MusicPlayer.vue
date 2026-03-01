<template>
  <div class="music-player" :class="{ playing }">
    <div class="album-art">
      <svg class="vinyl" viewBox="0 0 100 100">
        <circle cx="50" cy="50" r="45" fill="#333"/>
        <circle cx="50" cy="50" r="15" fill="#fff"/>
        <circle cx="50" cy="50" r="5" fill="#333"/>
        <path d="M50,5 A45,45 0 0,1 95,50 A45,45 0 0,1 50,95 A45,45 0 0,1 5,50 A45,45 0 0,1 50,5 Z" fill="none" stroke="#fff" stroke-width="1" stroke-dasharray="5,5"/>
      </svg>
    </div> 
    
    <div class="controls">
      <button class="play-btn" @click="togglePlay">
        <svg class="play-icon" viewBox="0 0 24 24" v-if="!playing" style="fill: #333;">
          <path d="M8 5v14l11-7z"/>
        </svg>
        <svg class="play-icon" viewBox="0 0 24 24" v-else style="fill: #333;">
          <path d="M6 19h4V5H6v14zm8-14v14h4V5h-4z"/>
        </svg>
      </button>
      
      <div class="progress-container" @click="setProgress">
        <div class="progress-bar" :style="{ width: progress + '%' }"></div>
      </div>
      
      <div class="time">{{ currentTime }}</div>
      
      <button class="mute-btn" @click="toggleMute" :class="{ muted: isMuted }">
        <svg class="volume-icon" viewBox="0 0 24 24" v-if="!isMuted" style="fill: #333;">
          <path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/>
        </svg>
        <svg class="volume-icon" viewBox="0 0 24 24" v-else style="fill: #333;">
          <path d="M3 9v6h4l5 5V4L7 9H3zm13.5 3c0-1.77-1.02-3.29-2.5-4.03v8.05c1.48-.73 2.5-2.25 2.5-4.02zM14 3.23v2.06c2.89.86 5 3.54 5 6.71s-2.11 5.85-5 6.71v2.06c4.01-.91 7-4.49 7-8.77s-2.99-7.86-7-8.77z"/>
        </svg>
      </button>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted, onUnmounted } from 'vue'

export default {
  name: 'MusicPlayer',
  setup() {
    const audio = new Audio()
    audio.src = '/music.mp3'
    audio.volume = 1

    const isPlaying = ref(false)
    const currentTime = ref('0:00')
    const progress = ref(0)

    const togglePlay = () => {
      if (isPlaying.value) {
        audio.pause()
      } else {
        audio.play()
      }
      isPlaying.value = !isPlaying.value
    }

    const updateProgress = () => {
      if (audio.duration) {
        const prog = (audio.currentTime / audio.duration) * 100
        progress.value = prog
        updateTimeDisplay()
      }
    }

    const updateTimeDisplay = () => {
      const minutes = Math.floor(audio.currentTime / 60)
      const seconds = Math.floor(audio.currentTime % 60)
      currentTime.value = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`
    }

    const setProgress = (e) => {
      const rect = e.currentTarget.getBoundingClientRect()
      const clickX = e.clientX - rect.left
      const width = rect.width
      const duration = audio.duration
      if (duration) {
        audio.currentTime = (clickX / width) * duration
      }
    }

    const toggleMute = () => {
      audio.muted = !audio.muted
    }

    const songEnded = () => {
      isPlaying.value = false
      progress.value = 0
      updateTimeDisplay()
    }

    onMounted(() => {
      audio.addEventListener('timeupdate', updateProgress)
      audio.addEventListener('ended', songEnded)
    })

    onUnmounted(() => {
      audio.removeEventListener('timeupdate', updateProgress)
      audio.removeEventListener('ended', songEnded)
    })

    return {
      playing: isPlaying,
      currentTime,
      progress,
      togglePlay,
      setProgress,
      toggleMute,
      isMuted: computed(() => audio.muted)
    }
  }
}
</script>

<style scoped>
.music-player {
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(5px);
  border-radius: 30px;
  padding: 5px 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 260px;
}

.album-art {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  overflow: hidden;
  position: relative;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.2);
}

.vinyl {
  width: 100%;
  height: 100%;
  animation: spin 4s linear infinite;
  animation-play-state: paused;
}

.playing .vinyl {
  animation-play-state: running;
}

@keyframes spin {
  100% { transform: rotate(360deg); }
}

.controls {
  display: flex;
  align-items: center;
  gap: 6px;
  flex-grow: 1;
}

.play-btn, .mute-btn {
  width: 22px;
  height: 22px;
  background: #fff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
  border: none;
  padding: 0;
}

.play-btn:hover, .mute-btn:hover {
  transform: scale(1.1);
}

.mute-btn.muted {
  background: #ff4d4d;
}

.play-icon, .volume-icon {
  width: 14px;
  height: 14px;
}

.progress-container {
  flex-grow: 1;
  height: 4px;
  background: rgba(255, 255, 255, 0.3);
  border-radius: 2px;
  cursor: pointer;
}

.progress-bar {
  height: 100%;
  background: #409eff;
  border-radius: 2px;
  width: 0%;
  transition: width 0.1s;
}

.time {
  font-size: 12px;
  color: #409eff;
  min-width: 35px;
  text-align: center;
}
</style>
