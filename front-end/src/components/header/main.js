import Vue from 'vue';
import {getLocalUser, resetLocalUser} from "../../router";
import {ACCESS_DENIED, getErrorMessage, UNEXPECTED} from "../../_sys/json-errors";
import {getCurrentLang, switchLang} from "../../i18n";

export default {
  name: "Header",
  data: () => ({
    menuVisible: false,
    authDialog: false,
    login: null,
    error: null,
    showBack: false,
    user: null
  }),
  created: function () {
    this.showBack = this.$parent.$parent.$parent.$parent.showBack;
    if (getLocalUser()) {
      Vue.http.get('auth/getCurrentSession')
        .then(
          response => {
            let json = response.body;

            if (!json.errors) {
              if (json.data[0].logged_in) {
                this.user = json.data[0];
                this.login = json.data[0].login;
              }
            } else if (json.errors.length) {
              switch (json.errors[0].errno) {
                case ACCESS_DENIED:
                  this.$router.push('/403');
                  break;
                default:
                  this.error = getErrorMessage(json.errors[0]);

                  break;
              }
            } else {
              this.error = getErrorMessage(UNEXPECTED);
            }
          }, error => {
            switch (error.status) {
              case 403:
              case 404:
                this.$router.push('/' + error.status);
                break;
              default:
                let json = error.body;

                if (json.errors) {
                  error = getErrorMessage(json.errors[0]);
                }
            }

            if (!this.error) {
              this.error = 'HTTP error (' + error.status + ': ' + error.statusText + ')';
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
            this.login = null;

            resetLocalUser();
          } else if (json.errors.length) {
            switch (json.errors[0].errno) {
              case ACCESS_DENIED:
                this.$router.push('/403');
                break;
              default:
                this.error = getErrorMessage(json.errors[0]);

                break;
            }
          } else {
            this.error = getErrorMessage(UNEXPECTED);
          }
        }, error => {
          switch (error.status) {
            case 400:
            case 500:
              this.$router.push('/' + error.status);
              break;
            default:
              let json = error.body;

              if (json.errors) {
                this.error = getErrorMessage(json.errors[0]);
              }
          }

          if (!this.error) {
            this.error = 'HTTP error (' + error.status + ': ' + error.statusText + ')';
          }
        }
      )
    },
    hideSnackBar() {
      this.error = null;
    },
    getLangClass(lang) {
      return getCurrentLang() === lang ? 'md-primary' : '';
    },
    backToMap() {
      localStorage.setItem('redirectFromIssue', true);
      this.$router.push('/');
    },
    getLocalUser() {
      return getLocalUser();
    }
  }
}
