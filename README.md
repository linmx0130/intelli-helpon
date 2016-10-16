# Intelli Helpon

Intelligent search entry for programmers."

## Data sources
* StackOverflow
* Github
* Oracle Java Documents
* Mozilla Development Network
* cppreference.com

## Architecture
The server part is built on [Vert.x](http://vertx.io/), which is a modern Java
Web Framework. The client is the Atom package Inetlli Helpon. The server will
search the keyword on those data sources website and merge their results into
a list. Finally Intelli Helpon will find at most 6 relevant results to show.

## Acknowledge
This project is created at [HACKxFDU](http://fdu.hackx.org). We also need to
thanks [Github Inc.](github.com) and [Qingcloud](https://www.qingcloud.com) for
their technology supports.
