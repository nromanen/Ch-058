import message from '@/components/Message/Message.vue'
import {getLocalUser} from "../../../router";
import 'stompjs/lib/stomp.js';
import * as SockJS from 'sockjs-client/dist/sockjs.min.js'
export default {
  name: 'AdminChatPage',
  data() {
    return {
      messages: [],
      newMessageText: '',
      stompClient: null,
      adminId: -1,
      userId: -1,
      issueId: -1
    }
  },
  components: {
    message
  },
  methods: {
    setUserId: function (userId) {
      this.userId = userId;
    },
    sendMes: function (event) {
      this.stompClient.send("/app/message" + "/" + this.issueId + "/" + this.userId, {},
        JSON.stringify({text: this.newMessageText, authorId: this.adminId}));
      this.newMessageText = '';
    },
    showMessage: function (message) {
      console.log(message);
      var result = {
        text: message.text,
        authorId: message.authorId
      }
      this.messages.push(result);
      var _this = this;
      this.$nextTick(function () {
        _this.scrollDown();
      })
      console.log(this.messages);
    },
    showMessages: function (messages) {
      for(var i = 0; i < messages.length; i++) {
        var result = {
          text: messages[i].text,
          authorId: messages[i].authorId
        }
        this.messages.push(result);
        var _this = this;
        this.$nextTick(function () {
          _this.scrollDown();
        })
      }
      console.log(this.messages);
    },
    getAllMessages: function () {
      this.$http.get('http://localhost:8080/message/all/' + this.issueId + '/' + this.userId).then( data => {
        console.log(data.body);
        this.showMessages(data.body);
      });
    },
    scrollDown: function () {
      var elem = document.getElementById('style-6');
      elem.scrollTop = elem.scrollHeight;
    }
  },
  created: function () {
    var issueId = this.$route.params.issueId;
    var userId = this.$route.params.userId;
    this.issueId = issueId;
    this.userId = userId;
    console.log('started');
    this.adminId = getLocalUser().id;
    let _this = this;

    _this.getAllMessages();

    var socket = new SockJS("http://localhost:8080/chat");
    var stompClient = Stomp.over(socket);
    this.stompClient = stompClient;

    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/broadcast' + '/' + _this.issueId + '/' + _this.userId, function (greeting) {
        console.log(greeting);
        _this.showMessage(JSON.parse(greeting.body));
      });
    })
  }
}