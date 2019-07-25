IF NOT EXIST eureka-server-0.0.1-SNAPSHOT.jar (
    @echo file 'eureka-server-0.0.1-SNAPSHOT.jar' not exist, copying from 'target' directory
    COPY /Y ..\target\eureka-server-0.0.1-SNAPSHOT.jar .\
)