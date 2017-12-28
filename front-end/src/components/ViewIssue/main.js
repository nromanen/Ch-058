import Vue from 'vue';
import VueMaterial from './../../../node_modules/vue-material'
import './../../../node_modules/vue-material/dist/vue-material.css'
import VueResource from 'vue-resource';
import { VTooltip } from 'v-tooltip'

Vue.directive('my-tooltip', VTooltip)
Vue.use(VueMaterial)
Vue.use(VueResource)

export default {
  data () {
    return {
      center: {lat: 10.0, lng: 11.0},
      markers: [{
        position: {lat: 10.0, lng: 11.1}
      }],
      title:'',
      text:'',
      isLiked : false,
      isUnliked : false,
      countLike : 0,
      countDislike: 0,
      clickDisabled : false
    }
  },

  methods: {
    getIssue() {
      var issueId = this.$route.params.id;
      this.$http.get('http://localhost:8181/issue/' + issueId).then(data=>{
        console.log(data.body);
          this.markers[0].position.lat = data.body.mapMarker.lat;
          this.markers[0].position.lng = data.body.mapMarker.lng;
          this.center.lat = data.body.mapMarker.lat;
          this.center.lng = data.body.mapMarker.lng;
          this.title = data.body.title;
          this.text = data.body.text;
      })
    },

    getVote() {
      var issueId = this.$route.params.id;
      this.$http.get('http://localhost:8181/isVote/' + issueId).then(data=> {
        console.log(data.body)
        if (data.body.vote !== null) {
          data.body ? this.isLiked = true : this.isUnliked = true;
        }
      })
    },

    like() {
      var isLiked = this.isLiked;
      var isUnliked = this.isUnliked;
      var issueId = this.$route.params.id;
      if (this.clickDisabled)
        return;
      if(isLiked) {
        this.$http.get('http://localhost:8181/deleteVote/' + issueId).then(data=>{
          this.calculateVote();
        })
      } else if(!isLiked) {
        if(isUnliked){
          this.$http.get('http://localhost:8181/deleteVote/' + issueId).then(data=>{
          })
          this.isUnliked = !isUnliked;
        }
        this.$http.get('http://localhost:8181/addVote/' + issueId +'/' + true).then(data=>{
          this.calculateVote();
        })
      }
      this.isLiked = !isLiked;
      this.clickDisabled = true;
      setTimeout(() =>{
        this.clickDisabled = false
      }, 2000)
    },

    dislike() {
      var isLiked = this.isLiked;
      var isUnliked = this.isUnliked;
      var issueId = this.$route.params.id;
      if (this.clickDisabled)
        return;
      if(isUnliked) {
        this.$http.get('http://localhost:8181/deleteVote/' + issueId).then(data=>{
          this.calculateVote();
        })
      } else if(!isUnliked) {
        if(isLiked){
          this.$http.get('http://localhost:8181/deleteVote/' + issueId).then(data=>{
          })
          this.isLiked = !isLiked;
        }
        this.$http.get('http://localhost:8181/addVote/' + issueId +'/' + false).then(data=>{
          this.calculateVote();
        })
      }
      this.isUnliked = !isUnliked;
      this.clickDisabled = true;
      setTimeout(() =>{
        this.clickDisabled = false
      }, 2000)
    },

    calculateVote() {
      var issueId = this.$route.params.id;
      this.$http.get('http://localhost:8181/calculateVote/' + issueId).then(data=>{
        console.log(data.body);
        this.countLike = data.body.likeVote;
        this.countDislike = data.body.dislikeVote;
      })
    }
  },

  created: function () {
    this.getIssue(), this.getVote(), this.calculateVote();
  }
}
