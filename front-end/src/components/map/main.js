import {minLength, required} from 'vuelidate/lib/validators'

export default {
  name: 'Map',
  data: ()=> ({
    form: {
      title: null,
      desc: null,
      type: null,
    },
    sending: false
  }),
  validations: {
    form: {
      title: {
        required,
        minLength: minLength(3)
      },
      desc: {
        minLength: minLength(10)
      },
      type: {
        required
      }
    }
  },
  methods: {
    getValidationClass (fieldName) {
      const field = this.$v.form[fieldName]

      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },
    validateData () {
      this.$v.$touch()

      if (!this.$v.$invalid) {
        this.saveIssue()
      }
    },

    initMap() {
        let self = this;
        self.map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 48.29149, lng: 25.94034},
          zoom: 13,
          maxZoom: 19,
          minZoom: 13,
          disableDefaultUI: true,
          disableDoubleClickZoom: true,
          mapTypeControl: true,
          mapTypeControlOptions: {
            style: google.maps.MapTypeControlStyle.DROPDOWN_MENU,
            position: google.maps.ControlPosition.TOP_CENTER
          }
        })
        this.addYourLocationButton(this.map)

        this.map.addListener('dblclick', function(e) {
            self.callPopup()
            self.lat = e.latLng.lat()
            self.lng = e.latLng.lng()
        })
        self.loadAllMarkers();
    },

    callPopup() {
        var modal = document.getElementById('myModal');
        var span = document.getElementsByClassName("close")[0];
        modal.style.display = "block"
        span.onclick = function() {
            modal.style.display = "none"
        };
    },

    reset () {
      Object.assign(this.$data, this.$options.data.call(this));
    },

    // calling on submit popup form
    saveIssue() {
        var self = this
        var title = document.getElementById("title").value
        var desc = document.getElementById("desc").value

        this.addMarker(this.lat, this.lng)

        this.$http.post(/*'auth/logout'*/ 'http://localhost:8080/map/saveData', { // TODO
          mapMarkerId: 1,
          title: title,
          text: desc,
          typeId: 1
        }).then((response) => {console.log(response.body)
        })
        document.getElementById('myModal').style.display = "none"
        self.reset()
    },

    addMarker(lat, lng) {
        let self = this
        var marker = new google.maps.Marker({
            map: self.map,
            position: {
              lat: parseFloat(lat),
              lng: parseFloat(lng)
            },
            animation: google.maps.Animation.DROP
        })
        this.$http.post(/*'auth/logout'*/ 'http://localhost:8080/map/saveMarker', { // TODO
            lat: lat,
            lng: lng
          }).then((response) => {console.log(response.body)
        })
        var infoWindow = new google.maps.InfoWindow();
        marker.addListener('click', function() {
            self.loadInfo(lat, lng, infoWindow);
            infoWindow.open(map, marker);
            setTimeout(function(){infoWindow.close()}, 5000);
            google.maps.event.addListener(self.map, 'click', function(){
              infoWindow.close();
            })
        })
    },

    loadInfo(lat, lng, infoWindow) {
      this.$http.get('http://localhost:8080/map/getMarker', /*null,*/ {params:  {lat: lat, lng: lng}}).then((response) => { //TODO
        infoWindow.setContent(response.body.data[0].data[0].id.toString())
      })
      //TODO
    },

    loadAllMarkers() {
        let self = this
        this.$http.get('http://localhost:8080/map').then((response) => {
            for (var i = 0; i < response.body.data[0].data.length; i++) {
                var lat = parseFloat(response.body.data[0].data[i].lat)
                var lng = parseFloat(response.body.data[0].data[i].lng)
                var marker = new google.maps.Marker({
                    map: self.map,
                    position: {
                      lat: lat,
                      lng: lng
                    },
                    animation: google.maps.Animation.DROP
                })

                var infoWindow = new google.maps.InfoWindow();
                marker.addListener('click', (function(marker, infoWindow, lat, lng){
                    return function() {
                      var idMarker = 0;
                      self.$http.get('http://localhost:8080/map/getMarker', /*null,*/ {params:  {lat: lat, lng: lng}}).then((response) => { //TODO
                        idMarker = response.body.data[0].data[0].id
                        console.log(idMarker);
                        /*self.$http.get('http://localhost:8080/issue/' + idMarker).then(response(data=>{
                          var idIssue = 0;*/
                          self.$router.push('/issue/' + idMarker);
                        /*}))*/
                      })
                      /*
                      window.location.href +*/
                        /*self.loadInfo(lat, lng, infoWindow)
                        infoWindow.open(map, marker)
                        setTimeout(function(){infoWindow.close()}, 5000);
                        google.maps.event.addListener(self.map, 'click', function(){
                          infoWindow.close();
                        })*/
                    };
                })(marker, infoWindow, lat, lng))
            }
        });
    },

    addYourLocationButton(map) {
        let self = this
        var controlDiv = document.createElement('div')

        var firstChild = document.createElement('button')
        firstChild.style.backgroundColor = '#fff'
        firstChild.style.border = 'none'
        firstChild.style.outline = 'none'
        firstChild.style.width = '28px'
        firstChild.style.height = '28px'
        firstChild.style.borderRadius = '2px'
        firstChild.style.boxShadow = '0 1px 4px rgba(0,0,0,0.3)'
        firstChild.style.cursor = 'pointer'
        firstChild.style.marginTop = '10px'
        firstChild.style.padding = '0px'
        firstChild.title = 'Find your location'
        controlDiv.appendChild(firstChild)

        var secondChild = document.createElement('div')
        secondChild.style.margin = '5px'
        secondChild.style.width = '18px'
        secondChild.style.height = '18px'
        secondChild.style.backgroundImage = 'url(https://maps.gstatic.com/tactile/mylocation/mylocation-sprite-1x.png)'
        secondChild.style.backgroundSize = '180px 18px'
        secondChild.style.backgroundPosition = '0px 0px'
        firstChild.appendChild(secondChild)

        // set user location
        firstChild.addEventListener('click', function() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    var pos = {
                      lat: position.coords.latitude,
                      lng: position.coords.longitude
                    }
                    var userMarker = new google.maps.Marker({
                      map: self.map,
                      position: pos,
                    })
                    userMarker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png')

                    var infoWindow = new google.maps.InfoWindow
                    infoWindow.setContent('Your location')
                    infoWindow.open(map, userMarker)
                    setTimeout(function() { infoWindow.close(); }, 2000)

                    self.map.setCenter(pos)
                    self.map.setZoom(16)
                })
            }
        })
        map.controls[google.maps.ControlPosition.TOP_CENTER].push(controlDiv)
      },
  },

  mounted: function () {
      this.initMap();
  }
}