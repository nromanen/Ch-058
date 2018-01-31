import Vue from 'vue';

const toLower = text => {
  return text.toString().toLowerCase()
};

const search = (issues, str) => {
  if (str) {
    return issues.filter(issue => toLower(
      issue.title
      + issue.text
      + issue.author.login
      + issue.createdAt
      + issue.type.name
    ).includes(toLower(str)))
  }
  return issues
};

export default {
  name: "AdminIssuesPage",
  data: () => ({
    page: 0,
    size: 10,
    sort: 'id,asc',
    totalPages: null,
    searchString: null,
    selected: null,
    searched: [],
    issues: []
  }),
  props: ['issue'],
  created: function() {
    this.load(this.page);
  },
  methods: {
    load(page) {
      Vue.http.get('admin/issues/', {params: {page: page, size: this.size, sort: this.sort}}).then(response => {
        let json = response.body;

        if (!json.errors) {
          this.issues = json.data;
          this.searched = this.issues;
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
      })
    },
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
