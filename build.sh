VERSION="4.3.0-openvino-2020.3.0"
curl -o opencv.tar.gz "https://codeload.github.com/opencv/opencv/tar.gz/$VERSION"
tar -xvf ./opencv.tar.gz -C ./
cd "opencv-$VERSION"
mkdir build && cd build
cmake -D CMAKE_BUILD_TYPE=RELEASE \
  -D CMAKE_INSTALL_PREFIX=/usr/local \
  -D INSTALL_PYTHON_EXAMPLES=ON \
  -D INSTALL_C_EXAMPLES=ON ..
make -j$(nproc)

SHARED_PATH=$(find "$PWD" . -name *.dylib | awk 'NR==1{print $1}')
SHARED_PATH_SO=$(echo $SHARED_PATH | sed -e "s/dylib/so/g")
cp $SHARED_PATH $SHARED_PATH_SO

echo $SHARED_PATH_SO
