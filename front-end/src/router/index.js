import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/pages/HelloWorld/HelloWorld'
import SignInPage from '@/components/pages/SignInPage/SignInPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'SignInPage',
      component: SignInPage
    }
  ]
})
