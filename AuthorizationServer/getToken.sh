#!/bin/bash
curl webshop-client:secretPassword@localhost:8086/auth/oauth/token -d grant_type=password -d username=schnowman -d password=anna -d scope=read