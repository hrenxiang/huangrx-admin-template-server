#!/bin/sh
# ./script/template.sh start 启动 stop 停止 restart 重启 status 状态
AppName=template-admin.jar

# JVM参数
JVM_OPTS="-Dname=$AppName  -Duser.timezone=Asia/Shanghai -Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC"

if [ "$1" = "" ];
then
    printf '\033[0;31m 未输入操作名 \033[0m  \033[0;34m {start|stop|restart|status} \033[0m\n'
    exit 1
fi

if [ "$AppName" = "" ];
then
    printf "\033[0;31m 未输入应用名 \033[0m"
    exit 1
fi

start() {
    PID=$(pgrep $AppName)

	if [ x"$PID" != x"" ]; then
	    echo "$AppName is running..."
	else
		nohup java "$JVM_OPTS" -jar $AppName --spring.config.location=./config/application.yml --logging.config=./config/logback.xml > /dev/null 2>&1 &
		echo "Start $AppName success..."
	fi
}

stop() {
    echo "Stop $AppName"

	PID=""
	query(){
		PID=$(pgrep $AppName)
	}

	query
	if [ x"$PID" != x"" ]; then
		kill -TERM "$PID"
		echo "$AppName (pid:$PID) exiting..."
		while [ x"$PID" != x"" ]
		do
			sleep 1
			query
		done
		echo "$AppName exited."
	else
		echo "$AppName already stopped."
	fi
}

restart() {
    stop
    sleep 2
    start
}

status() {
    PID=$(pgrep -c -f "java.*$AppName")
    if [ "$PID" != 0 ];then
        echo "$AppName is running..."
    else
        echo "$AppName is not running..."
    fi
}

case $1 in
    start)
    start;;
    stop)
    stop;;
    restart)
    restart;;
    status)
    status;;
    *)

esac