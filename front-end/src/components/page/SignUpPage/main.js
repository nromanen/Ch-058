import {email, maxLength, minLength, required, sameAs} from "vuelidate/lib/validators/index";
import {validationMixin} from "vuelidate";
import {
  LoginValidator, MAX_LOGIN_LENGTH, MAX_NAME_LENGTH, MAX_SURNAME_LENGTH, MIN_LOGIN_LENGTH, MIN_NAME_LENGTH,
  MIN_SURNAME_LENGTH, NameValidator
} from "../../_validator";
import {getErrorMessage, UNEXPECTED} from "../../_sys/json-errors";

export default {
  name: "SignUpPage",
  mixins: [validationMixin],
  data: () => ({
    form: {
      login: null,
      password: null,
      confirmPassword: null,
      email: null,
      name: null,
      surname: null,
    },
    sending: false,
    errors: null,
    showSnackBar: false
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
      },
      confirmPassword: {
        required,
        sameAs: sameAs('password')
      },
      email: {
        required,
        email
      },
      name: {
        required,
        minLength: minLength(MIN_NAME_LENGTH),
        maxLength: maxLength(MAX_NAME_LENGTH),
        NameValidator
      },
      surname: {
        required,
        minLength: minLength(MIN_SURNAME_LENGTH),
        maxLength: maxLength(MAX_SURNAME_LENGTH),
        NameValidator
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
    register() {
      this.errors = []
      this.sending = true

      this.$http.post('http://localhost:8080/auth/signUp',
        {
          login: this.form.login,
          email: this.form.email,
          password: this.form.password,
          name: this.form.name,
          surname: this.form.surname
        }).then(
        response => {
          let json = response.body

          if (json.result === 0) {
            this.showSnackBar = true
          } else if (json.error || json.error.errno !== UNEXPECTED) {
            this.errors.push(getErrorMessage(json.error))
          } else {
            this.errors.push('We have unexpected error')
          }

          this.sending = false
        }, error => {
          if (error.status === 400) {
            let json = error.body

            if (json.error) {
              this.errors.push(getErrorMessage(json.error))
            }
          }

          if (this.errors.length <= 0) {
            this.errors.push('HTTP error (' + error.status + ': ' + error.statusText + ')')
          }

          this.sending = false
        }
      )
    },
    snackBarAction() {
      this.$router.push('login')

      this.showSnackBar = false
    }
    ,
    validateCredentials() {
      this.$v.$touch()

      if (!this.$v.$invalid) {
        this.register()
      }
    }
  }
}