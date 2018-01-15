// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueResource from 'vue-resource'
import VueCookie from 'vue-cookie'
import VueMaterial from 'vue-material'
import Vuelidate from 'vuelidate'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/black-green-light.css'
import App from './App'
import SignInForm from '@/components/form/SignInForm/SignInForm'
import SignUpForm from '@/components/form/SignUpForm/SignUpForm'
import PassRecoveryForm from '@/components/form/PassRecoveryForm/PassRecoveryForm'
import AuthPage from '@/components/page/AuthPage/AuthPage'
import GoogleMap from '@/components/map/GoogleMap'
import Header from '@/components/header/Header'
import router from './router'
import i18n from './i18n'

Vue.use(VueResource)
Vue.use(VueCookie)
Vue.use(Vuelidate)
Vue.use(VueMaterial)
Vue.http.options.root = 'http://localhost:8080'
Vue.http.headers.common['Accept'] = 'application/json;charset=UTF-8'
Vue.http.headers.common['Content-Type'] = 'application/json;charset=UTF-8'
Vue.http.headers.common['Access-Control-Allow-Credentials'] = 'true'
Vue.http.options.credentials = true
Vue.component('sign-in-form', SignInForm)
Vue.component('sign-up-form', SignUpForm)
Vue.component('password-recovery-form', PassRecoveryForm)
Vue.component('auth-page', AuthPage)
Vue.component('map-page', GoogleMap)
Vue.component('header-page', Header)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  template: '<App/>',
  components: {App}
})
