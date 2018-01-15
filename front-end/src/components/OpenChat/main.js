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
      noAdmins: false,
      dataUserId: -1,
      dataIssueId: -1
    }
  },
  props: [
    'issueId', 'userId'
  ],

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
          if(user.text == 'Accept' && user.userId == _this.dataUserId && user.issueId == _this.dataIssueId){
            window.location.href = "http://localhost:8081/#/chat/" + _this.dataIssueId + "/" + _this.dataUserId;
          }
        });
      })
    },
    notificateAdmins: function (login) {
      this.stompClient.send("/app/connect", {}, JSON.stringify({text: "Alert", login: login,
        issueId: this.issueId, userId: this.userId, waiting: true}));
    },
    openChat: function(){
      this.dataIssueId = this.$props['issueId'];
      this.dataUserId = this.$props['userId'];
      let _this = this;
      this.$http.get(_this.dataIssueId + '/' + _this.dataUserId + '/chat').then( data => {
        console.log(data.body);
        if(data.body) {
          _this.stompClient.disconnect();
          _this.socket._close();

          window.location.href = "http://localhost:8081/#/chat/" + _this.dataIssueId + "/" + _this.dataUserId;
        }
        else{
          let _this = this;
          _this.notificateAdmins(_this.login);
          _this.waiting = true;
          function func() {
            _this.noAdmins = true;
            _this.stompClient.send("/app/connect",  {},
              JSON.stringify({text: "Notification timed out", login: _this.login,
                issueId: _this.dataIssueId, userId: _this.dataUserId, waiting: false}));
          }
          var timerId = setTimeout(func, 60000);
        }
      })
    }
  },
  created: function () {
    let _this = this;
    _this.socketConnect();
  }
}
