import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/pages/HelloWorld/HelloWorld'
import AuthPage from '@/components/pages/AuthPage/AuthPage'
import SignInPage from '@/components/pages/SignInPage/SignInPage'
import SignUpPage from '@/components/pages/SignUpPage/SignUpPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/auth**',
      name: 'AuthPage',
      component: AuthPage
    },
    {
      path: '/login',
      name: 'SignInPage',
      component: SignInPage
    },
    {
      path: '/signUp',
      name: 'SignUpPage',
      component: SignUpPage
    }
  ]
})
