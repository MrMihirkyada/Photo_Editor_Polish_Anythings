cmake_minimum_required(VERSION 3.4.1)

project(MyProject)

# Specify the OpenCV directory
set(OpenCV_DIR "path/to/your/opencv/build")

# Find OpenCV
find_package(OpenCV REQUIRED)

# Add your source files
add_library(native-lib SHARED src/main/cpp/native-lib.cpp)

# Link OpenCV libraries
target_link_libraries(native-lib ${OpenCV_LIBS})
