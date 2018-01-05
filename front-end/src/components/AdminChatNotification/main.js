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
      this.stompClient.send("/app/connect", {}, JSON.stringify({text: 'Accept', issueId: issueId, userId: userId}));
      window.location.href = "http://localhost:8081/#/adminChatPage/" + issueId + "/" + userId;
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
        var user = JSON.parse(input.body);
        _this.users.push(user);
        console.log('received notification ! connectedToAdmin');
        // console.log(input);
        console.log(_this.users[0].userId);
      });
    })
  }
}
