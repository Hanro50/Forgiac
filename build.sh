echo "[build]: Removing old files!"
mvn clean -f "/home/hanro50/Documents/GitHub/Forgiac/pom.xml"
echo "[build]: Building FORGIAC!"
mvn install -f "/home/hanro50/Documents/GitHub/Forgiac/pom.xml"
echo "[build]: Generating sha1"


