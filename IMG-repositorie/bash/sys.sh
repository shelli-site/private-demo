#!/bin/bash

APP_LIST=(mysql shadowsocks firewalld)

usage() {
    echo "用法: sh sys.sh [start(启动)|stop(停止)|restart(重启)|status(状态)|install(安装)]"
    exit 1
}

is_exist(){
  pid=`ps -ef|grep $APP_NAME|grep -v grep|awk '{print $2}' `
  if [ -z "${pid}" ]; then
   return 1
  else
    return 0
  fi
}

start(){
echo ""
for APP_NAME in ${APP_LIST[*]}
do
  is_exist
  if [ $? -eq "0" ]; then
    echo "[info]${APP_NAME} 正在运行。 pid=${pid} ."
  else
    case "${APP_NAME}" in
      "mysql")
        service mysqld start
        ;;
      "shadowsocks")
        service shadowsocks start
        ;;
      "firewalld")
        systemctl start firewalld
        ;;
      *)
        echo "[warning]${APP_NAME}未识别"
        ;;
     esac

    echo "[info]   ${APP_NAME}启动成功"
    fi
done
echo ""
}

stop(){
echo ""
for APP_NAME in ${APP_LIST[*]}
do
  is_exist
  if [ $? -eq "0" ]; then
    case "${APP_NAME}" in
      "mysql")
        service mysqld stop
        echo "[info]   ${APP_NAME}已停止运行"
        ;;
      *)
        kill -9 $pid
        echo "[info]   ${pid} 进程已被杀死，${APP_NAME}停止运行"
        ;;
    esac
  else
    echo "[info]   ${APP_NAME} 没有运行。"
  fi
done
echo ""
}

status(){
echo ""
for APP_NAME in ${APP_LIST[*]}
do
  is_exist
  if [ $? -eq "0" ]; then
      case "${APP_NAME}" in
        "firewalld")
          echo "[info]   ${APP_NAME} 正在运行。Pid is ${pid}"
          echo "[info]   已开放端口"`firewall-cmd --zone=public --list-ports`
          ;;
        *)
          echo "[info]   ${APP_NAME} 正在运行。Pid is ${pid}"
          ;;
      esac
  else
    echo "[warning]${APP_NAME} 没有运行。"
  fi
done
echo ""
}

restart(){
  stop
  start
}

install_mysql(){
  echo "install mysql"
}

install_shadowsocks(){
  wget –no-check-certificate  https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocks.sh

  chmod +x shadowsocks.sh

  ./shadowsocks.sh 2>&1 | tee shadowsocks.log
}

install_firewalld(){
  echo "install firewalld"
}
install(){
  echo "输入需要安装的模块(可输入all全部安装)。mysql | shadowsocks | firewalld"
  read INSTALL_NAME
  case "${INSTALL_NAME}" in
  "mysql")
    install_mysql
    ;;
  "shadowsocks")
    install_shadowsocks
    ;;
  "firewalld")
    install_firewalld
    ;;
  "all")
    install_mysql
	install_shadowsocks
    install_firewalld
    ;;
  *)
    echo "暂时支持以下模块。 mysql | shadowsocks | firewalld"
    ;;
  esac
}

case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  "install")
    install
    ;;
  *)
    usage
    ;;
esac
