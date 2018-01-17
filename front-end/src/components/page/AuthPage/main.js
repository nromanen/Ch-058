import {getCurrentLang, switchLang} from "../../../i18n";

export default {
  name: "AuthPage",
  methods: {
    setLogin(login) {
      this.$refs.signInForm.form.login = login;
    },
    switchLang(lang) {
      switchLang(lang);
    },
    getLangClass(lang) {
      return getCurrentLang() === lang ? 'md-primary' : '';
    }
  }
}
