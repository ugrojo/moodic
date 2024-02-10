
# Moodic Service API

Service for getting the correct music mood depending on your city's weather.

### Steps to Run

On `moodic` dir:

```docker-compose up --build```

In another terminal window or a browser:

```curl http://localhost:8080/api/v1/weather?city=<some_city>```

Example:

```curl localhost:8080/api/v1/weather?city=Zapopan```


