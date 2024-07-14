##构建镜像
docker build -t string-test .
##运行镜像 [TAG] 为镜像的tag,通过 [docker images] 命令查看
docker run -d -p 8848:8848 string-test:[TAG]