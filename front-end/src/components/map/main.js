import {minLength, required, } from 'vuelidate/lib/validators'

export default {
  name: 'Map',
  data: ()=> ({
    form: {
      title: null,
      desc: null,
      type: null,
    },
    id: 0,
    sending: false,
  }),
  validations: {
    form: {
      title: {
        required,
        minLength: minLength(3)
      },
      desc: {
        required,
        minLength: minLength(10)
      },
      type: {
        required
      }
    }
  },
  methods: {
    getValidationClass (fieldName) {
      const field = this.$v.form[fieldName];
      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },

    // calling when exit popup window
    clearForm() {
      this.$v.$reset();
      this.form.title = null;
      this.form.desc = null;
      this.form.type = null
    },

    // calling when submit input form
    validateData () {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.saveIssue()
      }
    },

    initMap() {
      var self = this
        this.map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: 48.29149, lng: 25.94034},
          zoom: 14,
          maxZoom: 19,
          minZoom: 14,
          disableDefaultUI: true,
          disableDoubleClickZoom: true,
          zoomControl: true,
          mapTypeControl: true,
        })
        this.addYourLocationButton();
        this.getUserLocation();

        this.map.addListener('dblclick', function(e) {
          self.addMarker(e.latLng.lat(), e.latLng.lng())
        })
    },

    getUserLocation() {
      var self = this;
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          }
          self.map.setCenter(pos);
          self.map.setZoom(19);
          var infoWindow = new google.maps.InfoWindow({map: self.map});
          infoWindow.setPosition(pos);
          infoWindow.setContent('<b>Your location</b>');
          setTimeout(function() { infoWindow.close(); }, 2000)
        }, function() {
          self.handleLocationError(true)
        })
      }
      else {
        self.handleLocationError(false)
      }
    },

    handleLocationError(browserHasGeolocation) {
      alert(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    },

    addYourLocationButton() {
      var self = this;
      var controlDiv = document.createElement('div');

      var firstChild = document.createElement('button');
      firstChild.style.backgroundColor = '#fff';
      firstChild.style.border = 'none';
      firstChild.style.outline = 'none';
      firstChild.style.width = '28px';
      firstChild.style.height = '28px';
      firstChild.style.borderRadius = '2px';
      firstChild.style.boxShadow = '0 1px 4px rgba(0,0,0,0.3)';
      firstChild.style.cursor = 'pointer';
      firstChild.style.marginRight = '10px';
      firstChild.style.padding = '0px';
      firstChild.title = 'Find your location';
      controlDiv.appendChild(firstChild);

      var secondChild = document.createElement('div');
      secondChild.style.margin = '5px';
      secondChild.style.width = '18px';
      secondChild.style.height = '18px';
      secondChild.style.backgroundImage = 'url(https://maps.gstatic.com/tactile/mylocation/mylocation-sprite-1x.png)';
      secondChild.style.backgroundSize = '180px 18px';
      secondChild.style.backgroundPosition = '0px 0px';
      firstChild.appendChild(secondChild);

      firstChild.addEventListener('click', function() {
        self.getUserLocation()
      });
      this.map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(controlDiv)
    },

    addMarker(lat, lng) {
      this.$http.post('map/saveMarker', {
        lat: lat,
        lng: lng
      }).then((response) => {
        console.log(response.body.data[0].id);
        window.id = response.body.data[0].id;
        this.openPopup()
      })
    },

    deleteMarker(id) {
      this.$http.post('map/deleteMarker', null, {
        params: {
          id: id
        }
      }).then((response) => {
        console.log(response.body.result)
      })
    },

    openPopup() {
      var self = this;
      var modal = document.getElementById('myModal');
      var span = document.getElementsByClassName("close")[0];
      modal.style.display = "block";
      span.onclick = function() {
        modal.style.display = "none";
        self.deleteMarker(window.id)
      };
    },

    saveIssue() {
      /*this.$http.post('map/saveIssue',{
            mapMarkerId: window.id,
            title: this.form.title,
            text: this.form.desc,
            typeId: this.form.type
        }).then((response) => {console.log(response.body)
        });*/
        document.getElementById('myModal').style.display = "none";
        this.clearForm();
        this.placeMarker(window.id); // + type
    },

    placeMarker(id) {
      this.$http.post('map/getMarker', null, {
        params: {
          id: id
        }
      }).then((response) => {
        var lat = parseFloat(response.body.data[0].lat);
        var lng = parseFloat(response.body.data[0].lng);
        var id = window.id;
        console.log(lat + " " + lng + " " + id);
        var marker = new google.maps.Marker({
          map: this.map,
          position: {
            lat: lat,
            lng: lng
          },
          animation: google.maps.Animation.DROP
        })
      })
    },

    /*addMarker(lat, lng) {
        let self = this
        var marker = new google.maps.Marker({
            map: self.map,
            position: {
              lat: parseFloat(lat),
              lng: parseFloat(lng)
            },
            animation: google.maps.Animation.DROP
        })
        this.$http.post('map/saveMarker', {
            lat: lat,
            lng: lng
          }).then((response) => {console.log(response.body)
        })
    },*/

    /*loadAllMarkers() {
        let self = this
        this.$http.get('map').then((response) => {
            for (var i = 0; i < response.body.data.length; i++) {
                var lat = parseFloat(response.body.data[i].lat)
                var lng = parseFloat(response.body.data[i].lng)
                var id = parseFloat(response.body.data[i].id)
                var marker = new google.maps.Marker({
                    map: self.map,
                    position: {
                      lat: lat,
                      lng: lng
                    },
                    animation: google.maps.Animation.DROP
                })

                var infoWindow = new google.maps.InfoWindow();
                marker.addListener('click', (function(marker, infoWindow, id){
                    return function() {
                        infoWindow.setContent(id.toString())
                        infoWindow.open(map, marker)
                        google.maps.event.addListener(self.map, 'click', function(){
                          infoWindow.close();
                        })
                    };
                })(marker, infoWindow, id))
            }
        }
        );
    },*/
  },

  mounted: function () {
      this.initMap();
  }
}
