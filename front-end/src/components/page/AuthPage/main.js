export default {
  name: "AuthPage",
  methods: {
    setLogin(login) {
      this.$refs.signInForm.form.login = login;
    }
  }
}
