FROM openjdk:14-jdk-slim

COPY . /opt/sakila-app
WORKDIR /opt/sakila-app

ENV DEBIAN_FRONTEND=noninteractive

RUN set -x \
 && apt-get -q -y update \
 && apt-get -q -y install \
                  bash \
                  locales \
 && sed -i -e 's/^# ja_JP.UTF-8/ja_JP.UTF-8/' "/etc/locale.gen" \
 && locale-gen \
 && update-locale LANG=ja_JP.UTF-8 LC_ALL=ja_JP.UTF-8

ENV LANG=ja_JP.UTF-8 \
    LC_ALL=ja_JP.UTF-8

RUN ./gradlew --no-daemon clean
