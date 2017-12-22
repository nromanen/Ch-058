import {minLength, required} from "vuelidate/lib/validators/index";
import {validationMixin} from "vuelidate";

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
      },
      confirmPassword: {
        required
      },
      email: {
        required
      },
      name: {
        required
      },
      surname: {
        required
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
