
# Moodic Service API

Service for getting the correct music mood depending on your city's weather.

### Steps to Run

On `moodic` dir:

```sudo docker-compose up --build```

In another terminal window or a browser:

```curl http://localhost:8080/api/v1/songs?city=<some_city>```

Example:

```curl http://localhost:8080/api/v1/songs?city=zapopan```

Output:

```["Relationship (feat. Future)","Perfect","Fight Night","I Don’t Wanna Live Forever (Fifty Shades Darker)","Time for That","Studio","Mercy","Playinwitme (feat. Kehlani)","Prayer in C - Robin Schulz Radio Edit","Call Out My Name","Whatever You Need (feat. Chris Brown & Ty Dolla $ign)","Easy Love","Turn Down for What","Comin Out Strong (feat. The Weeknd)","Call Me","Ain't Giving Up","Amnesia","Say Something","Bodak Yellow","Waves"]```