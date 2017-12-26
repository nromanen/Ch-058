import {validationMixin} from 'vuelidate'
import {maxLength, minLength, required} from 'vuelidate/lib/validators'
import {LoginValidator, MAX_LOGIN_LENGTH, MIN_LOGIN_LENGTH} from "../../_validator/LoginValidator";

export default {
  name: 'SignInPage',
  mixins: [validationMixin],
  data: () => ({
    form: {
      login: null,
      password: null,
    },
    sending: false,
    errors: null
  }),
  validations: {
    form: {
      login: {
        required,
        minLength: minLength(MIN_LOGIN_LENGTH),
        maxLength: maxLength(MAX_LOGIN_LENGTH),
        LoginValidator
      },
      password: {
        required,
        minLength: minLength(8)
      }
    }
  },
  methods: {
    getValidationClass(fieldName) {
      const field = this.$v.form[fieldName];

      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },
    authorize() {
      this.errors = []
      this.sending = true

      this.$http.post('http://localhost:8080/auth/login?login='
        + this.form.login
        + '&password=' + this.form.password,
      ).then(
        response => {
          let resp = response.body

          if (resp.result === 0) {
            // TODO: implement some code here
          } else if (resp.error) {
            this.errors.push(resp.error.errmsg)
          } else {
            this.errors.push('We have unexpected error')
          }

          this.sending = false
        }, error => {
          if (error.status === 400) {
            let resp = error.body

            if (resp.error) {
              this.errors.push(resp.error.errmsg)
            }
          }

          if (this.errors.length <= 0) {
            this.errors.push('HTTP error (' + error.status + ': ' + error.statusText + ')')
          }

          this.sending = false
        }
      )

    },
    validateCredentials() {
      this.$v.$touch()

      if (!this.$v.$invalid) {
        this.authorize()
      }
    }
  }
}
