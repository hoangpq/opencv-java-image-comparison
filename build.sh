VERSION="4.3.0"
URL="https://codeload.github.com/opencv/opencv/tar.gz/$VERSION"
curl -o opencv.tar.gz $URL
tar -xvf ./opencv.tar.gz -C ./

cd "opencv-$VERSION"
mkdir build && cd build
cmake -D -DBUILD_SHARED_LIBS=OFF ..
make -j4

SHARED_PATH=$(find "$PWD" -name *java*.dylib | awk 'NR==1{print $1}')
SHARED_PATH_SO=$(echo $SHARED_PATH | sed -e "s/dylib/so/g")
cp $SHARED_PATH $SHARED_PATH_SO

echo $SHARED_PATH_SO
