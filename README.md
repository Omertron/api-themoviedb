The Movie DB API
================

Author: Stuart Boston (Omertron AT Gmail DOT com)

This API uses the [TheMovieDB.org API](http://api.themoviedb.org/)

Originally written for use by Yet Another Movie Jukebox [(YAMJ)](http://code.google.com/p/moviejukebox/)

But anyone can use it for other projects as well.

[![Flattr this git repo](http://api.flattr.com/button/flattr-badge-large.png)](https://flattr.com/submit/auto?user_id=Omertron&url=https://github.com/Omertron/api-themoviedb&title=TheMovieDB API&language=&tags=github&category=software)

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/Omertron/api-themoviedb/trend.png)](https://bitdeli.com/free "Bitdeli Badge")
***

TMDB TV Support
===============

TheMovieDb has recently added TV shows to their site.
I am working on an update to this API to support that, but it will require a re-write of the API to support the new models and ensure that it is simple to understand.

Therefore TV support will be added to *v4.0* of the API, major version change because of the large change to the API.  I will release a stable version of 3.9 in the next week before releaseing 4.0 SNAPSHOT.

If you are interested in the current level of TV support checkout the `tv` branch where all the changes are being done.


***
### TheMovieDB.org
This is an excellent open database for movie and film content.

I *strongly* encourage you to check it out and contribute to keep it growing.

### http://www.themoviedb.org
***
Project Logging
---------------
This project uses [SLF4J](http://www.slf4j.org) to abstract the logging in the project.

To use the logging in your own project you should add one of the bindings listed [HERE](http://www.slf4j.org/manual.html#swapping)

Project Documentation
---------------------
The automatically generated documentation can be found [HERE](http://omertron.github.com/api-themoviedb/)
