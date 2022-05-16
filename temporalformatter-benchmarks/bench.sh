if [[ -n $2 ]]
then
    PROF="-prof $2"
fi

/usr/java/jdk-11/bin/java -jar target/benchmarks.jar -rf json $@ -wi 3 -i 3 -r 10 -f 3 $1 $PROF > output/$1$(date +%s) 2>&1
