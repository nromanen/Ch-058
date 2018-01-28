export default {
  name: "admin-user-actions-dialog",
  data: () => ({
    showDialog: false,
    user: null
  }),
  methods: {
    show(user) {
      this.showDialog = true;
      this.user = user;
    },
    banUser(ban) {
      this.$http.put('admin/users/' + this.user.id + '/' + (ban ? 'BANNED' : 'USER'))
        .then(response => {
          let json = response.body;

          if (!json.errors) {
            this.$parent.users[this.$parent.users.indexOf(this.user)].type = (ban ? 'BANNED' : 'USER');
            this.$parent.searched = this.$parent.users;
            // } else if (json.errors.length) {
            // TODO: show error in snackBar
            // console.log(JSON.stringify(json.errors));
          } else {
            // TODO: show Unexpected error in snackbar
            console.log('UNEXPECTED');
          }
        }, error => {
          console.log(JSON.stringify(error.body));
        });
    },
    grantUser(grant) {
      this.$http.put('admin/users/' + this.user.id + '/' + (grant ? 'ADMIN' : 'USER'))
        .then(response => {
          let json = response.body;

          if (!json.errors) {
            this.$parent.users[this.$parent.users.indexOf(this.user)].type = (grant ? 'ADMIN' : 'USER');
            this.$parent.searched = this.$parent.users;
            // } else if (json.errors.length) {
            // TODO: show error in snackBar
            // console.log(JSON.stringify(json.errors));
          } else {
            // TODO: show Unexpected error in snackbar
            console.log('UNEXPECTED');
          }
        }, error => {
          console.log(JSON.stringify(error.body));
        });
    },
    showUserIssues(user) {
      this.$router.push('/admin/issues/' + user.login)
    }
  }
}
