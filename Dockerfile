# 基础镜像，使用OpenJDK 11作为基础镜像，你可以根据项目需求更换合适的JDK版本
FROM openjdk:11-jdk-slim

# 设置工作目录
WORKDIR /financing

# 将打包后的Spring Boot应用的JAR文件复制到容器中的 /app 目录下
COPY target/*.jar financing.jar

# 暴露应用运行的端口，这里假设你的Spring Boot应用运行在8080端口，根据实际情况修改
EXPOSE 9528

# 定义容器启动时要执行的命令
CMD ["java", "-jar", "financing.jar"]