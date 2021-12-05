import Vue from 'vue'
import Vuex from 'vuex'
import BestellungUserDto from '../model/BestellungUserDto'
import { api } from '../api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    menueTimes: [],
    menues: [],
    currentMenues: [],
    kantinenOrders: [],
    userOrders: BestellungUserDto
  },
  mutations: {
    setMenueTimes (state, arr) {
      state.menueTimes = arr
    },
    setMenues (state, arr) {
      state.menues = arr
    },
    setCurrentMenues (state, m) {
      state.currentMenues = m
    },
    setUserOrders (state, o) {
      state.userOrders = o
    },
    setKantinenOrders (state, o) {
      state.kantinenOrders = o
    },
    async setFreePlaces (state) {
      // for-i genommen, da forEach nicht funktioniert hat
      for (let i = 0; i < state.menueTimes.length; i++) {
        const response = await api.getFreePlaceForTime(state.currentMenues[0].date, state.menueTimes[i].id)
        state.menueTimes[i].freeSeats = response.data
      }
    }
  },
  actions: {

  },
  modules: {
  },
  getters: {
    getMenueTimes: state => {
      return state.menueTimes
    },
    getMenues: state => {
      return state.menues
    },
    getCurrentMenues: state => {
      return state.currentMenues
    },
    getCurrentMenueForCode: (state) => (code) => {
      return state.currentMenues.find(m => m.code === code)
    },
    getCurrentMenueById: (state) => (id) => {
      return state.currentMenues.find(m => m.id === id)
    },
    getUserOrders: state => {
      return state.userOrders
    },
    getKantinenOrders: state => {
      return state.kantinenOrders
    },
    getCountForCode: (state) => (code) => {
      return state.kantinenOrders.filter(k => k.code === code).reduce((a, b) => a + b.menueCounter, 0)
    },
    getCountForAll: (state) => {
      return state.kantinenOrders.reduce((a, b) => a + b.menueCounter, 0)
    },
    getCountForTime: (state) => (time, code) => {
      console.log(state.kantinenOrders[0])
      return state.kantinenOrders.filter(k => k.timewindow === time && code === k.code).reduce((a, b) => a + b.menueCounter, 0)
    }
  }
})
