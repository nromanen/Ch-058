import {getLocalUser, resetLocalUser} from "../../../router";
import {getErrorMessage, UNEXPECTED} from "../../../_sys/json-errors";
import Vue from "vue";
import {getCurrentLang, switchLang} from "../../../i18n";
import {getServerAddress} from "../../../main";

export default {
  name: 'AdminIndexPage',
  data: () => ({
    menuVisible: false,
    user: null
  }),
  created: function() {
    if (getLocalUser()) {
      Vue.http.get('auth/getCurrentSession')
        .then(
          response => {
            let json = response.body;

            if (!json.errors) {
              if (json.data[0].logged_in) {
                this.user = json.data[0];

                Vue.http.get('users/' + getLocalUser().id)
                  .then(
                    response => {
                      let json = response.body;

                      if (!json.errors) {
                        if (json.data[0]) {
                          this.user.initials = json.data[0].name.charAt(0) + json.data[0].surname.charAt(0);
                          this.user.image = json.data[0].image;
                        }
                      } else if (json.errors.length) {
                        this.snackBarText = getErrorMessage(json.errors[0]);
                      } else {
                        this.snackBarText = getErrorMessage(UNEXPECTED);
                      }
                    }
                  );
              }
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

            if (!this.snackBarText) {
              this.snackBarText = 'HTTP error (' + error.status + ': ' + error.statusText + ')';
            }
          }
        );
    }
  },
  methods: {
    switchLang(lang) {
      switchLang(lang);
    },
    logout() {
      this.$http.post('auth/logout').then(
        response => {
          let json = response.body;

          if (!json.errors && json.data[0].logged_in === false) {
            this.user = null;

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

          if (!this.snackBarText) {
            this.snackBarText = 'HTTP error (' + error.status + ': ' + error.statusText + ')';
          }
        }
      )
    },
    getLangClass(lang) {
      return getCurrentLang() === lang ? 'md-primary' : '';
    },
    getServerAddress() {
      return getServerAddress();
    }
  }
}
