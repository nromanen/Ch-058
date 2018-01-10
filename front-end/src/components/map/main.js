import {minLength, maxLength, required} from 'vuelidate/lib/validators'
import {getLocalUser} from "../../router/index";

export default {
  name: 'Map',
  data: () => ({
    form: {
      title: null,
      desc: null,
      type: null,
      image: null
    },
    id: 0,
    lat: 0,
    lng: 0,
    marker: null,
    select: null,
    isPlaced: false,
    sending: false,
    showDialog: false,
    showIssueDialog: true,
    issues: [],
    showSnackBar: false,
    markers: [],
    activeMarker: {
        id: 0,
        lat: 0,
        lng: 0
      },
    map:null
  }),
  validations: {
    form: {
      title: {
        required,
        minLength: minLength(4),
        maxLength: maxLength(32)
      },
      desc: {
        required,
        minLength: minLength(8),
        maxLength: maxLength(2048)
      },
      type: {
        required
      },
      image: {
        required
      }
    }
  },
  methods: {
    getValidationClass(fieldName) {
      const field = this.$v.form[fieldName];
      if (field) {
        return {
          'md-invalid': field.$invalid && field.$dirty
        }
      }
    },

    // calling when submit input form
    validateData() {
      this.$v.$touch();
      if (!this.$v.$invalid) {
        var fileSize = document.getElementById("uploadImage").files[0].size;
        if (fileSize > 1024 * 1024 * 5) // 5 MB
        {
          window.alert('Image size cannot be bigger then 5 MB');
        } else {
          this.saveIssue();
        }
      }
    },

    // calling when exit popup window
    clearForm() {
      this.$v.$reset();
      this.form.title = null;
      this.form.desc = null;
      this.form.type = null;
      this.form.image = null
    },

    initMap() {
      var self = this;
      self.map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 48.29149, lng: 25.94034},
        zoom: 15,
        maxZoom: 19,
        minZoom: 15,
        disableDefaultUI: true,
        disableDoubleClickZoom: true,
        zoomControl: true,
        mapTypeControl: true,
      });
      this.addYourLocationButton();
      this.addSearchField();
      if (localStorage.getItem('redirectFromIssue')) {
        self.activeMarker = JSON.parse(localStorage.getItem('activeMarker'))
        self.showAllIssuesByMarker(self.activeMarker.id);
        var pos = {
          lat: self.activeMarker.lat,
          lng: self.activeMarker.lng
        };
        self.map.setCenter(pos);
        self.map.setZoom(parseInt(localStorage.getItem('zoom')));
      } else {
        this.getUserLocation();
      }
      this.loadAllMarkers();

      this.map.addListener('idle', function () {
        for (var i = 0; i < self.markers.length; i++) {
          var marker = self.markers[i];
          if (self.map.getBounds().contains(marker.getPosition()) && marker.getMap() !== self.map) {
            marker.setMap(self.map);
          }
          if (!self.map.getBounds().contains(marker.getPosition())) {
            marker.setMap(null);
          }
        }
      });

      this.map.addListener('dblclick', function (e) {
        if (getLocalUser()) {
          self.saveCoords(e.latLng.lat(), e.latLng.lng())
        } else {
          self.showSnackBar = true
        }
      });
    },

    search() {
      var self = this;
      var pos;
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
          pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          };
        })
      } else {
        pos = new google.maps.LatLng(48.29149, 25.94034);
      }
      var autocomplete = new google.maps.places.Autocomplete(
        document.getElementById('pac-input'), {
          types: ['establishment'],
          location: pos,
          radius: 10000,
        });
      autocomplete.bindTo('bounds', self.map);
      autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();
        if (!place.geometry) {
          window.alert("No details available for input: '" + place.name + "'");
          return;
        }
        else {
          var s = window.select;
          self.$http.get('marker/' + place.geometry.location.lat() + "/" + place.geometry.location.lng() + "/")
            .then((response) => {
              if (response.body.data[0] == null) {
                self.map.setCenter(place.geometry.location);
                self.map.setZoom(19);
                s = new google.maps.Marker({
                  map: self.map,
                  position: {
                    lat: place.geometry.location.lat(),
                    lng: place.geometry.location.lng()
                  },
                  animation: google.maps.Animation.DROP
                });
                self.setMarkerType(s, '5');
                if (getLocalUser()) {
                  setTimeout(function () {
                    self.saveCoords(place.geometry.location.lat(), place.geometry.location.lng());
                    s.setMap(null);
                  }, 1200)
                } else {
                  self.showSnackBar = true;
                  document.getElementById('pac-input').value = '';
                }
              }
              else {
                self.map.setCenter(place.geometry.location);
                self.map.setZoom(19);
              }
            })
        }
      });
    },

    getUserLocation() {
      var self = this;
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
          var pos = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          };
          self.map.setCenter(pos);
          self.map.setZoom(19);
          var infoWindow = new google.maps.InfoWindow({map: self.map});
          infoWindow.setPosition(pos);
          infoWindow.setContent('<b>Your location</b>');
          setTimeout(function () {
            infoWindow.close();
          }, 2000)
        }, function () {
          self.handleLocationError(true)
        })
      }
      else {
        self.handleLocationError(false)
      }
    },

    handleLocationError(browserHasGeolocation) {
      window.alert(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
    },

    addSearchField() {
      var self = this;
      var input = document.createElement('input');
      input.setAttribute('placeholder', 'Enter a location');
      input.setAttribute('id', 'pac-input');
      input.setAttribute('type', 'text');
      input.style.marginTop = '10px';
      input.style.border = '1px solid transparent';
      input.style.borderRadius = '2px 0 0 2px';
      input.style.height = '29px';
      input.style.boxShadow = '0 1px 4px rgba(0, 0, 0, 0.3)';
      input.style.padding = '0 11px 0 13px';
      input.style.fontSize = '15px';
      input.style.borderColor = '#4d90fe';
      this.map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
      input.addEventListener('click', function () {
        self.search()
      });
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

      firstChild.addEventListener('click', function () {
        self.getUserLocation()
      });
      this.map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(controlDiv)
    },

    saveCoords(lat, lng) {
      window.lat = lat;
      window.lng = lng;
      this.openPopup()
    },

    openPopup() {
      var modal = document.getElementById('myModal');
      var span = document.getElementsByClassName("close")[0];
      modal.style.display = "table";
      span.onclick = function () {
        modal.style.display = "none";
        document.getElementById('pac-input').value = '';
        document.getElementById("preview").hidden = true;
        window.lat = 0;
        window.lng = 0;
      };
    },

    saveIssue() {
      var title = this.form.title;
      var desc = this.form.desc;
      var type = this.form.type;
      var image = this.form.image;

      var formData = new FormData();
      formData.append('title', title);
      formData.append('desc', desc);
      formData.append('typeId', type);
      formData.append('file', document.getElementById("uploadImage").files[0]);

      if (window.isPlaced) {
        formData.append('markerId', window.id);
        this.setMarkerType(window.marker, '4');
        this.$http.post('issue', formData).then((response) => {
        });
      } else {
        var marker = new google.maps.Marker({
          map: this.map,
          position: {
            lat: window.lat,
            lng: window.lng
          },
        });
        this.setMarkerType(marker, type);
        this.setListeners(marker);

        this.$http.post('marker', {
          lat: window.lat,
          lng: window.lng
        }).then((response) => {
          formData.append('markerId', response.body.data[0].id);
          this.$http.post('issue', formData).then((response) => {
          });
        });
      }

      document.getElementById('myModal').style.display = "none";
      this.clearForm();
      window.isPlaced = false;
      window.marker = null;
      document.getElementById('pac-input').value = '';
      document.getElementById("preview").hidden = true;
    },

    setMarkerType(marker, type) {
      var url;
      switch (type) {
        case '1':
          url = '/src/assets/caution.png';
          break;
        case '2':
          url = '/src/assets/info.png';
          break;
        case '3':
          url = '/src/assets/feedback.png';
          break;
        case '4':
          url = '/src/assets/multiple.png';
          break;
        case '5':
          url = '/src/assets/select.png';
          break;
        default:
          url = ''
      }
      var icon = {
        url: url,
        scaledSize: new google.maps.Size(200, 200),
        anchor: new google.maps.Point(100, 120)

      };
      marker.setIcon(icon);
    },

    onCancelIssuesDialog: function () {
      this.issues = []
      localStorage.removeItem('redirectFromIssue')
      localStorage.removeItem('activeMarker')
      localStorage.removeItem('zoom')
    },

    setListeners(marker) {
      var self = this;
      var timer = 0;
      var delay = 300;
      var prevent = false;

      marker.addListener('click', function () {
        timer = setTimeout(function () {
          if (!prevent) {
            self.$http.get('marker/' + marker.getPosition().lat() + "/" + marker.getPosition().lng() + "/")
              .then((response) => {
                self.activeMarker.id = response.body.data[0].id;
                self.activeMarker.lat = parseFloat(response.body.data[0].lat);
                self.activeMarker.lng = parseFloat(response.body.data[0].lng);
                self.showAllIssuesByMarker(self.activeMarker.id);
              });
          }
          prevent = false;
        }, delay);

      });
      marker.addListener('dblclick', function () {
        clearTimeout(timer);
        prevent = true;

        self.getMarkerByCoords(marker.getPosition().lat(), marker.getPosition().lng());
        window.marker = marker;
        var modal = document.getElementById('myModal');
        var span = document.getElementsByClassName("close")[0];
        modal.style.display = "table";

        span.onclick = function () {
          modal.style.display = "none";
        };
      });
    },

    showAllIssuesByMarker(markerId) {
      var self = this;
      self.$http.get("issues/mapMarker/" + markerId).then(response => {
        var i;
        for (i = 0; i < response.body.data.length; i++) {
          self.issues.push({
            id: response.body.data[i].id,
            title: response.body.data[i].title,
            description: response.body.data[i].text,
            typeId: response.body.data[i].typeId
          });
        }
        self.showDialog = true;
      })
    },

    getMarkerByCoords(lat, lng) {
      this.$http.get('marker/' + lat + "/" + lng + "/").then((response) => {
        window.id = response.body.data[0].id;
      });
      window.isPlaced = true;
    },

    previewImage() {
      var reader = new FileReader();
      reader.readAsDataURL(document.getElementById("uploadImage").files[0]);
      reader.onload = function (e) {
        document.getElementById("preview").src = e.target.result;
        document.getElementById("preview").hidden = false;
      };
    },

    snackBarAction() {
      this.$router.push('auth/login');
    },

    redirectToIssue(issueId, marker) {
      localStorage.setItem('redirectFromIssue', true);
      localStorage.setItem('activeMarker', JSON.stringify(marker));
      localStorage.setItem('zoom', this.map.getZoom());
      this.$router.push('issue/' + issueId);
    },

    loadAllMarkers() {
      let self = this;
      this.$http.get('').then((response) => {
          for (var i = 0; i < response.body.data.length; i++) {

            var lat = parseFloat(response.body.data[i].lat);
            var lng = parseFloat(response.body.data[i].lng);
            var type = response.body.data[i].type.toString();

            var marker = new google.maps.Marker({
              position: {
                lat: lat,
                lng: lng
              },
            });
            this.setListeners(marker);
            this.setMarkerType(marker, type);

            this.markers.push(marker);
          }
          for (var i = 0; i < this.markers.length; i++) {
            var marker = this.markers[i];
            if (self.map.getBounds().contains(marker.getPosition())) {
              marker.setMap(self.map);
            }
          }
        }
      );
    },
  },

  mounted: function () {
    this.initMap();
  }
}
