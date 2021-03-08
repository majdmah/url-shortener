#Tremor International Url Shortener

###Prerequisites
* [Java 8](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)
* [Redis Data Base](https://redis.io/download)

###Running the service
* Make sure to run Redis Server on port 6379 could run on docker with command : "docker run -d -p 6379:6379 --name my-redis redis"
* Run from IDE as Spring Application
* Or Run from CLI with mvn install and then mvn spring-boot:run

###Using the service
* post to localhost:8080/rest/url with url you want to shorten as string inside the body
will return the id for the short link inside the response body

* get to localhost:8080/rest/url/{id} where id is shortened link, to get the full link 
inside the response body

* get to localhost:8080/rest/url/{id}/stats where id is shortened link, will get the stats
for this link

* get to localhost:8080/rest/url/{id}/redirect where id is shortened link, will redirect to 
the original link web page, will be redirected to OOPS page if id is invalid 
