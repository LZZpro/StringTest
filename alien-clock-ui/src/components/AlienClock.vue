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
        <input type="datetime-local" :step="1"  v-model="userTime">
        <button @click="setAlienTime">Set Alien Time</button>
        <button @click="resetAlienTime">Reset Alien Time</button>
      </div>

      <h2>Set Alien Time Alarm</h2>
      <div class="input-group">
        <input type="datetime-local" :step="1"  v-model="userTime">
        <button @click="setAlienAlarm">Set Alien Alarm(//todo)</button>

      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      alienTime: '',
      earthTime: '',
      userTime: ''
    };
  },
  methods: {
    fetchAlienTime() {
      axios.get('/test/api/alien-time')
        .then(response => {
          this.alienTime = response.data.alienTime;
          this.earthTime = response.data.earthTime;
        })
        .catch(error => {
          console.error(error);
        });
    },
    setAlienTime() {
      axios.post('/test/api/set-alien-time', { userTime: this.userTime })
        .then(response => {
          this.fetchAlienTime();
        })
        .catch(error => {
          console.error(error);
        });
    },
    resetAlienTime(){
      axios.get('/test/api/reset-alien-time')
      .then(response=>{
        this.alienTime = response.data.alienTime;
      })
      .catch(error=>{
        console.error(error);
      })
    }
  },
  mounted() {
    this.fetchAlienTime();
    setInterval(this.fetchAlienTime, 500);
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