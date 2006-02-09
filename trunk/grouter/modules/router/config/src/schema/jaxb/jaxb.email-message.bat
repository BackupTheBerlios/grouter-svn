REM Use maven build file to do this!!!!!


set JAVA_HOME=C:\java\JBuilder9\jdk1.4

C:\java\jwsdp-1.3\jaxb\bin\xjc.bat   email.xsd  -d ../java -p org.iris.xml.messages.email.generated

pause
