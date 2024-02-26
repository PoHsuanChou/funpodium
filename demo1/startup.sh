#!/bin/bash
source ./wait_for_service.sh &&
  waitForService db 3306 mysql &&
  java -jar ./demo1-0.0.1-SNAPSHOT.jar