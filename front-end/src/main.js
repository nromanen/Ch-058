// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueMaterial from 'vue-material'
import Vuelidate from 'vuelidate'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default-dark.css'
import App from './App'
import SignInPage from '@/components/pages/SignInPage/SignInPage'
import SignUpPage from '@/components/pages/SignUpPage/SignUpPage'
import router from './router/index'

Vue.use(Vuelidate)
Vue.use(VueMaterial)
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
