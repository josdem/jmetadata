@echo off

set ALL_DIR=%cd%
set ALL_MEM=128m
set ALL_MAX_MEM=1024m
set CLASSPATH=System\Jar;System\Jar\*
set STARTUP_ARGS=-Xmx%ALL_MAX_MEM% -Xms%ALL_MEM% -Djava.library.path="%ALL_DIR%\System\Lib"
set MAIN_CLASS=org.jas.Launcher

echo java %STARTUP_ARGS% -cp %CLASSPATH% %MAIN_CLASS%

java %STARTUP_ARGS% -cp %CLASSPATH% %MAIN_CLASS%
