#!/bin/bash
#curl -X POST webshop-client:secretPassword@localhost:8086/auth/oauth/token -d grant_type=password -d username=schnowman -d password=anna -d scope=read #--header 'Authorization:Basic nw--f7zYrvaC59mBrUuws8qfqS8'
curl --request POST http://localhost:8086/auth/oauth/token --header "Authorization:Basic d2Vic2hvcC1jbGllbnQ6c2VjcmV0UGFzc3dvcmQ=" --data "grant_type=password" --data "username=schnowman" --data "password=anna"
