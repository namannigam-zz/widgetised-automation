#!/usr/bin/env bash
cd ..
export platform=$1;
export machine=$2;
mvn clean install
if [ ${platform} = 'Android' ]
then
    mvn exec:java -pl driver -Dmodule=app -Dplatform=$1 -Dmachine=$2 -DbatchId=$3 -DtestFile=SmokeTests_Android.xml;
elif [ ${platform} = 'iOS' ]
then
    mvn exec:java -pl driver -Dmodule=app -Dplatform=$1 -Dmachine=$2 -DbatchId=$3 -DtestFile=SmokeTests_iOS.xml;
fi