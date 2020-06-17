# Opencv Java Image Example

## BUILDING
```bash
# copy script to other folder to prevent intellij indexing
chmod +x ./build.sh 
./build.sh 
# will show the dynamic library path like 
# <folder>/opencv-4.3.0/build/lib/libopencv_java430.so
```

```java
// Importing dynamic library
System.load("<folder>/opencv-4.3.0/build/lib/libopencv_java430.so");
```