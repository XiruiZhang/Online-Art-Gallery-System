import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Welcome from '@/components/Welcome'
import CustomerRegister from '@/components/CustomerRegister'
import CustomerLogin from '@/components/CustomerLogin'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome
    },
    {
      path: '/customer-register',
      name: 'CustomerRegister',
      component: CustomerRegister
    },
    {
      path: '/customer-login',
      name: 'CustomerLogin',
      component: CustomerLogin
    }
  ]
})
