#!/bin/bash

buildClassPath() {
        jar_dir=$1
        if [ $# -ne 1 ]; then
                echo "Jar directory must be specified."
                exit 1
        fi
        class_path=
        c=1
        for i in `ls $jar_dir/*.jar`
        do
                if [ "$c" -eq "1" ]; then
                        class_path=${i}
                        c=2
                else
                        class_path=${class_path}:${i}
                fi
        done
        echo $class_path
        #return $class_path
}

export JAR_DIR="`pwd`/System/Jar"
export LIB_PATH="`pwd`/System/Lib"

export CP="./System:`buildClassPath $JAR_DIR`"

export JMETADATA_CLIENT="-Xmx1024m -Xms128m -Djava.library.path=$LIB_PATH -Djna.library.path=$LIB_PATH -cp $CP org.jas.Launcher"

if [ ! -n "$JAVA_HOME" ]; then
   echo "Please set JAVA_HOME environment variable to point to a supported Java Virtual Machine home directory"
   if [ -n `which java` ]; then
      echo "Using `which java` to launch client"
      echo "`java -version`"
      java $JMETADATA_CLIENT
   else
      echo "No Java Virtual Machine Found in $PATH"
   fi
else

   export JAVA="$JAVA_HOME/bin/java"

   if [ -e $JAVA ]; then
     echo "Using $JAVA_HOME/bin/java to launch Jmetadata"
     echo "`$JAVA_HOME/bin/java -version`"
     $JAVA_HOME/bin/java $JMETADATA_CLIENT
  else
     echo "No Java Virtual Machine found, please install a supported Java Virtual Machine or configure the JAVA_HOME environment variable properly"
  fi
fi
