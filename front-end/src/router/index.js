import Vue from 'vue'
import Router from 'vue-router'
import AuthPage from '@/components/page/AuthPage/AuthPage'
import SignInPage from '@/components/page/SignInPage/SignInPage'
import SignUpPage from '@/components/page/SignUpPage/SignUpPage'

import Issue from '@/components/ViewIssue/App'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/issue/:id',
      component: Issue
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
