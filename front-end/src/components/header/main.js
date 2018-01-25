import Vue from 'vue';
import {getLocalUser, resetLocalUser} from "../../router";
import {getErrorMessage, UNEXPECTED} from "../../_sys/json-errors";
import {getCurrentLang, switchLang} from "../../i18n";

export default {
  name: "index-page",
  data: () => ({
	menuVisible: false,
	authDialog: false,
	login: null,
	snackBarText: null,
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
			this.login = null;

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
	hideSnackBar() {
	  this.snackBarText = null;
	},
	getLangClass(lang) {
	  return getCurrentLang() === lang ? 'md-primary' : '';
	},
	backToMap() {
	  localStorage.setItem('redirectFromIssue', true);
	  this.$router.push('/');
	}
  }
}
