import Vue from 'vue'
import Router from 'vue-router'
import IndexPage from '@/components/page/IndexPage/IndexPage'
import AuthPage from '@/components/page/AuthPage/AuthPage'
import Chat from '@/components/page/ChatPage/ChatPage'
import AdminChatNotification from '@/components/AdminChatNotification/AdminChatNotification'
import OpenChat from '@/components/OpenChat/OpenChat'
import AdminChatPage from '@/components/page/AdminChatPage/AdminChatPage'
import Issue from '@/components/page/ViewIssue/App'
import SocialSuccessPage from '@/components/page/SocialSuccessPage/SocialSuccessPage'
import AdminPage from '@/components/page/AdminPage/AdminPage'
import AdminUsersPage from '@/components/subpage/AdminUsersPage/AdminUsersPage'
import AdminIssuesPage from '@/components/subpage/AdminIssuesPage/AdminIssuesPage'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'IndexPage',
      component: IndexPage,
      meta: {
        checkSuccessRegistration: true
      }
    },
    {
      path: '/auth**',
      name: 'AuthPage',
      component: AuthPage,
      meta: {
        requiresAnonymous: true
      }
    },
    {
      path: '/socialSuccess**',
      name: 'SocialSuccessPage',
      component: SocialSuccessPage,
      meta: {
        requiresAnonymous: true
      }
    },
    {
      path: '/chat/:issueId/:userId',
      name: 'ChatPage',
      component: Chat
    },
    {
      path: '/openChat',
      name: 'OpenChat',
      component: OpenChat
    },
    {
      path: '/adminChatPage/:issueId/:userId',
      name: 'AdminChatPage',
      component: AdminChatPage
    },
    {
      path: '/issue/:id',
      component: Issue
    },
    {
      path: '/admin',
      name: 'AdminPage',
      component: AdminPage,
      children: [
        {
          path: '/',
          component: AdminUsersPage
        },
        {
          path: 'users',
          component: AdminUsersPage
        },
        {
          path: 'issues',
          component: AdminIssuesPage
        },
        {
          path: 'issues/:user',
          component: AdminIssuesPage,
          props: true
        },
        {
          path: 'notification',
          component: AdminChatNotification
        }
      ]
    }
  ]
})

export default router

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAnonymous)) {
    Vue.http.get('auth/getCurrentSession').then(response => {
      let json = response.body

      if (!json.errors && json.data[0].logged_in && !json.data[0].login.match(/.*(google)|(facebook).*/)) {
        localStorage.setItem('user', JSON.stringify(json.data[0]))
        next({
          path: '/',
          query: {
            redirect: to.fullPath
          }
        })
      } else {
        next()
      }
    })
  } else if (to.matched.some(record => record.meta.requiresAuth)) {
    Vue.http.get('auth/getCurrentSession').then(response => {
      let json = response.body

      if (!json.errors && json.data[0].login) {
        localStorage.setItem('user', JSON.stringify(json.data[0]))
        next()
      } else {
        next({
          path: '/auth/login',
          query: {
            redirect: to.fullPath
          }
        })
      }
    })
  } else if (to.matched.some(record => record.meta.checkSuccessRegistration)) {
    Vue.http.get('auth/getCurrentSession').then(response => {
      let json = response.body

      if (!json.errors && json.data[0].login) {
        if (json.data[0].login.match(/.*(google)|(facebook).*/)) {
          next({
            path: '/socialSuccess',
            query: {
              redirect: to.fullPath
            }
          })
        }
      }
      next()
    }, error => {
      console.log(error)
      next()
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
