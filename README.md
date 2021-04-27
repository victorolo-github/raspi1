# raspi1 
Código Robot Raspi1

# Cargar Librería de terceros en Maven_PCA9685Driver.txt
C:\Users\ulabvrl\Desktop\Temp\basura\Projects\rpi>mvn install:install-file -Dfile="C:\Users\ulabvrl\Desktop\Temp\basura\Projects\raspi1-master\3rdLib\DRIVERPCA9685_2\Java_Servo_Driver.jar" -DgroupId=com.rpi -DartifactId=pca9685Driver -Dversion=1.0 -Dpackaging=jar

# ¿Como ejecutar el .jar en la Raspberry?
1) instalar java
   pi@raspberrypi:~ $ sudo apt-get install default-jdk
2) instalar openjfx
   pi@raspberrypi:~ $ sudo apt-get install openjfx
3) Ejecutar
   pi@raspberrypi:~ $ java --module-path /usr/share/openjfx/lib --add-modules ALL-MODULE-PATH -jar raspi1-1.0-SNAPSHOT.jar