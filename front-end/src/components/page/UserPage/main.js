import {getLocalUser} from "../../../router";
import {validationMixin} from "vuelidate";
import {maxLength, minLength, required} from "vuelidate/lib/validators/index";
import {
  MAX_NAME_LENGTH,
  MAX_SURNAME_LENGTH,
  MIN_NAME_LENGTH,
  MIN_SURNAME_LENGTH,
  NameValidator
} from "../../../_validator";

export default {
  name: "UserPage",
  props: {
    id: Number
  },
  mixins: [validationMixin],
  data: () => ({
    user: null,
    showBack: true,
    editMode: false,
    sending: false
  }),
  created: function () {
    this.$http.get('users/' + this.$route.params.id)
      .then(response => {
        let json = response.body;

        if (!json.errors) {
          this.user = json.data[0];
          this.user.initials = this.user.name.charAt(0) + this.user.surname.charAt(0);
        }
      }, error => {
        this.$router.push('/' + error.status);
      });
  }, validations: {
    user: {
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
    edit() {
      if (!this.editMode) {
        this.editMode = true;

        return;
      }

      if (this.validateCredentials()) {
        this.$http.put('users/edit', this.user).then(response => {
          let json = response.body;

          if (!json.errors) {
            this.editMode = false;
            this.user.name = json.data[0].name;
            this.user.surname = json.data[0].surname;
            this.user.initials = this.user.name.charAt(0) + this.user.surname.charAt(0);
          } else if (json.errors.length) {
            // TODO: show error
          } else {
            // TODO: show error
          }
        }, error => {
          // TODO: manage this
          // this.$router.push('/' + error.status);
        });
      }
    },
    validateCredentials() {
      this.$v.$touch();
      return !this.$v.$invalid;
    },
    getValidationClass(fieldName) {
      const field = this.$v.user[fieldName];

      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        };
      }
    },
    getLocalUser() {
      return getLocalUser();
    }
  }
}