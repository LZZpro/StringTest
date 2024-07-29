<template>
  <div id="app">
    <div class="clock-container">
      <h1>Alien Clock</h1>
      <div class="time-display">
        <p>Current Alien Time: {{ alienTime }}</p>
        <p>Current Earth Time: {{ earthTime }}</p>
      </div>

      <h2>Set Alien Time</h2>
      <div class="input-group">
        <input placeholder="2820-06-32 12:00:00" size="20" v-model="userTime">
        <button @click="setAlienTime">设置外星时钟</button>
        <button @click="resetAlienTime">重置外星时钟</button>
      </div>

      <h2>Set Alien Time Alarm</h2>
      <div class="input-group">
        <input placeholder="2820-06-32 12:00:00" v-model="alarmTime">
        <el-button :plain="true" @click="setAlienAlarm">设定闹钟</el-button>
        <audio id="myAudio" src="/audio/TangTeacherWakeUp.m4a" controls autoplay></audio>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus'

export default {
  data() {
    return {
      alienTime: '',
      earthTime: '',
      userTime: '',
      isAlarm: false,
      alarmTime: ''
    };
  },
  methods: {
    fetchAlienTime() {
      axios.get('/test/api/alien-time')
        .then(res => {
          this.alienTime = res.data.data.alienTime;
          this.earthTime = res.data.data.earthTime;
        });
    },
    setAlienAlarm() {
      axios.post('/test/api/validateTime', { alarmTime: this.alarmTime })
        .then(res => {
          if (res.data.data) {
            ElMessage({
              message: '闹钟设置成功',
              type: 'success',
            })
          }else{
            ElMessage.error(res.data.msg)
          }
        });

    },
    //设置外星时间为指定时间
    setAlienTime() {
      axios.post('/test/api/set-alien-time', { userTime: this.userTime })
        .then(res => {
          if (res.data.code !== 200) {
            ElMessage.error(res.data.msg)
          } else {
            ElMessage({
              message: '设置成功',
              type: 'success',
            })
            this.fetchAlienTime();
          }
        });
    },
    //重置外星时钟为当前外星时间
    resetAlienTime() {
      axios.get('/test/api/reset-alien-time')
        .then(res => {
          this.alienTime = res.data.data.alienTime;
          ElMessage({
              message: '重置成功',
              type: 'success',
            })
        });
    },
  },
  mounted() {
    this.fetchAlienTime();
    setInterval(this.fetchAlienTime, 1000);
  },
  watch: {
    //闹钟监听
    alienTime(old, newAlienTime) {
      if (newAlienTime === this.alarmTime) {
        //音量50%
        document.querySelector('audio').volume = 0.5;
        //循环播放
        document.querySelector('audio').loop = true;
        document.querySelector('audio').play();
      }
    }
  }
};
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  background-color: #f0f4f8;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.clock-container {
  background-color: #ffffff;
  padding: 20px 60px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  font-size: 2.5em;
  margin-bottom: 20px;
  color: #34495e;
}

.time-display p {
  font-size: 1.2em;
  margin: 10px 0;
}

h2 {
  margin-top: 20px;
  color: #34495e;
}

.input-group {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

input[type="datetime-local"] {
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  font-size: 1em;
  color: white;
  background-color: #3498db;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #2980b9;
}
</style>