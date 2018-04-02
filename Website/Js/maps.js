// $.get("http://145.24.191.11:8080/api/monuments/all").done(function(data){
//     console.log(status);
// });

    var map
    var marker = []
    var clear = false
    
    $.ajax({
            type: 'GET',
            crossDomain: true,
            async:true,
            headers: {"Access-Control-Allow-Origin": "*"},
            contentType:"application/json; charset=utf-8",
            dataType: 'json',
            url: 'http://localhost:8080/api/monuments/all',        
            success: monumentsSuccessCallback,
            error: monumentsErrorCallback,
    });

    $.ajax({
            type: 'GET',
            crossDomain: true,
            async:true,
            headers: {"Access-Control-Allow-Origin": "*"},
            contentType:"application/json; charset=utf-8",
            dataType: 'json',
            url: 'http://localhost:8080/api/biketheft/all',        
            success: biketheftSuccessCallback,
            error: biketheftErrorCallback,
        });

    function Marks(i, name, LatLng) {
        marker[i] = new google.maps.Marker({
            position: LatLng,
            map: map,
            title: name
        });
    }

    function clearMarkers() {
        if(clear) {
            for (var i = 0; i < marker.length; i++ ) {
                marker[i].setMap(map);
            }
            clear = false
        } else {
            for (var i = 0; i < marker.length; i++ ) {
                marker[i].setMap(null);
            }
            clear = true
        }
      }

    function toggleDames() {
        Dames.setMap(Dames.getMap() ? null : map);
    }

    function toggleHeren() {
        Heren.setMap(Heren.getMap() ? null : map);
    }

    function toggleNeutraal() {
        neutraal.setMap(neutraal.getMap() ? null : map);
    }

    function monumentsSuccessCallback(data, status){
        for (i = 0; i < data.data.length; i++) {
            name = data.data[i].naam_c;
            LatLng = {lat:+data.data[i].lat, lng:+data.data[i].long};
            Marks(i, name, LatLng)	
        };
    }

    function biketheftSuccessCallback(data, status){
        var herenlist = [];
        var dameslist = [];
        var list = [];
        for (i = 0; i < data.data.length; i++) {
            hlat = parseFloat(data.data[i].lat);
            hlng = parseFloat(data.data[i].long);
            if(!isNaN(hlat) && !isNaN(hlng) && data.data[i].trefwoord == "DAMES"){
                dameslist.push({location: new google.maps.LatLng(hlat, hlng)});
                var Damesarray = new google.maps.MVCArray(dameslist);
            } else if(!isNaN(hlat) && !isNaN(hlng) && data.data[i].trefwoord == "HEREN"){
                herenlist.push({location: new google.maps.LatLng(hlat, hlng)});
                var Herenarray = new google.maps.MVCArray(herenlist);
            } else if(!isNaN(hlat) && !isNaN(hlng)){
                list.push({location: new google.maps.LatLng(hlat, hlng)});
                var array = new google.maps.MVCArray(list);
            } else {
                console.log("Failed to load: NaN");
            }
        };
        Dames = new google.maps.visualization.HeatmapLayer({
            data: Damesarray,
            map: null,
            radius: 0.003,
            dissipating: false
        });
        Heren = new google.maps.visualization.HeatmapLayer({
            data: Herenarray,
            map: null,
            radius: 0.003,
            dissipating: false
        });
        neutraal = new google.maps.visualization.HeatmapLayer({
            data: array,
            map: null,
            radius: 0.003,
            dissipating: false
        });
    }
    
    function monumentsErrorCallback(data, status){
        console.log("error");
        console.log( "data:" + data, "status: " + status);
    }

    function biketheftErrorCallback(data, status){
        console.log("error");
        console.log( "data:" + data, "status: " + status);
    }

    function initMap() {
        var Rotterdam = {lat: 51.9244201, lng: 4.4777325};

        map  = new google.maps.Map(document.getElementById('map'), {
        zoom: 13,
        center: Rotterdam
        });
    }

    