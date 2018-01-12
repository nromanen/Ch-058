import Vue from 'vue';

const toLower = text => {
  return text.toString().toLowerCase()
}

const searchByName = (items, term) => {
  if (term) {
    return items.filter(item => toLower(
      item.login
      + item.email
      + item.name
      + item.surname
      + item.type
    ).includes(toLower(term)))
  }

  return items
}

export default {
  name: "AdminUsersPage",
  data: () => ({
    searchString: null,
    selected: null,
    searched: [],
    users: [],
    showBanUserDialog: false
  }),
  created: function() {
    Vue.http.get('users/getAll')
      .then(response => {
        let json = response.body;

        if (!json.errors) {
          this.users = json.data;
          this.searched = this.users
        } else if (json.errors.length) {
          // TODO: show error in snackBar
        } else {
          // TODO: show Unexpected error in snackbar
        }
      }, error => {
        // TODO: implement this shit, pls
      });
  },
  methods: {
    onSelect (item) {
      if (this.selected !== item) {
        this.showBanUserDialog = true;
        this.selected = item;
      }
    },
    onConfirmBanUser() {
      this.$http.put('admin/ban', null, {
        params: {
          id: this.selected.id
        }
      })
        .then(response => {

        }, error => {

        });
    },
    searchOnTable () {
      this.searched = searchByName(this.users, this.searchString);
    }
  }
};
