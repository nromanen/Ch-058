import Vue from 'vue'
import Router from 'vue-router'
import IndexPage from '@/components/page/IndexPage/IndexPage'
import AuthPage from '@/components/page/AuthPage/AuthPage'
import SignInPage from '@/components/page/SignInPage/SignInPage'
import SignUpPage from '@/components/page/SignUpPage/SignUpPage'
import Issue from '@/components/ViewIssue/App'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'IndexPage',
      component: IndexPage
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

// eslint-disable-next-line
export function getLocalUser() {
  return JSON.parse(localStorage.getItem('user'))
}

// eslint-disable-next-line
export function resetLocalUser() {
  localStorage.setItem('user', null)
}
