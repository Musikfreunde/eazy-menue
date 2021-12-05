import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Uebersicht',
    meta: {
      isAuthenticated: true
    },
    component: () => import('../views/Uebersicht')
  },
  {
    path: '/bestellung/:menueId',
    name: 'Bestellung',
    meta: {
      isAuthenticated: true
    },
    component: () => import('../views/Bestellung')
  },
  {
    path: '/verlauf',
    name: 'Verlauf',
    meta: {
      isAuthenticated: true
    },
    component: () => import('../views/Verlauf')
  },
  {
    path: '/drucken',
    name: 'Drucken',
    meta: {
      isAuthenticated: true
    },
    component: () => import('../views/Drucken')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.name === 'Print' && Vue.$keycloak.hasRealmRole('mitarbeiter')) {
    next({ name: 'Ubersicht' })
  }
  if (to.name === 'Verlauf' && Vue.$keycloak.hasRealmRole('kantine')) {
    next({ name: 'Ubersicht' })
  }
  next()
})

export default router
