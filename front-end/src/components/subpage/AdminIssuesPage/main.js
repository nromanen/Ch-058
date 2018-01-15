import Vue from 'vue';

const toLower = text => {
  return text.toString().toLowerCase()
}

const search = (issues, str) => {
  if (str) {
    return issues.filter(issue => toLower(
      issue.title
      + issue.text
      + issue.type
      + issue.closed
      + issue.createdAt
    ).includes(toLower(str)))
  }
  return issues
}

export default {
  name: "AdminIssuesPage",
  data: () => ({
    searchString: null,
    selected: null,
    searched: [],
    issues: []
  }),
  created: function() {
    Vue.http.get('issues/getAll')
      .then(response => {
        let json = response.body;

        if (!json.errors) {
          this.issues = json.data;
          this.searched = this.issues;
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
    onSelect(issue) {
      if (issue) {
        this.$refs.aIssueActionsDialog.show(issue);

        this.selected = issue;
      }
    },
    searchOnTable() {
      this.searched = search(this.issues, this.searchString);
    }
  }
}
