// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueResource from 'vue-resource'
import VueMaterial from 'vue-material'
import Vuelidate from 'vuelidate'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default-dark.css'
import App from './App'
import SignInPage from '@/components/page/SignInPage/SignInPage'
import SignUpPage from '@/components/page/SignUpPage/SignUpPage'
import router from './router/index'

Vue.use(VueResource)
Vue.use(Vuelidate)
Vue.use(VueMaterial)
Vue.http.headers.common['Accept'] = 'application/json;charset=UTF-8'
Vue.http.headers.common['Content-Type'] = 'application/json;charset=UTF-8'
Vue.config.productionTip = false
Vue.component('sign-in-page', SignInPage)
Vue.component('sign-up-page', SignUpPage)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: {App}
})

// Custom validators
