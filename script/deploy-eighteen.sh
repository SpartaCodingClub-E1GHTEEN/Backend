#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/Backend
LOG_FILE=$REPOSITORY/log.txt

echo "deploy-eighteen.sh 시작 " | sudo tee -a $LOG_FILE

cd $REPOSITORY || echo "repository 없음 $REPOSITORY" | sudo tee -a $LOG_FILE
echo "현재 디렉토리: $REPOSITORY" | sudo tee -a $LOG_FILE

APP_NAME=Eighteen
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

# 서비스 종료
if [ -z "$CURRENT_PID" ]
then
  echo "실행중인 Eighteen서비스 없음." | sudo tee -a $LOG_FILE
else
  echo "sudo kill -9 $CURRENT_PID" | sudo tee -a $LOG_FILE # 로그파일에 기록
  echo "$CURRENT_PID" | xargs sudo kill -9 # 실제 프로세스 종료
  sleep 5
fi

# 실행 및 로그 저장
nohup sudo java -jar -Xms1024m -Xmx1024m -Dspring.profiles.active=dev -Dapp.name=$APP_NAME "$JAR_PATH" > jarExcuete.log 2>&1 < /dev/null &

# 실행된 프로세스ID 확인
RUNNING_PROCESS=$(ps aux | grep java | grep "$JAR_NAME")
if [ -z "$RUNNING_PROCESS" ]
then
  echo "애플리케이션 프로세스 실행 X" | sudo tee -a $LOG_FILE
else
  echo "어플리케이션 프로세스 확인: $RUNNING_PROCESS" | sudo tee -a $LOG_FILE
fi