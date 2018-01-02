import Vue from 'vue'
import Router from 'vue-router'
import IndexPage from '@/components/page/IndexPage/IndexPage'
import AuthPage from '@/components/page/AuthPage/AuthPage'
import Map from '@/components/map/GoogleMap'
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
      path: '/issue/:id',
      component: Issue
    },
    {
      path: '/auth**',
      name: 'AuthPage',
      component: AuthPage
    }
  ]
})

export default router

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    Vue.http.get('auth/login').then(response => {
      let json = response.body

      if (!json.errors && json.data[0].login) {
        localStorage.setItem('user', JSON.stringify(json.data[0]))
        next({
          path: '/auth/login',
          query: {
            redirect: to.fullPath
          }
        })
      } else {
        next()
      }
    })
  } else {
    next()
  }
})
// eslint-disable-next-line
export function getLocalUser() {
  return JSON.parse(localStorage.getItem('user'))
}

// eslint-disable-next-line
export function resetLocalUser() {
  localStorage.setItem('user', null)
}
