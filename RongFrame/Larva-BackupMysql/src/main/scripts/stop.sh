#!/usr/bin/env bash
echo `ps -ef|grep  BackUpMySQL.jar|grep -v grep`
kill -9 `ps -ef|grep BackUpMySQL.jar|grep -v grep|awk '{print $2}'`
