## hive
https://cwiki.apache.org/confluence/display/Hive/AvroSerDe
https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DML

## boot profile?
you can set a environment variable to fix profile.
powershell: setx spring.profiles.active dev

## jar?
Get-Item env:java_home
jar cf mrjars\xx.jar hello\hadoopwc\mrcode\*.class
jar cf .\mrjars\wc.jar .\bin\hello\hadoopwc\mrcode\*.class
cd .\bin

jar cf ..\mrjars\wc.jar .\hello\hadoopwc\mrcode\*.class

## java.lang.UnsatisfiedLinkError: org.apache.hadoop.io.nativeio.NativeIO$Windows.access0(Ljava/lang/String;I)Z
run winutilcopy.ps1
setx PATH "%PATH%;E:\configuratedHadoopFolder\hadoop-2.7.3\bin"

## hadoop env?
search Cluster class to debug.
JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,HADOOP_YARN_HOME

## hdfs permission
/user/admin
/tmp/hadoop-yarn/staging/admin
/tmp/hadoop-yarn/staging/history/done_intermediate

## nutch plugins zip dir to "plugins" in stead of "classes/plugins".

modify build.xml in nutch project.

## conf.set("mapreduce.app-submission.cross-platform", "true");

<property>
  <description>If enabled, user can submit an application cross-platform
  i.e. submit an application from a Windows client to a Linux/Unix server or
  vice versa.
  </description>
  <name>mapreduce.app-submission.cross-platform</name>
  <value>false</value>
</property>

## hbase shell
list
scan table

## classLoader leak
https://www.dynatrace.com/resources/ebooks/javabook/class-loader-issues/

## hbase rest
Foreground: bin/hbase rest start -p <port>
Background: bin/hbase-daemon.sh start rest -p <port>

## katharsis rest
Invoke-WebRequest -Uri http://localhost:8080/jsonapi/users -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Get
([System.Text.Encoding]::UTF8).GetString($r.Content)
Invoke-WebRequest -Uri http://localhost:8080/jsonapi/users -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -Method Put

$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/users -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"ab": 5}, "type": "users"}}' -Method Post

$r = Invoke-WebRequest -Uri http://localhost:8080/jsonapi/users/1 -Headers @{Accept="application/vnd.api+json;charset=UTF-8"} -ContentType "application/vnd.api+json;charset=UTF-8" -Body '{"data": {"attributes": {"ab": 5}, "type": "users"}}' -Method Patch

http://localhost:88/jsonapi/roles/?page[offset]=0&page[limit]=10&sort=name,-id&filter[name][EQ]=Super%20task