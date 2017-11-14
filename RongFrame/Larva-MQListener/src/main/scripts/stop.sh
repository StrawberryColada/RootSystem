#!/usr/bin/env bash
echo `ps -ef|grep  MQListener.jar|grep -v grep`
kill -9 `ps -ef|grep MQListener.jar|grep -v grep|awk '{print $2}'`
