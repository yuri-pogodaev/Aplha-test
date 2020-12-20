#TEST APP for Alpha
Run: 
> docker build . -t currency-001  
> docker run --publish 8081:8081 currency-001
#Command for API
>http://localhost:8081/latest