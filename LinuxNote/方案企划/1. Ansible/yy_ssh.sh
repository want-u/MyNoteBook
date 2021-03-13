#!/bin/bash
#yum -y install expect
#hostip=$1
#name=$2
#PASSWORD=$3
key_generate() {
    expect -c "set timeout -1;
        spawn ssh-keygen -t rsa;
        expect {
            {Enter file in which to save the key*} {send -- \r;exp_continue}
            {Enter passphrase*} {send -- \r;exp_continue}
            {Enter same passphrase again:} {send -- \r;exp_continue}
            {Overwrite (y/n)*} {send -- n\r;exp_continue}
            eof             {exit 0;}
    };"
}
auto_ssh_copy_id () {
    expect -c "set timeout -1;
        spawn ssh-copy-id -i $HOME/.ssh/id_rsa.pub $2@$1;
            expect {
                {Are you sure you want to continue connecting *} {send -- yes\r;exp_continue;}
                {*password:} {send -- $3\r;exp_continue;}
                eof {exit 0;}
            };"
}

check_cmd () {
    if [[ $? == 0 ]];then
        echo "$1 SUCCESS"
    else
        echo "$1 ERROR"
        exit
    fi
}

if [[ ! -e $HOME/.ssh/id_rsa.pub ]]; then
  key_generate && check_cmd "ssh key creat"
fi
cat host_ip.txt | while read myline
do
    hostip=`echo $myline | cut -d " " -f1`
    name=`echo $myline | cut -d " " -f2`
    PASSWORD=`echo $myline | cut -d " " -f3`
    auto_ssh_copy_id $hostip  $name $PASSWORD
done
check_cmd "ssh scripts run cmd"  