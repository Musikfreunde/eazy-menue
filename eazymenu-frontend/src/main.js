import App from './App.vue'
import VueHtmlToPaper from 'vue-html-to-paper'
import './registerServiceWorker'
import router from './router'
import store from './store'
import Vue from 'vue'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import keycloakplugin from './keycloakplugin'
import { api } from './api'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

const options = {
  name: '_blank',
  specs: ['fullscreen=yes'],
  styles: [
    'https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css',
    'https://unpkg.com/kidlat-css/css/kidlat.css'
  ],
  autoClose: true
}
Vue.config.productionTip = false

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(keycloakplugin)
Vue.use(api)

Vue.use(VueHtmlToPaper, options)
Vue.$keycloak
  .init({
    onLoad: 'login-required',
    checkLoginIframe: false
  })
  .then(() => {
    new Vue({
      router,
      store,
      render: h => h(App)
    }).$mount('#app')
  })
