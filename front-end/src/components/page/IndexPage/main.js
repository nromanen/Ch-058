import {getLocalUser, resetLocalUser} from "../../../router";
import Vue from 'vue'
import {getErrorMessage, UNEXPECTED} from "../../_sys/json-errors";

export default {
  name: "index-page",
  data: () => ({
    menuVisible: false,
    authDialog: false,
    userEmail: null,
    snackBarText: null
  }),
  created: function () {
    Vue.http.get('users/get/' + getLocalUser().id)
      .then(
        response => {
          let json = response.body;

          if (!json.errors) {
            this.userEmail = json.data[0].email;
          } else if (json.errors.length) {
            this.snackBarText = getErrorMessage(json.errors[0]);
          } else {
            this.snackBarText = getErrorMessage(UNEXPECTED);
          }
        }, error => {
          switch (error.status) {
            case 400:
            case 500:
              let json = error.body;

              if (json.errors) {
                this.snackBarText = getErrorMessage(json.errors[0]);
              }
          }

          if (!this.errors.length) {
            this.errors.push('HTTP error (' + error.status + ': ' + error.statusText + ')');
          }
        }
      )
  },
  methods: {
    logout() {
      this.$http.post('auth/logout').then(
        response => {
          let json = response.body;

          if (!json.errors && json.data[0].logged_in === false) {
            this.userEmail = null;

            resetLocalUser();
          } else if (json.errors.length) {
            this.snackBarText = getErrorMessage(json.errors[0]);
          } else {
            this.snackBarText = getErrorMessage(UNEXPECTED);
          }
        }, error => {
          switch (error.status) {
            case 400:
            case 500:
              let json = error.body;

              if (json.errors) {
                this.snackBarText = getErrorMessage(json.errors[0]);
              }
          }

          if (!this.errors.length) {
            this.errors.push('HTTP error (' + error.status + ': ' + error.statusText + ')');
          }
        }
      )
    },
    hideSnackBar() {
      this.snackBarText = null;
    }
  }
}
