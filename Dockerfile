# 基础镜像，使用OpenJDK 11作为基础镜像
FROM openjdk:11-jdk-slim

# 安装时区数据包
RUN apt-get update && apt-get install -y tzdata

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置工作目录
WORKDIR /opt/financing

# 将打包后的Spring Boot应用的JAR文件复制到容器中的 /app 目录下
COPY target/*.jar financing.jar

# 暴露应用运行的端口
EXPOSE 9528

# 定义容器启动时要执行的命令
CMD ["java", "-jar", "financing.jar"]