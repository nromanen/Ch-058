import {getLocalUser, resetLocalUser} from "../../../router";
import Vue from 'vue'

export default {
  name: "index-page",
  data: () => ({
    menuVisible: false,
    authDialog: false,
    userEmail: null
  }),
  created: function () {
    Vue.http.post('users/get', null, {
      params: {
        id: getLocalUser().id
      }
    }).then(
      response => {
        let json = response.body

        if (json.result === 0) {
          this.userEmail = json.data[0].email
        } else if (json.error) {
          // TODO: show json.error.errmsg
        } else {
          // TODO: show 'We have unexpected error'
        }
      }
    )
  },
  methods: {
    logout() {
      this.$http.post('auth/logout').then(
        response => {
          let json = response.body

          if (json.result === 0 && json.data[0].logged_in === false) {
            this.userEmail = null

            resetLocalUser()
          } else if (json.error) {
            // TODO: show json.error.errmsg
          } else {
            // TODO: show 'We have unexpected error'
          }
        }
      )
    }
  }
}
