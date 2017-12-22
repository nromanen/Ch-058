import {validationMixin} from 'vuelidate'
import {minLength, required} from 'vuelidate/lib/validators'

export default {
  name: 'SignInPage',
  mixins: [validationMixin],
  data: () => ({
    form: {
      login: null,
      password: null,
    },
    sending: false
  }),
  validations: {
    form: {
      login: {
        required,
        minLength: minLength(4)
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
      this.sending = true

      // Instead of this timeout, here you can call your API
      window.setTimeout(() => {
        this.sending = false
        this.clearForm()
      }, 1500)
    },
    validateCredentials() {
      this.$v.$touch()

      if (!this.$v.$invalid) {
        this.authorize()
      }
    }
  }
}
