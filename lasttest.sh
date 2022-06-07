$/bin/bash

for (( c=1; c<=2; c++ ))
do  
   curl http://localhost:8090/v1/iban/generateTestData?numberOfIbans=100000 > testdata17.json &
   curl http://localhost:8080/v1/iban/generateTestData?numberOfIbans=100000 > testdata11.json &
done

