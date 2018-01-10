import Vue from 'vue'

export default {
  name: "index-page",
  data: () => ({
    menuVisible: false,
    snackBarText: null
  }),
  methods: {
    hideSnackBar() {
      this.snackBarText = null;
    }
  }
}
