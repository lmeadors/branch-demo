# Branch Demo Application

## Using the application

After starting it up, it will be listening on port 8080; it can be called like this:

```shell
curl http://localhost:8080/users/lmeadors
```

For a crude load test to verify caching, I did this (using ab):

```shell
ab -n 10000 -c 10 http://localhost:8080/users/lmeadors
```

## V1.1 - better, but not great

Added logging, caching, some error handling, and unit tests.

## V1.0 - brute force "git-r-dun" version

This is the "crude but effective" build.
