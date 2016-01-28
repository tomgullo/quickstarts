_services=(
"service1"
"service2"
)

for s in ${_services[@]};
do
        sudo -i -u peadmin mco rpc service status service=$s 
        #sudo -i -u peadmin mco rpc service stop service=$s 
done;

