import `Vue from 'vue';
import VueMaterial from './../../../../node_modules/vue-material'
import './../../../../node_modules/vue-material/dist/vue-material.css'
import VueResource from 'vue-resource';
import { VTooltip } from 'v-tooltip'

Vue.directive('my-tooltip', VTooltip);
Vue.use(VueMaterial);
Vue.use(VueResource);


export default {
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
      clickDisabled : false,
      typeId: -1,
      marker : null,
    }
  },

  methods: {
    loadIssue() {
      var self = this;
      var issueId = this.$route.params.id;
      this.$http.get('issues/' + issueId).then(data=>{
        console.log(data.body);
        this.markerPosition.lat = parseFloat(data.body.data[0].mapMarker.lat);
        this.markerPosition.lng = parseFloat(data.body.data[0].mapMarker.lng);
        this.center.lat = parseFloat(data.body.data[0].mapMarker.lat);
        this.center.lng = parseFloat(data.body.data[0].mapMarker.lng);
        this.title = data.body.data[0].title;
        this.text = data.body.data[0].text;
        this.typeId = data.body.data[0].typeId;

        this.map = new google.maps.Map(document.getElementById('issueMap'), {
          center: self.markerPosition,
          zoom: 16,
          maxZoom: 17,
          minZoom: 4,
          disableDefaultUI: true,
          disableDoubleClickZoom: true,
          zoomControl: true,
          mapTypeControl: true
        });
        var url;
        switch (self.typeId) {
          case 1:
            url = '/src/assets/caution-large.png';
            break;
          case 2:
            url = '/src/assets/info-large.png';
            break;
          case 3:
            url = '/src/assets/feedback-large.png';
            break;
          default:
            url = ''
        }
        var marker = new google.maps.Marker({
          map: self.map,
          position: self.markerPosition,
          animation: google.maps.Animation.DROP,
          icon: {
            url:url,
            scaledSize: new google.maps.Size(50, 50)
          }
        });
      })
    },

    loadVote() {
      var issueId = this.$route.params.id;
      this.$http.get('issues/' + issueId + '/is-vote-exist').then(data=> {
        if (data.body.data[0].vote !== null) {
          data.body.data[0] ? this.isLiked = true : this.isUnliked = true;
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
        this.$http.delete('issues/' + issueId + '/vote').then(data=>{
          this.calculateVote();
        })
      } else if(!isLiked) {
        if(isUnliked){
          this.$http.delete('issues/' + issueId + '/vote').then(data=>{
          })
          this.isUnliked = !isUnliked;
        }
        this.$http.post('issues/' + issueId +'/' + true).then(data=>{
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
        this.$http.delete('issues/' + issueId + '/vote').then(data=>{
          this.calculateVote();
        })
      } else if(!isUnliked) {
        if(isLiked){
          this.$http.delete('issues/' + issueId + '/vote').then(data=>{
          });
          this.isLiked = !isLiked;
        }
        this.$http.post('issues/' + issueId +'/' + false).then(data=>{
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
      this.$http.get('issues/' + issueId + '/votes').then(data=>{
        this.countLike = data.body.data[0].likeVote;
        this.countDislike = data.body.data[0].dislikeVote;
      })
    }
  },

  mounted: function () {
    this.loadIssue(), this.loadVote(), this.calculateVote();
  }
}
