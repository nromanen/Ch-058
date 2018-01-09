import {getLocalUser} from "../../router";
import 'stompjs/lib/stomp.js';
import * as SockJS from 'sockjs-client/dist/sockjs.min.js'
export default {
  name: 'OpenChat',
  data() {
    return {
      login: '',
      socket: null,
      stompClient: null,
      waiting: false,
      noAdmins: false
    }
  },
  methods: {
    socketConnect: function () {
      console.log('started');
      this.login = getLocalUser().login;
      let _this = this;
      var socket = new SockJS("http://localhost:8080/chat");
      this.socket = socket;
      var stompClient = Stomp.over(socket);
      this.stompClient = stompClient;
      stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/checkTopic/broadcast', function (responseForChat) {
          console.log(responseForChat);
          var user = JSON.parse(responseForChat.body);
          if(user.text == 'Accept' && user.userId == getLocalUser().id && user.issueId == 3){
            window.location.href = "http://localhost:8081/#/chat";
          }
        });
      })
    },
    notificateAdmins: function (login) {
      this.stompClient.send("/app/connect", {}, JSON.stringify({text: "Alert", login: login,
        issueId: 3, userId: getLocalUser().id}));
    },
    openChat: function(){
      this.$http.get('http://localhost:8080/3/' + getLocalUser().id + '/chat').then( data => {
        console.log(data.body);
        if(data.body) {
          let _this = this;
          _this.stompClient.disconnect();
          _this.socket._close();

          window.location.href = "http://localhost:8081/#/chat";
        }
        else{
          let _this = this;
          _this.notificateAdmins(_this.login);
          _this.waiting = true;
          function func() {
            _this.noAdmins = true;
          }
          var timerId = setTimeout(func, 60000);
          _this.stompClient.send("/app/connect",  {},
            JSON.stringify({text: "Notification timed out", login: login, issueId: 3, userId: getLocalUser().id}));
        }
      })
    }
  },
  created: function () {
    let _this = this;
    _this.socketConnect();
  }
}
