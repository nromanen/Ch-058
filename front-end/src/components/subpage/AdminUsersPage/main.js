import Vue from 'vue';

const toLower = text => {
  return text.toString().toLowerCase()
}

const search = (users, str) => {
  if (str) {
    return users.filter(user => toLower(
      user.login
      + user.email
      + user.name
      + user.surname
      + user.type
    ).includes(toLower(str)))
  }

  return users
}

export default {
  name: "AdminUsersPage",
  data: () => ({
    searchString: null,
    selected: null,
    searched: [],
    users: []
  }),
  created: function() {
    Vue.http.get('users/')
      .then(response => {
        let json = response.body;

        if (!json.errors) {
          this.users = json.data;
          this.searched = this.users;
        } else if (json.errors.length) {
          // TODO: show error in snackBar
          console.log(JSON.stringify(json.errors));
        } else {
          // TODO: show Unexpected error in snackbar
          console.log('UNEXPECTED');
        }
      }, error => {
        // TODO: implement this shit, pls
        console.log(JSON.stringify(error.body));
      });
  },
  methods: {
    onSelect(user) {
      if (user) {
        this.$refs.aUserActionsDialog.show(user);

        this.selected = user;
      }
    },
    searchOnTable() {
      this.searched = search(this.users, this.searchString);
    }
  }
}
