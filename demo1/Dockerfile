FROM maven:3.8.3-openjdk-17 as maven
ARG SOURCE_ROOT=/tmp/source
COPY ./ ${SOURCE_ROOT}/
RUN  mvn -f ${SOURCE_ROOT} clean package -DskipTests

FROM registry.access.redhat.com/ubi8/openjdk-17:1.18-2.1705573234


ARG RUN_ROOT=/run
ARG SOURCE_ROOT=/tmp/source

ENV SPRING_DATASOURCE_URL jdbc:mysql://db:3306/btc_database
ENV SPRING_DATASOURCE_USERNAME root
ENV SPRING_DATASOURCE_PASSWORD springboot

USER root

RUN microdnf install nc

COPY ./startup.sh ${RUN_ROOT}/
COPY ./wait_for_service.sh ${RUN_ROOT}/

RUN chmod g+x ${RUN_ROOT}/startup.sh \
    && chmod g+x ${RUN_ROOT}/wait_for_service.sh

WORKDIR ${RUN_ROOT}/

COPY --from=maven  ${SOURCE_ROOT}/target/demo1-0.0.1-SNAPSHOT.jar ${RUN_ROOT}/

ENTRYPOINT ["bash","-c","./startup.sh"]

