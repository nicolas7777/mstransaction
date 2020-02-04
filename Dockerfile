FROM openjdk:11
VOLUME /tmp
EXPOSE 8891
ADD ./target/ms.config.server-0.0.1.jar mstransaction.jar
ENTRYPOINT ["java","-jar","/mstransaction.jar"]