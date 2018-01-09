import {getLocalUser} from "../../router";
import 'stompjs/lib/stomp.js';
import * as SockJS from 'sockjs-client/dist/sockjs.min.js'
import chatPage from '@/components/page/ChatPage/ChatPage.vue'
export default {
  name: 'AdminChatNotification',
  data: function(){
    return {
      users: [],
      stompClient: null
    }
  },
  components: {
    chatPage
  },
  methods: {
    answer: function (userId, issueId) {
      this.stompClient.send("/app/connect", {}, JSON.stringify({text: 'Accept', login: "", issueId: issueId, userId: userId}));
      window.location.href = "http://localhost:8081/#/adminChatPage/" + issueId + "/" + userId;
    },
    markAsReaded: function (userId, issueId) {
      this.$http.delete('http://localhost:8080/notification/' + issueId + '/' + userId,
        JSON.stringify({text: 'Delete', login: "", issueId: issueId, userId: userId})).then( data => {
          console.log(data.body);
      });
    }
  },
  created: function () {
    console.log('started');
    this.login = getLocalUser().login;
    let _this = this;

    var socket = new SockJS("http://localhost:8080/chat");
    var stompClient = Stomp.over(socket);
    this.stompClient = stompClient;

    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/checkTopic/broadcast', function (input) {
        console.log(input.body);
        var user = JSON.parse(input.body);
        if(user.text == "Accept"){
          var index = -1;
          for(var i = 0; i < _this.users.length; i++){
            if(_this.users[i].userId == user.userId && _this.users[i].issueId == user.issueId){
              index = i;
              break;
            }
          }
          _this.users.splice(index, 1);
          console.log(index);
          console.log('removed');
        }
        else if(user.text == "Notification timed out"){
          var issueId = user.issueId;
          var userId = user.userId;
          var index = -1;
          for(var i = 0; i < _this.users.length; i++){
            if(_this.users[i].userId == user.userId && _this.users[i].issueId == user.issueId){
              index = i;
              break;
            }
          }
          _this.users[i].waiting = true;
        }
        else {
          var user = JSON.parse(input.body);
          _this.users.push(user);
          console.log('received notification !');
        }
      });
    })

    this.$http.get('http://localhost:8080/notification/all').then( data => {
      var charRequestArray = data.body;
      for(var i = 0; i < charRequestArray.length; i++) {
        var user = charRequestArray[i];
        this.users.push(user);
      }
    });
  }
}
