IF NOT EXIST eureka-client-producer-0.0.1-SNAPSHOT.jar (
    @echo file 'eureka-client-producer-0.0.1-SNAPSHOT.jar' not exist, copying from 'target' directory
    COPY /Y ..\target\eureka-client-producer-0.0.1-SNAPSHOT.jar .\
)