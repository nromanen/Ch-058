import Vue from 'vue'
import Router from 'vue-router'
import AuthPage from '@/components/page/AuthPage/AuthPage'
import SignInPage from '@/components/page/SignInPage/SignInPage'
import SignUpPage from '@/components/page/SignUpPage/SignUpPage'
import Map from '@/components/map/GoogleMap'

Vue.use(Router)

export default new Router({
  routes: [
    /* {
      path: '/',
      name: 'Home',
      component: Home
    }, */
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
    },
    {
      path: '/map',
      name: 'Map',
      component: Map
    }
  ]
})
