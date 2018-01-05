/*import * as VueGoogleMaps from 'vue2-google-maps';*/
import Vue from 'vue';
import VueMaterial from './../../../../node_modules/vue-material'
import './../../../../node_modules/vue-material/dist/vue-material.css'
import VueResource from 'vue-resource';
import { VTooltip } from 'v-tooltip'

Vue.directive('my-tooltip', VTooltip)
Vue.use(VueMaterial)
Vue.use(VueResource)
/*Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyCSfmbaMZpr_QgS0jvkLYDaMdluFga9J-4'
  }
});*/

export default {
  name: 'Map',
  data () {
    return {
      center: {lat: 0, lng: 0},
      markerPosition: {lat: 0, lng: 0},
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
    loadIssue() {
      var self = this;
      var issueId = this.$route.params.id;
      this.$http.get('issue/' + issueId).then(data=>{
        console.log(data.body);
          this.markerPosition.lat = parseFloat(data.body.mapMarker.lat);
          this.markerPosition.lng = parseFloat(data.body.mapMarker.lng);
          this.center.lat = parseFloat(data.body.mapMarker.lat);
          this.center.lng = parseFloat(data.body.mapMarker.lng);
          this.title = data.body.title;
          this.text = data.body.text;

        this.map = new google.maps.Map(document.getElementById('issueMap'), {
          center: self.markerPosition,
          zoom: 14,
          maxZoom: 17,
          minZoom: 4,
          disableDefaultUI: true,
          disableDoubleClickZoom: true,
          zoomControl: true,
          mapTypeControl: true
        });
        var marker = new google.maps.Marker({
          map: self.map,
          position: self.markerPosition,
          animation: google.maps.Animation.DROP
        });
      })
    },

    loadVote() {
      var issueId = this.$route.params.id;
      this.$http.get('isVote/' + issueId).then(data=> {
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
        this.$http.get('deleteVote/' + issueId).then(data=>{
          this.calculateVote();
        })
      } else if(!isLiked) {
        if(isUnliked){
          this.$http.get('deleteVote/' + issueId).then(data=>{
          })
          this.isUnliked = !isUnliked;
        }
        this.$http.get('addVote/' + issueId +'/' + true).then(data=>{
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
        this.$http.get('deleteVote/' + issueId).then(data=>{
          this.calculateVote();
        })
      } else if(!isUnliked) {
        if(isLiked){
          this.$http.get('deleteVote/' + issueId).then(data=>{
          })
          this.isLiked = !isLiked;
        }
        this.$http.get('addVote/' + issueId +'/' + false).then(data=>{
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
      this.$http.get('calculateVote/' + issueId).then(data=>{
        console.log(data.body);
        this.countLike = data.body.likeVote;
        this.countDislike = data.body.dislikeVote;
      })
    }
  },

  mounted: function () {
    this.loadIssue(), this.loadVote(), this.calculateVote();
  }
}
