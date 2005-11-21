REM Use maven build file to do this!!!!!

set JAVA_HOME=C:\java\jdk1.5.0_03

C:\java\jwsdp-1.5\jaxb\bin\xjc.bat feeder.xsd -d ../java -p org.siri.feederconfig.xmlbinding

pause
