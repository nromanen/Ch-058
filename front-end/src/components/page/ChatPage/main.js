import message from '@/components/Message/Message.vue'
import {getLocalUser} from "../../../router";
import 'stompjs/lib/stomp.js';
import * as SockJS from 'sockjs-client/dist/sockjs.min.js'
export default {
  name: 'ChatPage',
  data() {
    return {
      messages: [
        {
          text: '',
          authorId: 0
        }
      ],
      newMessageText: '',
      stompClient: null,
      userId: -1
    }
  },
  components: {
    message
  },
  methods: {
    sendMes: function (event) {
      this.stompClient.send("/app/message" + "/3/" + this.userId, {},
        JSON.stringify({text: this.newMessageText, authorId: this.userId}));
      this.newMessageText = '';
    },
    showMessage: function (message) {
      console.log(message);
      var result = {
        text: message.text,
        authorId: message.authorId
      }
      this.messages.push(result);
      console.log(this.messages);
    },
    getAllMessages: function () {
      this.$http.get('http://localhost:8080/message/all/3/' + this.userId).then( data => {
        console.log(data.body);
      });
    }
  },
  created: function () {
    if(this.userId == -1)
      this.userId = getLocalUser().id;
    console.log('started');
    let _this = this;

    _this.getAllMessages();

    var socket = new SockJS("http://localhost:8080/chat");
    var stompClient = Stomp.over(socket);
    this.stompClient = stompClient;

    stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/topic/broadcast' + '/3/' + _this.userId, function (greeting) {
        console.log(greeting);
        _this.showMessage(JSON.parse(greeting.body));
      });
    })
  }
}
