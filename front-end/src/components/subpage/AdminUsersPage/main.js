import Vue from 'vue';

const toLower = text => {
  return text.toString().toLowerCase()
};

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
};

var debounce = require('debounce');

export default {
  name: "AdminUsersPage",
  data: () => ({
    page: 0,
    size: 10,
    sort: 'id,asc',
    totalPages: null,
    searchString: null,
    selected: null,
    searched: [],
    users: []
  }),
  created: function() {
    this.load(this.page);
  },
  methods: {
    load(page, size, sort) {
      Vue.http.get('admin/users/', {params: {page: page, size: size, sort: sort}}).then(response => {
        let json = response.body;

        if (!json.errors) {
          this.users = json.data;
          this.searched = this.users;
          this.totalPages = json.count / this.size;
          this.totalPages = (this.totalPages - Math.floor(this.totalPages) ? (this.totalPages | 0) + 1 : this.totalPages | 0);
          // } else if (json.errors.length) {
          //   TODO: show error in snackBar
          //   console.log(JSON.stringify(json.errors));
        } else {
          // TODO: show Unexpected error in snackbar
          console.log('UNEXPECTED');
        }
      }, error => {
        // TODO: implement this shit, pls
        console.log(JSON.stringify(error.body));
      })
    },
    onSelect(user) {
      if (user) {
        this.$refs.aUserActionsDialog.show(user);

        this.selected = user;
      }
    },
    searchOnTable() {
      this.searched = search(this.users, this.searchString);
    },
    search: debounce(function () {
      if (!this.searchString) {
        this.load(this.page, this.size, this.sort);
        return;
      }
      this.$http.get('admin/users/search/' + encodeURIComponent(this.searchString), {
        params: {
          page: this.page,
          size: 10,
          sort: null
        }
      }).then(response => {
        let json = response.body;

        if (!json.errors) {
          this.users = json.data;
          this.searched = this.users;
          this.totalPages = json.count / this.size;
          this.totalPages = (this.totalPages - Math.floor(this.totalPages) ? (this.totalPages | 0) + 1 : this.totalPages | 0);
          // } else if (json.errors.length) {
          //   TODO: show error in snackBar
          // console.log(JSON.stringify(json.errors));
        } else {
          // TODO: show Unexpected error in snackbar
          console.log('UNEXPECTED');
        }
      }, error => {
        // TODO: implement this shit, pls
        console.log(JSON.stringify(error.body));
      });
    }, 500)
  }
}
